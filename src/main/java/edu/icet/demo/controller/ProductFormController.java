package edu.icet.demo.controller;

import edu.icet.demo.bo.custom.impl.ProductBoImpl;
import edu.icet.demo.bo.custom.impl.SupplierBoImpl;
import edu.icet.demo.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductFormController implements Initializable {
    public AnchorPane productsAnchor;
    public TextField itemIdField;
    public TextField itemNameField;
    public TextField qtyField;
    public TextField itemPriceField;
    public Button addBtn;
    public Button searchBtn;
    public Button updateBtn;
    public Button deleteBtn;
    public TableColumn itemIdColumn;
    public TableColumn itemNameColumn;
    public TableColumn itemQtyColumn;
    public TableColumn itemSizeColumn;
    public TableColumn itemPriceColumn;
    public ComboBox categoryCombo;
    public ComboBox sizeCombo;
    public TableColumn supplierIdColumn;
    public ComboBox supIdCombo;
    public TableColumn itemCategoryColumn;
    public TableView itemTable;

    ProductBoImpl productBoImpl = new ProductBoImpl();
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();


    String category,supplierId,size;
    boolean isAction,isMouseClick,isPriceValid,isSupplierSelect,isCategorySelect,isSizeSelect;

    private ObservableList<String> categoryLoad(){
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Gents");
        list.add("Ladies");
        list.add("Kids");
        list.add("Unisex");

        return list;
    }

    private ObservableList<String> sizeLoad(){
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("XS");
        list.add("S");
        list.add("M");
        list.add("L");
        list.add("XL");
        list.add("XXL");

        return list;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        supIdCombo.setItems(new SupplierBoImpl().getAllSupplierIds());

        isSupplierSelect = true;
        isPriceValid = true;
        isMouseClick = false;
        isCategorySelect = false;
        isSizeSelect = false;
        itemIdField.setText(productBoImpl.generateProductId());
        updateBtn.setVisible(true);
        deleteBtn.setVisible(true);
        categoryCombo.setItems(categoryLoad());
        categoryCombo.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> {
            category = (String) newValue;
            isCategorySelect=true;
            System.out.println(category);
        });
        sizeCombo.setItems(sizeLoad());
        sizeCombo.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> {
            size = (String) newValue;
            isSizeSelect=true;
            System.out.println(size);
        });
        supIdCombo.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> {
            isSupplierSelect = true;
            supplierId = (String) newValue;

        });
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemQtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        itemSizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        supplierIdColumn.setCellValueFactory(new PropertyValueFactory<>("supId"));

        itemTable.setItems(productBoImpl.getAllProducts());
    }

    public void manageOrdersAction(ActionEvent actionEvent) {
    }

    public void manageProductsAction(ActionEvent actionEvent) {
    }

    public void manageCustomersAction(ActionEvent actionEvent) {
    }

    public void manageSuppliersAction(ActionEvent actionEvent) {
    }

    public void reportGenAction(ActionEvent actionEvent) {
    }

    public void logoutAction(ActionEvent actionEvent) {
    }

    public void addAction(ActionEvent actionEvent) throws Exception {
        if (isSupplierSelect && isPriceValid && !itemNameField.getText().equals("") && !qtyField.getText().equals("")) {
            Product product = new Product(
                    itemIdField.getText(),
                    itemNameField.getText(),
                    Integer.parseInt(qtyField.getText()),
                    category,
                    size,
                    Double.parseDouble(itemPriceField.getText()),
                    supplierId);

            boolean isInsert = productBoImpl.insertProduct(product);

            if (isInsert) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product Added");
                alert.setContentText("Product Added successfully");
                alert.showAndWait();
                itemIdField.setText(productBoImpl.generateProductId());
                itemNameField.setText("");
                qtyField.setText("");
                itemPriceField.setText("");
                itemTable.setItems(productBoImpl.getAllProducts());
                isSupplierSelect = false;
            }
        }
    }

    public void searchAction(ActionEvent actionEvent) {
    }

    public void updateAction(ActionEvent actionEvent) {
        if (isSupplierSelect && isCategorySelect && isPriceValid && isMouseClick && !itemNameField.getText().equals("") && !qtyField.getText().equals("")) {
            Product product = new Product(
                    itemIdField.getText(),
                    itemNameField.getText(),
                    Integer.parseInt(qtyField.getText()),
                    category,
                    size,
                    Double.parseDouble(itemPriceField.getText()),
                    supplierId);
            boolean isUpdate = productBoImpl.updateProduct(product);

            if (isUpdate){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product Updated");
                alert.setContentText("Product Updated Successfully..!!!");
                alert.showAndWait();
                itemIdField.setText("");
                itemNameField.setText("");
                qtyField.setText("");
                itemTable.setItems(productBoImpl.getAllProducts());
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Something Missing").show();
        }
    }

    public void deleteAction(ActionEvent actionEvent) {
        if (isMouseClick){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure want to delete this Product");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get()==ButtonType.OK){
                boolean isDelete = productBoImpl.deleteProductById(itemIdField.getText());

                if (isDelete){
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Product Deleted");
                    alert1.setContentText("Product Deleted Successfully");
                    alert1.showAndWait();
                    itemIdField.setText("");
                    itemNameField.setText("");
                    qtyField.setText("");
                    itemPriceField.setText("");
                    itemTable.setItems(productBoImpl.getAllProducts());
                }
            }
        }
    }

    public void categoryComboAction(ActionEvent actionEvent) {
    }

    public void sizeComboAction(ActionEvent actionEvent) {
    }

    public void supIdComboAction(ActionEvent actionEvent) {
    }


}
