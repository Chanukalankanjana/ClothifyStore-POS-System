package edu.icet.demo.controller;

import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.impl.CustomerBoImpl;
import edu.icet.demo.model.Customer;
import edu.icet.demo.utill.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewCustomerController implements Initializable {
    public TableColumn cusIdColumn;
    public TableColumn cusNameColumn;
    public TableColumn cusEmailColumn;
    public AnchorPane viewCustomerAnchor;
    public TextField customerIdField;
    public TextField customerNameField;
    public TextField cusEmailAddressField;
    public TextField cusAddressField;
    public TableView customerTable;
    public TableColumn cusAddressColumn;


    CustomerBoImpl customerBoImpl= BoFactory.getInstance().getBo(BoType.CUSTOMER);
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    boolean isAction = true,isMouseClick;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cusIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        cusNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cusEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        cusAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        customerTable.setItems(customerBoImpl.getAllCustomers());

    }

    public void manageEmployeeAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewCustomerAnchor,"adminDash.fxml");
    }

    public void viewOrdersAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewCustomerAnchor,"viewOrders.fxml");
    }

    public void viewProductsAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewCustomerAnchor,"viewOrders.fxml");
    }

    public void viewCustomersAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewCustomerAnchor,"viewCustomer.fxml");
    }


    public void viewSuppliersAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewCustomerAnchor,"viewSuppliers.fxml");
    }
//
//    public void tableMouseClickedAction(MouseEvent mouseEvent) {
//        int index = customerTable.getSelectionModel().getSelectedIndex();
//
//
//        if(index < 0){
//            return;
//        }
//        String id = cusIdColumn.getCellData(index).toString();
//
//        if (isAction){
//            Customer customer = customerBoImpl.getCustomerById(id);
//            customerIdField.setText(customer.getId());
//            customerNameField.setText(customer.getName());
//            cusEmailAddressField.setText(customer.getEmail());
//            cusAddressField.setText(customer.getAddress());
//            byte[] data;
//
//            if (!customer.getId().equals("")){
//                isMouseClick = true;
//            }
//        }
//    }

    public void logoutOnAction(MouseEvent mouseEvent) throws IOException {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setContentText("Are you sure want to logout..?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                sceneSwitch.switchScene(viewCustomerAnchor,"loginForm.fxml");
            }
    }
//
//    public void releaseEmailkey(KeyEvent keyEvent) {
//    }

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
