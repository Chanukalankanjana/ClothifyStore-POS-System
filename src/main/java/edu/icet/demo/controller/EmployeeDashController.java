package edu.icet.demo.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import edu.icet.demo.bo.custom.impl.CustomerBoImpl;
import edu.icet.demo.bo.custom.impl.UserBoImpl;
import edu.icet.demo.model.Customer;
import edu.icet.demo.model.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class EmployeeDashController implements Initializable {
    public AnchorPane customerAnchor;
    public TextField customerIdField;
    public TextField customerNameField;
    public TextField cusEmailAddressField;
    public TextField cusAddressField;
    public TableView customerTable;
    public TableColumn cusIdColumn;
    public TableColumn cusNameColumn;
    public TableColumn cusEmailColumn;
    public TableColumn cusAddressColumn;
    public Button addCusBtn;
    public Button searchCusBtn;
    public Button updateCusBtn;
    public Button deleteCusBtn;
    public Rectangle employeeAnchor;

    CustomerBoImpl customerBoImpl = new CustomerBoImpl();

    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();
    boolean isAction = true,isEmailValid,isMouseClick;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cusIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        cusNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cusEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        cusAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        customerIdField.setText(customerBoImpl.generateCustomerId());
        customerTable.setItems(customerBoImpl.getAllCustomers());
    }

    public void addActionBtn(ActionEvent actionEvent) {

        Customer customer = new Customer(
                customerIdField.getText(),
                customerNameField.getText(),
                cusEmailAddressField.getText(),
                cusAddressField.getText()
        );

        if (!customerIdField.getText().isEmpty() && customerBoImpl.isValidEmail(cusEmailAddressField.getText()) && !cusAddressField.getText().isEmpty()) {
            boolean isInsert = customerBoImpl.insertCustomer(customer);

            if (isInsert) {
                customerTable.setItems(FXCollections.observableArrayList(customerBoImpl.getAllCustomers()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Customer Added");
                alert.setContentText("Customer added successfully!");
                alert.showAndWait();
                clearFields();
                customerIdField.setText(customerBoImpl.generateCustomerId());
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add customer. Please try again.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fill in all fields with valid data.").show();
        }
    }

    public void searchActionBtn(ActionEvent actionEvent) {
        Customer customer = customerBoImpl.getCustomerById(customerIdField.getText());
        if (customer != null) {
            customerNameField.setText(customer.getName());
            cusEmailAddressField.setText(customer.getEmail());
            cusAddressField.setText(customer.getAddress());
        } else {
            new Alert(Alert.AlertType.ERROR, "Customer not found.").show();
        }
    }

    public void updateActionBtn(ActionEvent actionEvent) {
        if (!customerNameField.getText().isEmpty() && !cusEmailAddressField.getText().isEmpty() && !cusAddressField.getText().isEmpty()) {
            Customer customer = new Customer(
                    customerIdField.getText(),
                    customerNameField.getText(),
                    cusEmailAddressField.getText(),
                    cusAddressField.getText()
            );

            boolean isUpdate = customerBoImpl.updateCustomer(customer);

            if (isUpdate) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Employee Update");
                alert.setContentText("Employee updated successfully.");
                alert.showAndWait();
                clearFields();
                customerTable.setItems(FXCollections.observableArrayList(customerBoImpl.getAllCustomers()));
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
        if (!customerIdField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure you want to delete this Customer?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean isDeleted = customerBoImpl.deleteCustomerById(customerIdField.getText());
                if (isDeleted) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Customer Deleted");
                    alert2.setContentText("Customer deleted successfully.");
                    alert2.showAndWait();
                    clearFields();
                    customerTable.setItems(FXCollections.observableArrayList(customerBoImpl.getAllCustomers()));
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

    private void clearFields() {
        customerIdField.clear();
        customerNameField.clear();
        cusEmailAddressField.clear();
        cusAddressField.clear();
    }

    public void manageOrdersAction(ActionEvent actionEvent) {
    }

    public void manageProductsAction(ActionEvent actionEvent) {
    }

    public void manageCustomersAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(customerAnchor,"employeeDash.fxml");
    }

    public void manageSuppliersAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(customerAnchor,"supplierManageForm.fxml");
    }

    public void reportGenAction(ActionEvent actionEvent) {
    }

    public void logoutAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Are you sure want to logout..?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            sceneSwitch.switchScene(customerAnchor, "loginForm.fxml");
        }
    }

    public void releaseEmailkey(KeyEvent keyEvent) {
        boolean isValidEmail = customerBoImpl.isValidEmail(cusEmailAddressField.getText());
        if (!isValidEmail) {
            addCusBtn.setDisable(true);
        } else {
            addCusBtn.setDisable(false);
        }
    }

    public void tableMouseClickedAction(MouseEvent mouseEvent) {
        int index = customerTable.getSelectionModel().getSelectedIndex();


        if(index < 0){
            return;
        }
        String id = cusIdColumn.getCellData(index).toString();

        if (isAction){
            isEmailValid = true;
            Customer customer = customerBoImpl.getCustomerById(id);
            customerIdField.setText(customer.getId());
            customerNameField.setText(customer.getName());
            cusEmailAddressField.setText(customer.getEmail());
            cusAddressField.setText(customer.getAddress());
            byte[] data;

            if (!customer.getId().equals("")){
                isMouseClick = true;
            }
        }
    }

    public void closeAction(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure you want exit...?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            System.exit(0);
        }
    }
}
