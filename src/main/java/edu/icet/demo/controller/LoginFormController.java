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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    public PasswordField PasswordField;
    public TextField UserNameField;
    public AnchorPane WelcomAnchor;

    private boolean isShow;

    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();
    UserBoImpl userBoImpl=new UserBoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isShow=true;
    }


    public void SignInBtn(ActionEvent actionEvent) throws IOException {
        userBoImpl= BoFactory.getInstance().getBo(BoType.USER);
        UserEntity userEntity = userBoImpl.getUserByEmail(UserNameField.getText());

        if (userEntity==null){
            new Alert(Alert.AlertType.ERROR,"Invalid Email Address or Password").show();
            return;
        }

        String password = userBoImpl.passwordDecrypt(userEntity.getPassword());

            if (userEntity.getRole().equals("Admin") && password.equals(PasswordField.getText())){
                System.out.println("Logged");
                try {
                    SceneSwitchController.getInstance().switchScene(WelcomAnchor,"adminDash.fxml");
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            } else if (userEntity.getRole().equals("Employee") && password.equals(PasswordField.getText())) {
                EmployeeData instance = EmployeeData.getInstance();
                instance.setId(userEntity.getId());
                instance.setName(userEntity.getName());
                instance.setEmail(userEntity.getEmail());
                SceneSwitchController.getInstance().switchScene(WelcomAnchor,"employeeDash.fxml");
            } else if (userEntity.getId()==null) {
                System.out.println("Null");
            } else {
                new Alert(Alert.AlertType.ERROR,"Invalid Password").show();
            }
    }

    public void ForgetPassBtn(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(WelcomAnchor,"resetPassword.fxml");
    }

    public void NeedHelpBtn(ActionEvent actionEvent) {
    }


    public void emailKeyReleasedAction(KeyEvent keyEvent) {
        if (isShow){
            new Alert(Alert.AlertType.INFORMATION,"If your first time to sign in to this, Please reset your password clicked forgot password button").show();
            isShow=false;
        }
    }
}
