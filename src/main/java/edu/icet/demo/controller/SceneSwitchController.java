package edu.icet.demo.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class SceneSwitchController {
    private static SceneSwitchController instance;
    private SceneSwitchController(){

    }

    public static SceneSwitchController getInstance(){
        if (instance==null){
            return instance = new SceneSwitchController();
        }
        return instance;
    }

    public void switchScene(AnchorPane currentAnchorPane, String fxml) throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/"+fxml));
        currentAnchorPane.getChildren().setAll(pane);
    }
}
