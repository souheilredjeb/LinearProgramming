package com.linsolve.illustrated.fabrics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class FabricsApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlUrl = getClass().getResource("/com/linsolve/illustrated/fabrics/fabrics-view.fxml");
        if (fxmlUrl == null) throw new RuntimeException("FXML file not found!");

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        VBox root = loader.load();

        Scene scene = new Scene(root, 1150, 750);
        scene.getStylesheets().add(
            Objects.requireNonNull(getClass().getResource("/com/linsolve/illustrated/fabrics/style.css")).toExternalForm()
        );

        primaryStage.setTitle("Fabrics & Fall Fashions - LP Solver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}