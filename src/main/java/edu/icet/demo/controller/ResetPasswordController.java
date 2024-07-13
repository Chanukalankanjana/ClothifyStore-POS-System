package edu.icet.demo.controller;

import edu.icet.demo.bo.custom.impl.UserBoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class ResetPasswordController implements Initializable {
    public TextField newPasswordField;
    public TextField reEnterNewPasswordField;
    public TextField OTPField;
    public AnchorPane ResetAnchor;
    public TextField emailField;
    private int otp;

    UserBoImpl userBoImpl = new UserBoImpl();
    ExitOrClose exitOrClose = new ExitOrClose();
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void sendOTPbtn(ActionEvent actionEvent) {
        Random random = new Random();
        otp = random.nextInt(900000) + 100000;
        System.out.println(otp);

        try {
            userBoImpl.sendEmail(emailField.getText(), Integer.toString(otp));
            new Alert(Alert.AlertType.INFORMATION, "Send Email Successfully").show();
        } catch (MessagingException e) {
            new Alert(Alert.AlertType.ERROR, "Access Denied..your Email is invalid").show();
        }
    }

    public void resetPassword(ActionEvent actionEvent) {
        try {
            if (newPasswordField.getText().equals(reEnterNewPasswordField.getText())){
                if (otp==Integer.parseInt(OTPField.getText())){
                    boolean isUpdatePassword = userBoImpl.isUpdatePassword(emailField.getText(),newPasswordField.getText());
                    if (isUpdatePassword){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Reset Password");
                        alert.setContentText("Password reset Successfully");
                        alert.showAndWait();
                        newPasswordField.setText("");
                        reEnterNewPasswordField.setText("");
                        OTPField.setText("");
                        sceneSwitch.switchScene(ResetAnchor, "loginForm.fxml");
                    }
                }else {
                    new Alert(Alert.AlertType.ERROR,"Incorrect OTP, Please Check your OTP").show();
                }

            }else {
                new Alert(Alert.AlertType.ERROR,"Password & Confirmation Password does not match..!!").show();
            }
        }catch (Exception e){
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR,"Invalid OTP").show();
        }
    }

    public void closeOnAction(ActionEvent actionEvent) {
        exitOrClose.exit();
    }

    public void goBackArrowOnAction(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Go Back");
        alert.setContentText("Are you sure you don't want to reset the password...?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            sceneSwitch.switchScene(ResetAnchor, "loginForm.fxml");

        }
    }
}
