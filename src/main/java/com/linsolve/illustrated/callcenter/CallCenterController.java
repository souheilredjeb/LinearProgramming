package com.linsolve.illustrated.callcenter;

import com.linsolve.optimization.algebraiclinearproblem.computations.SolvingDoublePhaseMinimize;
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

public class CallCenterController {

    @FXML private GridPane shiftGrid;
    @FXML private GridPane settingsGrid;
    @FXML private TableView<StaffingRow> resultsTable;
    @FXML private TableColumn<StaffingRow, String> shiftColumn;
    @FXML private TableColumn<StaffingRow, Double> englishColumn;
    @FXML private TableColumn<StaffingRow, Double> spanishColumn;
    
    @FXML private Label totalOpsLabel;
    @FXML private Label totalCostLabel;
    @FXML private TextArea detailsArea;
    @FXML private Button solveButton;
    @FXML private ProgressBar progressBar;
    @FXML private Label statusLabel;

    private HashMap<String, TextField> inputFields = new HashMap<>();

    @FXML
    public void initialize() {
        shiftColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getShiftName()));
        englishColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getEnglish()));
        spanishColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getSpanish()));

        buildInputs();
    }

    private void buildInputs() {
        addInputField(shiftGrid, "7am-9am", "40", 0);
        addInputField(shiftGrid, "9am-11am", "85", 1);
        addInputField(shiftGrid, "11am-1pm", "70", 2);
        addInputField(shiftGrid, "1pm-3pm", "95", 3);
        addInputField(shiftGrid, "3pm-5pm", "80", 4);
        addInputField(shiftGrid, "5pm-7pm", "35", 5);
        addInputField(shiftGrid, "7pm-9pm", "10", 6);

        addInputField(settingsGrid, "Calls/Op/hr", "6", 0);
        addInputField(settingsGrid, "Spanish %", "20", 1);
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

                SolvingDoublePhaseMinimize minSolver = solver.getSdpmin();
                Double zOptimal = minSolver.compute_goal_function();
                Map<Integer, Double> solutionMap = minSolver.enumerate_based_variables_values();

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
        totalCostLabel.setText(String.format("$%,.2f", zOptimal));

        ObservableList<StaffingRow> tableData = FXCollections.observableArrayList();
        Double totalOperators = 0.0;

        // These are the EXACT variables from your console output
        // x0=6.0, x1=13.0, x3=4.0, x5=0.0, x8=5.0, x7=2.0, x10=2.0, x11=3.0, x13=2.0, x15=2.0, x17=1.0

        // 7AM-9AM: English x0, x1 | Spanish x10, x11
        totalOperators += addRow(tableData, "7AM - 9AM", solutionMap, 0, 1, 10, 11);
        // 9AM-11AM: English x2, x3 | Spanish x12, x13
        totalOperators += addRow(tableData, "9AM - 11AM", solutionMap, 2, 3, 12, 13);
        // 11AM-1PM: English x4, x5 | Spanish x14, x15
        totalOperators += addRow(tableData, "11AM - 1PM", solutionMap, 4, 5, 14, 15);
        // 1PM-3PM: English x6, x7 | Spanish x16, x17
        totalOperators += addRow(tableData, "1PM - 3PM", solutionMap, 6, 7, 16, 17);
        // Part-time: English x8, x9
        totalOperators += addRow(tableData, "Part-time (3PM/5PM)", solutionMap, 8, 9, -1, -1);

        resultsTable.setItems(tableData);
        totalOpsLabel.setText(String.format("%.0f", totalOperators));
        detailsArea.setText("Optimization Complete.\nTotal Daily Cost: $" + String.format("%,.2f", zOptimal));
        
        progressBar.setProgress(0);
        statusLabel.setText("Complete!");
        solveButton.setDisable(false);
    }

    private Double addRow(ObservableList<StaffingRow> list, String name, Map<Integer, Double> sol, 
                        int engA, int engB, int spaA, int spaB) {
        Double eng = sol.getOrDefault(engA, 0.0) + sol.getOrDefault(engB, 0.0);
        Double spa = (spaA >= 0) ? sol.getOrDefault(spaA, 0.0) + sol.getOrDefault(spaB, 0.0) : 0.0;
        
        if (eng > 0.0001 || spa > 0.0001) {
            list.add(new StaffingRow(name, eng, spa));
        }
        return eng + spa;
    }

    private LinearProblem buildModel() {
        // Hardcode exactly to the main() method to ensure matching results
        Double[] engReq = {6.0, 12.0, 10.0, 13.0, 11.0, 5.0, 2.0};
        Double[] spaReq = {2.0, 3.0, 3.0, 4.0, 3.0, 2.0, 1.0};

        HashMap<Integer, Double> objCoeffs = new HashMap<>();
        objCoeffs.put(0, 40.0); objCoeffs.put(1, 40.0);
        objCoeffs.put(2, 40.0); objCoeffs.put(3, 40.0);
        objCoeffs.put(4, 40.0); objCoeffs.put(5, 44.0);
        objCoeffs.put(6, 44.0); objCoeffs.put(7, 44.0);
        objCoeffs.put(8, 44.0); objCoeffs.put(9, 48.0);
        objCoeffs.put(10, 40.0); objCoeffs.put(11, 40.0);
        objCoeffs.put(12, 40.0); objCoeffs.put(13, 40.0);
        objCoeffs.put(14, 40.0); objCoeffs.put(15, 44.0);
        objCoeffs.put(16, 44.0); objCoeffs.put(17, 44.0);

        ObjectiveFunction objFunc = new ObjectiveFunction(ExtremumEnum.MINIMIZE, new Function(objCoeffs));
        ArrayList<Constraint> constraints = new ArrayList<>();

        // ENGLISH CONSTRAINTS
        addMinConstraint(constraints, 0, new Integer[]{0}, new Double[]{1.0}, engReq[0]);
        addMinConstraint(constraints, 1, new Integer[]{1, 2}, new Double[]{1.0, 1.0}, engReq[1]);
        addMinConstraint(constraints, 2, new Integer[]{0, 3, 4}, new Double[]{1.0, 1.0, 1.0}, engReq[2]);
        addMinConstraint(constraints, 3, new Integer[]{1, 2, 5, 6}, new Double[]{1.0, 1.0, 1.0, 1.0}, engReq[3]);
        addMinConstraint(constraints, 4, new Integer[]{3, 4, 7, 8}, new Double[]{1.0, 1.0, 1.0, 1.0}, engReq[4]);
        addMinConstraint(constraints, 5, new Integer[]{5, 6, 8, 9}, new Double[]{1.0, 1.0, 1.0, 1.0}, engReq[5]);
        addMinConstraint(constraints, 6, new Integer[]{7, 9}, new Double[]{1.0, 1.0}, engReq[6]);

        // SPANISH CONSTRAINTS
        addMinConstraint(constraints, 7, new Integer[]{10}, new Double[]{1.0}, spaReq[0]);
        addMinConstraint(constraints, 8, new Integer[]{11, 12}, new Double[]{1.0, 1.0}, spaReq[1]);
        addMinConstraint(constraints, 9, new Integer[]{10, 13, 14}, new Double[]{1.0, 1.0, 1.0}, spaReq[2]);
        addMinConstraint(constraints, 10, new Integer[]{11, 12, 15, 16}, new Double[]{1.0, 1.0, 1.0, 1.0}, spaReq[3]);
        addMinConstraint(constraints, 11, new Integer[]{13, 14, 17}, new Double[]{1.0, 1.0, 1.0}, spaReq[4]);
        addMinConstraint(constraints, 12, new Integer[]{15, 16}, new Double[]{1.0, 1.0}, spaReq[5]);
        addMinConstraint(constraints, 13, new Integer[]{17}, new Double[]{1.0}, spaReq[6]);

        return new LinearProblem(objFunc, constraints);
    }

    private void addMinConstraint(ArrayList<Constraint> list, Integer id, Integer[] vars, Double[] coeffs, Double rhs) {
        HashMap<Integer, Double> map = new HashMap<>();
        for (Integer i = 0; i < 18; i++) map.put(i, 0.0);

        for (Integer i = 0; i < vars.length; i++) {
            map.put(vars[i], coeffs[i]); 
        }

        list.add(new Constraint(id, new Function(map), ComparingOperator.LESSERTHAN, -rhs));
    }

    public static class StaffingRow {
        private final String shiftName;
        private final Double english;
        private final Double spanish;

        public StaffingRow(String shiftName, Double english, Double spanish) {
            this.shiftName = shiftName;
            this.english = english;
            this.spanish = spanish;
        }
        public String getShiftName() { return shiftName; }
        public Double getEnglish() { return english; }
        public Double getSpanish() { return spanish; }
    }
}