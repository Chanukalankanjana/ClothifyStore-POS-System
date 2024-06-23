package edu.icet.demo.controller;

import edu.icet.demo.bo.custom.impl.UserBoImpl;
import edu.icet.demo.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    public TableColumn IdColumn;
    public TableColumn NameColumn;
    public TableColumn EmailColumn;
    public TableColumn AddressColumn;
    public AnchorPane adminAnchor;
    public TextField empNameField;
    public TextField empEmailField;
    public TextField empAddressField;
    public Button deleteBtn;
    public Button addEmployeeBtn;
    public Button updateBtn;
    public TableView employeeTable;
    public TextField empIdField;
    public Button logoutBtn;
    public Button viewSuppliersBtn;
    public Button viewCustomersBtn;
    public Button viewProductsBtn;
    public Button viewOrderBtn;
    public Button manageEmpBtn;

    UserBoImpl userBoImpl = new UserBoImpl();
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    boolean isAction = true,isEmailValid,isMouseClick;
    public void initialize(URL url, ResourceBundle resourceBundle) {

        IdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        empIdField.setText(userBoImpl.generateEmployeeId());
        employeeTable.setItems(userBoImpl.getAllUsers());

    }

    public void AddActionBtn(ActionEvent actionEvent) {
        Random random = new Random();
        int pass = random.nextInt(90000000) + 10000000; // Generates 8 digit number
        String encrypt = Integer.toString(pass);
        String password = userBoImpl.passwordEncrypt(encrypt);

        User user = new User(
                empIdField.getText(),
                empNameField.getText(),
                empEmailField.getText(),
                password,
                "Employee",
                empAddressField.getText()
        );

        if (!empNameField.getText().isEmpty() && userBoImpl.isValidEmail(empEmailField.getText()) && !empAddressField.getText().isEmpty()) {
            boolean isInsert = userBoImpl.insertUser(user);

            if (isInsert) {
                employeeTable.setItems(FXCollections.observableArrayList(userBoImpl.getAllUsers()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Employee Added");
                alert.setContentText("Employee added successfully!");
                alert.showAndWait();
                clearFields();
                empIdField.setText(userBoImpl.generateEmployeeId());
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add employee. Please try again.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fill in all fields with valid data.").show();
        }
    }

    public void ViewActionBtn(ActionEvent actionEvent) {
        User user = userBoImpl.getUserById(empIdField.getText());
        if (user != null) {
            empNameField.setText(user.getName());
            empEmailField.setText(user.getEmail());
            empAddressField.setText(user.getAddress());
        } else {
            new Alert(Alert.AlertType.ERROR, "Employee not found.").show();
        }
    }

    public void UpdateActionBtn(ActionEvent actionEvent) {
        if (!empNameField.getText().isEmpty() && !empEmailField.getText().isEmpty() && !empAddressField.getText().isEmpty()) {
            User user = new User(
                    empIdField.getText(),
                    empNameField.getText(),
                    empEmailField.getText(),
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

    public void DeleteActionBtn(ActionEvent actionEvent) {
        if (!empIdField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure you want to delete this Employee?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean isDeleted = userBoImpl.deleteUserById(empIdField.getText());
                if (isDeleted) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Employee Deleted");
                    alert2.setContentText("Employee deleted successfully.");
                    alert2.showAndWait();
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

    public void clearFields() {
        empIdField.clear();
        empNameField.clear();
        empEmailField.clear();
        empAddressField.clear();
    }

    public void releaseEmailkey(KeyEvent keyEvent) {
        boolean isValidEmail = userBoImpl.isValidEmail(empEmailField.getText());
        if (!isValidEmail) {
            addEmployeeBtn.setDisable(true);
        } else {
            addEmployeeBtn.setDisable(false);
        }
    }


    public void LogoutAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Are you sure want to logout..?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            sceneSwitch.switchScene(adminAnchor,"loginForm.fxml");
        }
    }

    public void viewSuppliersAction(ActionEvent actionEvent) {
    }

    public void viewCustomersAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(adminAnchor,"viewCustomer.fxml");
    }

    public void viewProductsAction(ActionEvent actionEvent) {
    }

    public void viewOrderAction(ActionEvent actionEvent) {
    }

    public void manageEmpAction(ActionEvent actionEvent) {
    }

    public void tableMouseClickedAction(MouseEvent mouseEvent) {
            int index = employeeTable.getSelectionModel().getSelectedIndex();


            if(index < 0){
                return;
            }
            String id = IdColumn.getCellData(index).toString();

        if (isAction){
            isEmailValid = true;
            User user = userBoImpl.getUserById(id);
            empIdField.setText(user.getId());
            empNameField.setText(user.getName());
            empEmailField.setText(user.getEmail());
            empAddressField.setText(user.getAddress());
            byte[] data;

            if (!user.getId().equals("")){
                    isMouseClick = true;
            }
        }
    }
}

