package edu.icet.demo.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ExitOrClose {
    public void exit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure want to exit..?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void report(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Report Generation");
        alert.setContentText("The report generation feature is currently under development. Please check back later for updates.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            alert.close();
        }
    }
}
