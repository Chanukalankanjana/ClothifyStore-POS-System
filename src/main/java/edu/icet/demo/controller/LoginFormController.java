package edu.icet.demo.controller;

import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.impl.UserBoImpl;
import edu.icet.demo.entity.UserEntity;
import edu.icet.demo.utill.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import javax.print.attribute.standard.MediaName;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    public TextField UserNameField;
    public AnchorPane WelcomAnchor;
    public ImageView imgeBtn;
    public ToggleButton togglePasswordBtn;
    public javafx.scene.control.PasswordField passwordField;
    public ImageView showIcon;
    public ImageView hideIcon;
    public TextField passwordTextField;
    private MediaPlayer mediaPlayer;

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

            if (userEntity.getRole().equals("Admin") && password.equals(passwordField.getText())){
                System.out.println("Logged");
                try {
                    SceneSwitchController.getInstance().switchScene(WelcomAnchor,"adminDash.fxml");
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            } else if (userEntity.getRole().equals("Employee") && password.equals(passwordField.getText())) {
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

    public void emailKeyReleasedAction(KeyEvent keyEvent) {
        if (isShow){
            new Alert(Alert.AlertType.INFORMATION,"If your first time to sign in to this, Please reset your password clicked forgot password button").show();
            isShow=false;
        }
    }

    public void handleNeedHelpBtn(ActionEvent actionEvent) {
        URL resource = getClass().getResource("/assets/need_help.mp4");
        String videoPath = resource.toExternalForm();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        Stage videoStage = new Stage();
        StackPane root = new StackPane();
        root.getChildren().add(mediaView);
        videoStage.setScene(new Scene(root, 782, 440));
        videoStage.show();

        mediaPlayer.play();

        videoStage.setOnCloseRequest(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
        });

    }

    public void closeBtnOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void togglePasswordVisibility(ActionEvent actionEvent) {
        if (togglePasswordBtn.isSelected()) {
            // Show password as plain text
            passwordTextField.setText(passwordField.getText());
            passwordTextField.setVisible(true);
            passwordField.setVisible(false);
            showIcon.setVisible(false);
            hideIcon.setVisible(true);
        } else {
            // Show password as masked
            passwordField.setText(passwordTextField.getText());
            passwordField.setVisible(true);
            passwordTextField.setVisible(false);
            showIcon.setVisible(true);
            hideIcon.setVisible(false);
        }
    }
}
