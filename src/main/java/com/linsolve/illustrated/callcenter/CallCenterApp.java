package com.linsolve.illustrated.callcenter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class CallCenterApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlUrl = getClass().getResource("/com/linsolve/illustrated/callcenter/callcenter-view.fxml");
        if (fxmlUrl == null) throw new RuntimeException("FXML file not found!");

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        VBox root = loader.load();

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(
            Objects.requireNonNull(getClass().getResource("/com/linsolve/illustrated/callcenter/style.css")).toExternalForm()
        );

        primaryStage.setTitle("Call Center Staffing - LP Solver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}