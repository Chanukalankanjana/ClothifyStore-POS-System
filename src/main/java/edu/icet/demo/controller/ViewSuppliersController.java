package edu.icet.demo.controller;

import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.impl.CustomerBoImpl;
import edu.icet.demo.bo.custom.impl.SupplierBoImpl;
import edu.icet.demo.utill.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewSuppliersController implements Initializable {
    public AnchorPane viewSupAnchor;
    public TableView supplierTable;
    public TableColumn supIdColumn;
    public TableColumn supNameColumn;
    public TableColumn supEmailColumn;
    public TableColumn supCompanyColumn;

    SupplierBoImpl supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        supIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        supNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        supEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        supCompanyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));

        supplierTable.setItems(supplierBo.getAllSuppliers());

    }
    public void manageEmployeeAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewSupAnchor,"adminDash.fxml");
    }

    public void viewOrdersAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewSupAnchor,"viewOrders.fxml");
    }

    public void viewProductsAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewSupAnchor,"viewProducts.fxml");
    }

    public void viewSuppliersAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewSupAnchor,"viewSuppliers.fxml");
    }


    public void viewCustomersAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewSupAnchor,"viewCustomer.fxml");
    }

    public void logoutOnAction(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Are you sure want to logout..?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            sceneSwitch.switchScene(viewSupAnchor,"loginForm.fxml");
        }
    }

    public void closeAction(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure want to exit..?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
