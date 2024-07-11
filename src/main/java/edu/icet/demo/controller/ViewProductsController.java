package edu.icet.demo.controller;

import edu.icet.demo.bo.custom.impl.ProductBoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewProductsController implements Initializable {
    public TableColumn itemIdColumn;
    public TableColumn supIdColumn;
    public TableColumn itemNameColumn;
    public TableColumn itemQtyColumn;
    public TableColumn itemSizeColumn;
    public TableColumn itemCategColumn;
    public TableColumn itemPriceColumn;
    public TableView itemTable;
    public AnchorPane viewProductsAnchor;

    ProductBoImpl productBoImpl = new ProductBoImpl();
    ExitOrClose exitOrClose = new ExitOrClose();
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        supIdColumn.setCellValueFactory(new PropertyValueFactory<>("supId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemQtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        itemCategColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        itemSizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        itemTable.setItems(productBoImpl.getAllProducts());

    }

    public void manageEmployeeAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewProductsAnchor,"adminDash.fxml");
    }

    public void viewOrdersAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewProductsAnchor,"viewOrders.fxml");
    }

    public void viewProductsAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewProductsAnchor,"viewProducts.fxml");
    }

    public void viewCustomersAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewProductsAnchor,"viewCustomer.fxml");
    }

    public void viewSuppliersAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(viewProductsAnchor,"supplierManageForm.fxml");
    }

    public void logoutOnAction(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Are you sure want to logout..?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            sceneSwitch.switchScene(viewProductsAnchor,"loginForm.fxml");
        }
    }

    public void closeAction(MouseEvent mouseEvent) {
        exitOrClose.exit();
    }
}
