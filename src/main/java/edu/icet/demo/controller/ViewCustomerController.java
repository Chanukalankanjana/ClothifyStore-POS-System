package edu.icet.demo.controller;

import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.impl.CustomerBoImpl;
import edu.icet.demo.utill.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewCustomerController implements Initializable {
    public AnchorPane viewCusAnchor;
    public Button manageEmpBtn;
    public Button viewOrderBtn;
    public Button viewProductsBtn;
    public Button viewCustomersBtn;
    public Button viewSuppliersBtn;
    public TableView viewCustomerTable;
    public TableColumn cusIdColumn;
    public TableColumn cusNameColumn;
    public TableColumn cusEmailColumn;
    public TableColumn cusAddresscoIumn;
    public Button logoutBtn;


    CustomerBoImpl customerBoImpl= BoFactory.getInstance().getBo(BoType.CUSTOMER);
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cusIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        cusNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cusEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        cusAddresscoIumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        viewCustomerTable.setItems(customerBoImpl.getAllCustomers());



    }
    public void manageEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewCusAnchor,"adminDash.fxml");
    }

    public void viewOrdersOnAction(ActionEvent actionEvent) {
    }

    public void viewProductsOnAction(ActionEvent actionEvent) {
    }

    public void viewCustomersOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewCusAnchor,"viewCustomer.fxml");
    }

    public void viewSuppliersOnAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewCusAnchor,"viewSuppliers.fxml");
    }


    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Are you sure want to logout..?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            sceneSwitch.switchScene(viewCusAnchor,"loginForm.fxml");
        }
    }
}
