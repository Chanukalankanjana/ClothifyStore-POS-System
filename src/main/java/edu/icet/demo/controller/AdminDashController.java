package edu.icet.demo.controller;

import edu.icet.demo.bo.custom.impl.UserBoImpl;
import edu.icet.demo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

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

    UserBoImpl userBoImpl = new UserBoImpl();
    String selectedId;
    SceneSwitchController sceneSwitchController = SceneSwitchController.getInstance();
    @Override
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
        int pass = random.nextInt(99999999) + 10000000;

        String encrypt = Integer.toString(pass);
        String passwprd = userBoImpl.passwordEncrypt(encrypt);

        User user = new User(empIdField.getText(),
                empNameField.getText(),
                empEmailField.getText(),
                passwprd,
                "Employee",
                empAddressField.getText());

        if (!empNameField.getText().equals("") && userBoImpl.isValidEmail(empEmailField.getText()) && !empAddressField.getText().equals("")){

            boolean isInsert = userBoImpl.insertUser(user);

            if (isInsert){
                employeeTable.setItems(userBoImpl.getAllUsers());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Employee Added");
                alert.setContentText("Employee Added Successfully..!");
                alert.showAndWait();
                empIdField.setText(userBoImpl.generateEmployeeId());
                empAddressField.setText("");
                empEmailField.setText("");
                empNameField.setText("");
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Somthing Wrong..!!!").show();
        }
    }


    public void ViewActionBtn(ActionEvent actionEvent) {
        User user = userBoImpl.getUserById(empIdField.getText());
                empNameField.setText(user.getName());
                empEmailField.setText(user.getEmail());
                empAddressField.setText(user.getAddress());
    }

    public  void clear(){
        empIdField.setText("");
        empNameField.setText("");
        empEmailField.setText("");
        empAddressField.setText("");
    }


    public void UpdateActionBtn(ActionEvent actionEvent) {
        if (!empNameField.getText().equals("") && !empEmailField.getText().equals("") && empAddressField.getText().equals("")){
            User user = new User(empIdField.getText(),
                    empNameField.getText(),
                    empEmailField.getText(),
                    null,
                    null,
                    empAddressField.getText());

            boolean isUpdate = userBoImpl.updateUser(user);

            if (isUpdate){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Employee Update");
                alert.setContentText("Employee Update Successfully");
                alert.showAndWait();
                empNameField.setText("");
                empEmailField.setText("");
                empAddressField.setText("");
                employeeTable.setItems(userBoImpl.getAllUsers());
                clear();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something Missing");
            alert.setContentText("Please Check your Form again..!!!");
            alert.showAndWait();
        }
    }

    public void DeleteActionBtn(ActionEvent actionEvent) {
        if (!empIdField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure want to delete this Employee");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get()== ButtonType.OK){
                boolean isDeleted = userBoImpl.deleteUserById(empIdField.getText());
                if (isDeleted){
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Employee Deleted");
                    alert2.setContentText("Employee deleted successfully");
                    alert2.showAndWait();
                    employeeTable.setItems(userBoImpl.getAllUsers());
                    empEmailField.setText("");
                    empAddressField.setText("");
                    empNameField.setText("");
                    clear();
                }
            }
        }
    }


    public void ManageEmpBtn(ActionEvent actionEvent) {
    }

    public void ViewOrderBtn(ActionEvent actionEvent) {
    }

    public void ViewProductsBtn(ActionEvent actionEvent) {
    }

    public void ViewCustomerBtn(ActionEvent actionEvent) {
    }

    public void ViewSupplierBtn(ActionEvent actionEvent) {
    }

    public void LogoutBtn(ActionEvent actionEvent) {
    }


    public void releaseEmailkey(KeyEvent keyEvent) {
        boolean isValidEmail = userBoImpl.isValidEmail(empEmailField.getText());
        if (!isValidEmail) {
            addEmployeeBtn.setVisible(true);
        } else {
            addEmployeeBtn.setDisable(false);
        }
    }
}
