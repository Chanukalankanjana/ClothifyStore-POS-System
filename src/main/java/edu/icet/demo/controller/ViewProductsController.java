package edu.icet.demo.controller;

import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.impl.ProductBoImpl;
import edu.icet.demo.utill.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
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

    ProductBoImpl productBoImpl = new ProductBoImpl();
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    public void manageEmployeeAction(ActionEvent actionEvent) {
    }

    public void viewOrdersAction(ActionEvent actionEvent) {
    }

    public void viewProductsAction(ActionEvent actionEvent) {
    }

    public void viewCustomersAction(ActionEvent actionEvent) {
    }

    public void viewSuppliersAction(ActionEvent actionEvent) {
    }

    public void logoutOnAction(MouseEvent mouseEvent) {
    }

    public void closeAction(MouseEvent mouseEvent) {
    }

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
}
