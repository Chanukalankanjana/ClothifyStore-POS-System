package edu.icet.demo.controller;

import edu.icet.demo.bo.custom.impl.UserBoImpl;
import edu.icet.demo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
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
    public ComboBox empIdField;
    public Text empIdText;
    public Text empIdLabel;
    public Button deleteBtn;
    public Button addEmployeeBtn;
    public Button updateBtn;
    public TableView employeeTable;

    UserBoImpl userBoImpl = new UserBoImpl();
    String selectedId;
    SceneSwitchController sceneSwitchController = SceneSwitchController.getInstance();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        empIdField.setVisible(false);
        empIdField.getSelectionModel().selectedItemProperty().addListener((observableValue,oldValue,newValue) -> {

            User user = userBoImpl.getUserById((String) newValue);

            empNameField.setText(user.getName());
            empAddressField.setText(user.getAddress());
            empEmailField.setText(user.getEmail());

            selectedId = (String) newValue;

        });

        empIdText.setText(userBoImpl.generateEmployeeId());
        updateBtn.setVisible(false);
        deleteBtn.setVisible(false);

        IdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        employeeTable.setItems(userBoImpl.getAllUsers());
    }

    public void AddActionBtn(ActionEvent actionEvent) {

        Random random = new Random();
        int pass = random.nextInt(99999999) + 10000000;

        String encrypt = Integer.toString(pass);
        String passwprd = userBoImpl.passwordEncrypt(encrypt);

        User user = new User(empIdText.getText(),
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
                empIdText.setText(userBoImpl.generateEmployeeId());
                empAddressField.setText("");
                empEmailField.setText("");
                empNameField.setText("");
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Somthing Wrong..!!!").show();
        }
    }

    public void UpdateActionBtn(ActionEvent actionEvent) {
    }

    public void ViewActionBtn(ActionEvent actionEvent) {
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

    public void DeleteActionBtn(ActionEvent actionEvent) {
    }



}
