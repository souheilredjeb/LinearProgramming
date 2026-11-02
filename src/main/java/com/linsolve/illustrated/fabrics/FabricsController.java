package com.linsolve.illustrated.fabrics;

import com.linsolve.optimization.algebraiclinearproblem.computations.SolvingDoublePhaseMaximize;
import com.linsolve.optimization.algebraiclinearproblem.computations.solver.DoublePhaseLinearProblemSolver;
import com.linsolve.optimization.linearproblem.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FabricsController {

    @FXML private GridPane materialGrid;
    @FXML private GridPane demandGrid;
    @FXML private TableView<FabricRow> resultsTable;
    @FXML private TableColumn<FabricRow, String> itemColumn;
    @FXML private TableColumn<FabricRow, Double> quantityColumn;
    @FXML private TableColumn<FabricRow, Double> profitColumn;
    
    @FXML private Label totalProfitLabel;
    @FXML private TextArea detailsArea;
    @FXML private Button solveButton;
    @FXML private ProgressBar progressBar;
    @FXML private Label statusLabel;

    private final String[] ITEM_NAMES = {
        "Wool Slacks", "Cashmere Sweater", "Silk Blouse", "Silk Camisole", 
        "Tailored Skirt", "Wool Blazer", "Velvet Pants", "Cotton Sweater", 
        "Cotton Miniskirt", "Velvet Shirt", "Button-down Blouse"
    };
    
    private final Double[] UNIT_PROFITS = {
        113.0, 210.0, 60.5, 53.5, 143.25, 155.25, 139.0, 66.25, 33.75, 22.0, 26.625
    };

    private HashMap<String, TextField> inputFields = new HashMap<>();

    @FXML
    public void initialize() {
        itemColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getItemName()));
        quantityColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getQuantity()));
        profitColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getProfit()));

        buildInputs();
    }

    private void buildInputs() {
        addInputField(materialGrid, "Wool", "45000", 0);
        addInputField(materialGrid, "Acetate", "28000", 1);
        addInputField(materialGrid, "Cashmere", "9000", 2);
        addInputField(materialGrid, "Silk", "18000", 3);
        addInputField(materialGrid, "Rayon", "30000", 4);
        addInputField(materialGrid, "Velvet", "20000", 5);
        addInputField(materialGrid, "Cotton", "30000", 6);

        addInputField(demandGrid, "Wool Slacks (Min)", "4200", 0);
        addInputField(demandGrid, "Wool Blazers (Min)", "3000", 1);
        addInputField(demandGrid, "Tailored Skirts (Min)", "2800", 2);
    }

    private void addInputField(GridPane grid, String label, String defaultValue, Integer row) {
        Label lbl = new Label(label + ":");
        TextField tf = new TextField(defaultValue);
        tf.setPrefWidth(100);
        grid.add(lbl, 0, row);
        grid.add(tf, 1, row);
        inputFields.put(label, tf);
    }

    private Double getVal(String label) {
        try {
            return Double.parseDouble(inputFields.get(label).getText());
        } catch (Exception e) {
            return 0.0;
        }
    }

    @FXML
    private void handleSolve() {
        solveButton.setDisable(true);
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        statusLabel.setText("Solving...");
        resultsTable.getItems().clear();

        new Thread(() -> {
            try {
                LinearProblem lp = buildModel();
                DoublePhaseLinearProblemSolver solver = new DoublePhaseLinearProblemSolver(lp);
                solver.apply();

                SolvingDoublePhaseMaximize maxSolver = solver.getSdpmax();
                Double zOptimal = maxSolver.compute_goal_function();
                Map<Integer, Double> solutionMap = maxSolver.enumerate_based_variables_values();

                Platform.runLater(() -> updateUI(zOptimal, solutionMap));

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    statusLabel.setText("Error: " + e.getMessage());
                    solveButton.setDisable(false);
                });
            }
        }).start();
    }

    private void updateUI(Double zOptimal, Map<Integer, Double> solutionMap) {
        totalProfitLabel.setText(String.format("$%,.2f", zOptimal));

        ObservableList<FabricRow> tableData = FXCollections.observableArrayList();
        for (Integer i = 0; i < ITEM_NAMES.length; i++) {
            Double qty = solutionMap.getOrDefault(i, 0.0);
            if (qty > 0.0001) { 
                Double profit = qty * UNIT_PROFITS[i];
                tableData.add(new FabricRow(ITEM_NAMES[i], qty, profit));
            }
        }
        resultsTable.setItems(tableData);
        
        detailsArea.setText("Optimization Complete.\nOptimal Profit: $" + String.format("%,.2f", zOptimal));
        progressBar.setProgress(0);
        statusLabel.setText("Complete!");
        solveButton.setDisable(false);
    }

    private LinearProblem buildModel() {
        HashMap<Integer, Double> objectiveCoeffs = new HashMap<>();
        for (Integer i = 0; i < UNIT_PROFITS.length; i++) {
            objectiveCoeffs.put(i, UNIT_PROFITS[i]);
        }
        Function objectiveFunction = new Function(objectiveCoeffs);
        ObjectiveFunction objFunc = new ObjectiveFunction(ExtremumEnum.MAXIMIZE, objectiveFunction);   

        ArrayList<Constraint> constraints = new ArrayList<>();    

        // YOUR MAIN METHOD LOGIC EXACTLY REPLICATED
        addMinConstraint(constraints, 0, 0, getVal("Wool Slacks (Min)"));
        addMinConstraint(constraints, 1, 5, getVal("Wool Blazers (Min)"));
        addMinConstraint(constraints, 2, 4, getVal("Tailored Skirts (Min)"));

        addMaxConstraint(constraints, 3, 3, 15000.0); // Silk Camisoles
        addMaxConstraint(constraints, 4, 2, 12000.0); // Silk Blouses
        addMaxConstraint(constraints, 5, 1, 4000.0);  // Cashmere
        addMaxConstraint(constraints, 6, 9, 6000.0);  // Velvet Shirts
        addMaxConstraint(constraints, 7, 6, 5500.0);  // Velvet Pants

        addConstraint(constraints, 8, new Integer[]{7, 8}, new Double[]{1.0, -1.0}, 0.0); // Cotton Link
        addConstraint(constraints, 9, new Integer[]{2, 3}, new Double[]{1.0, -1.0}, 0.0); // Silk Link

        addConstraint(constraints, 10, new Integer[]{7, 8}, new Double[]{1.5, 0.5}, getVal("Cotton"));
        addConstraint(constraints, 11, new Integer[]{6, 9}, new Double[]{3.0, 1.5}, getVal("Velvet"));
        addConstraint(constraints, 12, new Integer[]{4, 10}, new Double[]{2.0, 1.5}, getVal("Rayon"));
        addConstraint(constraints, 13, new Integer[]{2, 3}, new Double[]{1.5, 0.5}, getVal("Silk"));
        addConstraint(constraints, 14, new Integer[]{1}, new Double[]{1.5}, getVal("Cashmere"));
        addConstraint(constraints, 15, new Integer[]{0, 4, 5, 6}, new Double[]{2.0, 1.5, 1.5, 2.0}, getVal("Acetate"));
        addConstraint(constraints, 16, new Integer[]{0, 5}, new Double[]{3.0, 2.5}, getVal("Wool"));

        return new LinearProblem(objFunc, constraints);
    }

    private void addConstraint(ArrayList<Constraint> list, Integer id, Integer[] vars, Double[] coeffs, Double rhs) {
        HashMap<Integer, Double> map = new HashMap<>();
        for (Integer i = 0; i < 11; i++) map.put(i, 0.0); 
        for (Integer i = 0; i < vars.length; i++) {
            map.put(vars[i], coeffs[i]);
        }
        list.add(new Constraint(id, new Function(map), ComparingOperator.LESSERTHAN, rhs));
    }

    private void addMaxConstraint(ArrayList<Constraint> list, Integer id, Integer varIndex, Double maxValue) {
        HashMap<Integer, Double> map = new HashMap<>();
        for (Integer i = 0; i < 11; i++) map.put(i, 0.0);
        map.put(varIndex, 1.0); 
        list.add(new Constraint(id, new Function(map), ComparingOperator.LESSERTHAN, maxValue));
    }

    private void addMinConstraint(ArrayList<Constraint> list, Integer id, Integer varIndex, Double minValue) {
        HashMap<Integer, Double> map = new HashMap<>();
        for (Integer i = 0; i < 11; i++) map.put(i, 0.0);
        
        // Note: We leave the coefficient POSITIVE here, exactly like your main() method does.
        map.put(varIndex, 1.0); 
        
        // And we set the RHS to NEGATIVE, exactly like your main() method does.
        list.add(new Constraint(id, new Function(map), ComparingOperator.LESSERTHAN, -minValue)); 
    }

    public static class FabricRow {
        private final String itemName;
        private final Double quantity;
        private final Double profit;

        public FabricRow(String itemName, Double quantity, Double profit) {
            this.itemName = itemName;
            this.quantity = quantity;
            this.profit = profit;
        }
        public String getItemName() { return itemName; }
        public Double getQuantity() { return quantity; }
        public Double getProfit() { return profit; }
    }
}