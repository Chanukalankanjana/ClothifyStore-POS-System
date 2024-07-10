package edu.icet.demo.controller;

import edu.icet.demo.bo.custom.impl.UserBoImpl;
import edu.icet.demo.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class AdminDashController implements Initializable {
    public AnchorPane adminAnchor;
    public TextField empAddressField;
    public TableView employeeTable;
    public TextField employeeIdField;
    public TextField employeeNameField;
    public TextField empEmailAddressField;
    public TableColumn empIdColumn;
    public TableColumn empNameColumn;
    public TableColumn empEmailColumn;
    public TableColumn empAddressColumn;
    public Button addEmpBtn;
    public ImageView imageView;

    UserBoImpl userBoImpl = new UserBoImpl();
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    boolean isAction = true,isEmailValid,isMouseClick;
    public void initialize(URL url, ResourceBundle resourceBundle) {

        empIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        empNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        empEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        empAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        employeeIdField.setText(userBoImpl.generateEmployeeId());
        employeeTable.setItems(userBoImpl.getAllUsers());

    }


    public void ViewActionBtn(ActionEvent actionEvent) {
        User user = userBoImpl.getUserById(employeeIdField.getText());
        if (user != null) {
            employeeNameField.setText(user.getName());
            empEmailAddressField.setText(user.getEmail());
            empAddressField.setText(user.getAddress());
        } else {
            new Alert(Alert.AlertType.ERROR, "Employee not found.").show();
        }
    }

    public void clearFields() {
        employeeNameField.clear();
        empEmailAddressField.clear();
        empAddressField.clear();
    }

    public void releaseEmailkey(KeyEvent keyEvent) {
        boolean isValidEmail = userBoImpl.isValidEmail(empEmailAddressField.getText());
        if (!isValidEmail) {
            addEmpBtn.setDisable(true);
        } else {
            addEmpBtn.setDisable(false);
        }
    }

    public void addActionBtn(ActionEvent actionEvent) {
        Random random = new Random();
        int pass = random.nextInt(90000000) + 10000000; // Generates 8 digit number
        String encrypt = Integer.toString(pass);
        String password = userBoImpl.passwordEncrypt(encrypt);

        User user = new User(
                employeeIdField.getText(),
                employeeNameField.getText(),
                empEmailAddressField.getText(),
                password,
                "Employee",
                empAddressField.getText()
        );

        if (!employeeNameField.getText().isEmpty() && userBoImpl.isValidEmail(empEmailAddressField.getText()) && !empAddressField.getText().isEmpty()) {
            boolean isInsert = userBoImpl.insertUser(user);

            if (isInsert) {
                employeeTable.setItems(FXCollections.observableArrayList(userBoImpl.getAllUsers()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Employee Added");
                alert.setContentText("Employee added successfully!");
                alert.showAndWait();
                clearFields();
                employeeIdField.setText(userBoImpl.generateEmployeeId());
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add employee. Please try again.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fill in all fields with valid data.").show();
        }
    }

    public void updateActionBtn(ActionEvent actionEvent) {
        if (!employeeNameField.getText().isEmpty() && !empEmailAddressField.getText().isEmpty() && !empAddressField.getText().isEmpty()) {
            User user = new User(
                    employeeIdField.getText(),
                    employeeNameField.getText(),
                    empEmailAddressField.getText(),
                    null,
                    null,
                    empAddressField.getText()
            );

            boolean isUpdate = userBoImpl.updateUser(user);

            if (isUpdate) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Employee Update");
                alert.setContentText("Employee updated successfully.");
                alert.showAndWait();
                employeeIdField.setText(userBoImpl.generateEmployeeId());
                clearFields();
                employeeTable.setItems(FXCollections.observableArrayList(userBoImpl.getAllUsers()));

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Update Failed");
                alert.setContentText("Failed to update employee. Please try again.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Information");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
        }
    }

    public void deleteActionBtn(ActionEvent actionEvent) {
        if (!employeeIdField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure you want to delete this Employee?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean isDeleted = userBoImpl.deleteUserById(employeeIdField.getText());
                if (isDeleted) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Employee Deleted");
                    alert2.setContentText("Employee deleted successfully.");
                    alert2.showAndWait();
                    employeeIdField.setText(userBoImpl.generateEmployeeId());
                    clearFields();
                    employeeTable.setItems(FXCollections.observableArrayList(userBoImpl.getAllUsers()));
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Deletion Failed");
                    alert2.setContentText("Failed to delete employee. Please try again.");
                    alert2.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Information");
            alert.setContentText("Please enter a valid Employee ID.");
            alert.showAndWait();
        }
    }

    public void closeBtnOnAction(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure want to exit..?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void logoutOnAction(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Are you sure want to logout..?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            sceneSwitch.switchScene(adminAnchor, "loginForm.fxml");
        }
    }

    public void tableMouseClickedAction(MouseEvent mouseEvent) {
        int index = employeeTable.getSelectionModel().getSelectedIndex();


        if(index < 0){
            return;
        }
        String id = empIdColumn.getCellData(index).toString();

        if (isAction){
            isEmailValid = true;
            User user = userBoImpl.getUserById(id);
            employeeIdField.setText(user.getId());
            employeeNameField.setText(user.getName());
            empEmailAddressField.setText(user.getEmail());
            empAddressField.setText(user.getAddress());
            byte[] data;

            if (!user.getId().equals("")){
                isMouseClick = true;
            }
        }
    }

    public void manageEmployeeAction(ActionEvent actionEvent) {
    }

    public void viewCustomersAction(ActionEvent actionEvent) {
    }

    public void viewSuppliersAction(ActionEvent actionEvent) {
    }

    public void viewProductsAction(ActionEvent actionEvent) {
    }

    public void viewOrders(ActionEvent actionEvent) {
    }

    public void imageClickAction(MouseEvent mouseEvent) {
       clearFields();
    }
}

