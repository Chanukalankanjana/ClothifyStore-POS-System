package edu.icet.demo.controller;

import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.impl.UserBoImpl;
import edu.icet.demo.entity.UserEntity;
import edu.icet.demo.utill.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController {
    public PasswordField PasswordField;
    public TextField UserNameField;
    public AnchorPane WelcomAnchor;

    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();
    UserBoImpl userBoImpl=new UserBoImpl();


    public void SignInBtn(ActionEvent actionEvent) {
        userBoImpl= BoFactory.getInstance().getBo(BoType.USER);

        UserEntity userEntity = userBoImpl.getUserByEmail(UserNameField.getText());
    }

    public void ForgetPassBtn(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(WelcomAnchor,"resetPassword.fxml");
    }

    public void NeedHelpBtn(ActionEvent actionEvent) {
    }

}
