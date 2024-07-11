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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
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
    ExitOrClose exitOrClose = new ExitOrClose();
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();


    String category,supplierId,size;
    boolean isAction = true,isMouseClick,isPriceValid,isSupplierSelect,isCategorySelect,isSizeSelect;

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

    private void clearFields() {
        itemIdField.setText("");
        itemNameField.setText("");
        qtyField.setText("");
        itemPriceField.setText("");
    }

    private void initializeComboBoxes() {
        categoryCombo.setItems(categoryLoad());
        sizeCombo.setItems(sizeLoad());
        supIdCombo.setItems(FXCollections.observableArrayList(supplierId));
    }

    private void resetComboBoxes() {
        categoryCombo.getSelectionModel().clearSelection();
        sizeCombo.getSelectionModel().clearSelection();
        supIdCombo.getSelectionModel().clearSelection();
    }

    private void refreshProductTable() {
        itemTable.setItems(FXCollections.observableArrayList(productBoImpl.getAllProducts()));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetComboBoxes();
        initializeComboBoxes();
        itemIdField.setText(productBoImpl.generateProductId());
        refreshProductTable();

        supIdCombo.setItems(new SupplierBoImpl().getAllSupplierIds());

        isSupplierSelect = true;
        isPriceValid = true;
        isMouseClick = false;
        isCategorySelect = false;
        isSizeSelect = false;
        itemIdField.setText(productBoImpl.generateProductId());
        updateBtn.setVisible(true);
        deleteBtn.setVisible(true);

        categoryCombo.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> {
            category = (String) newValue;
            isCategorySelect=true;
            System.out.println(category);
        });

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
        exitOrClose.report();
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
                clearFields();
                itemIdField.setText(productBoImpl.generateProductId());
                itemTable.setItems(productBoImpl.getAllProducts());
                isSupplierSelect = false;
            }
        }
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
                alert.setContentText("Product Updated successfully");
                alert.showAndWait();
                clearFields();
                itemIdField.setText(productBoImpl.generateProductId());
                itemTable.setItems(productBoImpl.getAllProducts());
                isSupplierSelect = false;
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Something Missing").show();
        }
    }

    public void deleteAction(ActionEvent actionEvent) {
        if (isMouseClick) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure you want to delete this Product?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean isDelete = productBoImpl.deleteProductById(itemIdField.getText());

                if (isDelete) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Product Deleted");
                    alert1.setContentText("Product Deleted Successfully");
                    alert1.showAndWait();
                    clearFields();
                    itemIdField.setText(productBoImpl.generateProductId());
                    itemTable.setItems(productBoImpl.getAllProducts());
                    isSupplierSelect = false;

                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Deletion Failed");
                    alert1.setContentText("Failed to delete the product.");
                    alert1.showAndWait();
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


    public void rowMouseClicked(MouseEvent mouseEvent) {
        int index = itemTable.getSelectionModel().getSelectedIndex();


        if(index < 0){
            return;
        }
        String id = itemIdColumn.getCellData(index).toString();

        if (isAction){
            isPriceValid = true;
            Product product = productBoImpl.getProductById(id);
            itemIdField.setText(product.getId());
            itemNameField.setText(product.getName());
            itemPriceField.setText(Double.toString(product.getPrice()));
            qtyField.setText(Integer.toString(product.getQty()));
            categoryCombo.getSelectionModel().select(product.getCategory());
            sizeCombo.getSelectionModel().select(product.getSize());
            supIdCombo.getSelectionModel().select(product.getSupId());
            byte[] data;

            if (!product.getId().equals("")){
                isMouseClick = true;
            }
        }
    }

    public void manageEmployeeAction(ActionEvent actionEvent) {
    }

    public void viewProductsAction(ActionEvent actionEvent) {
    }

    public void viewCustomersAction(ActionEvent actionEvent) {
    }

    public void viewSuppliersAction(ActionEvent actionEvent) {
    }

    public void logoutOnAction(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Are you sure want to logout..?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            sceneSwitch.switchScene(productsAnchor, "loginForm.fxml");
        }
    }

    public void closeAction(MouseEvent mouseEvent) {
        exitOrClose.exit();
    }

    public void imageViewClicked(MouseEvent mouseEvent) {
        clearFields();
        itemIdField.setText(productBoImpl.generateProductId());
    }
}
