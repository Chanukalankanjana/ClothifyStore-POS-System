package edu.icet.demo.controller;

import edu.icet.demo.bo.custom.impl.CustomerBoImpl;
import edu.icet.demo.bo.custom.impl.SupplierBoImpl;
import edu.icet.demo.model.Customer;
import edu.icet.demo.model.Supplier;
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
import java.util.ResourceBundle;

public class SupplierManageController implements Initializable {
    public AnchorPane supplierAnchor;
    public TextField supplierNameField;
    public TextField supplierEmailAddressField;
    public TableView supplierTable;
    public TableColumn supIdColumn;
    public TableColumn supNameColumn;
    public TableColumn supEmailColumn;
    public Button addSupBtn;
    public Button updateSupBtn;
    public Button deleteSupBtn;
    public TextField companyField;
    public TableColumn supCompanyColumn;
    public TextField supplierIdField;

    SupplierBoImpl supplierBoImpl = new SupplierBoImpl();
    ExitOrClose exitOrClose = new ExitOrClose();
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();
    boolean isAction = true,isEmailValid,isMouseClick;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        supIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        supNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        supEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        supCompanyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));

        supplierIdField.setText(supplierBoImpl.generateSupplierId());
        supplierTable.setItems(supplierBoImpl.getAllSuppliers());
    }
    public void manageOrdersAction(ActionEvent actionEvent) {
    }

    public void manageProductsAction(ActionEvent actionEvent) {
    }

    public void manageCustomersAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(supplierAnchor,"employeeDash.fxml");
    }

    public void manageSuppliersAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(supplierAnchor,"supplierManageForm.fxml");
    }

    public void reportGenAction(ActionEvent actionEvent) {
        exitOrClose.report();
    }

    public void releaseEmailkey(KeyEvent keyEvent) {
        boolean isValidEmail = supplierBoImpl.isValidEmail(supplierEmailAddressField.getText());
        if (!isValidEmail) {
            addSupBtn.setDisable(true);
        } else {
            addSupBtn.setDisable(false);
        }
    }

    public void tableMouseClickedAction(MouseEvent mouseEvent) {
        int index = supplierTable.getSelectionModel().getSelectedIndex();


        if(index < 0){
            return;
        }
        String id = supIdColumn.getCellData(index).toString();


        if (isAction){
            isEmailValid = true;
            Supplier supplier = supplierBoImpl.getSupplierById(id);
            supplierIdField.setText(supplier.getId());
            supplierNameField.setText(supplier.getName());
            supplierEmailAddressField.setText(supplier.getEmail());
            companyField.setText(supplier.getCompany());
            byte[] data;

            if (!supplier.getId().equals("")){
                isMouseClick = true;
            }
        }
    }

    public void addActionBtn(ActionEvent actionEvent) {
        Supplier supplier = new Supplier(
                supplierIdField.getText(),
                supplierNameField.getText(),
                supplierEmailAddressField.getText(),
                companyField.getText()
        );

        if (!supplierIdField.getText().isEmpty() && supplierBoImpl.isValidEmail(supplierEmailAddressField.getText()) && !companyField.getText().isEmpty()) {
            boolean isInsert = supplierBoImpl.insertSupplier(supplier);

            if (isInsert) {
                supplierTable.setItems(FXCollections.observableArrayList(supplierBoImpl.getAllSuppliers()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Supplier Added");
                alert.setContentText("Supplier added successfully!");
                alert.showAndWait();
                clearFields();
                supplierIdField.setText(supplierBoImpl.generateSupplierId());
                supplierTable.setItems(supplierBoImpl.getAllSuppliers());

            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add supplier. Please try again.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fill in all fields with valid data.").show();
        }
    }

    private void clearFields() {
        supplierIdField.clear();
        supplierNameField.clear();
        supplierEmailAddressField.clear();
        companyField.clear();
    }

    public void updateActionBtn(ActionEvent actionEvent) {
        if (!supplierNameField.getText().isEmpty() && !supplierEmailAddressField.getText().isEmpty() && !companyField.getText().isEmpty()) {
            Supplier supplier = new Supplier(
                    supplierIdField.getText(),
                    supplierNameField.getText(),
                    supplierEmailAddressField.getText(),
                    companyField.getText()
            );

            boolean isUpdate = supplierBoImpl.updateSupplier(supplier);

            if (isUpdate) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Supplier Update");
                alert.setContentText("Supplier updated successfully.");
                alert.showAndWait();
                clearFields();
                supplierIdField.setText(supplierBoImpl.generateSupplierId());
                supplierTable.setItems(FXCollections.observableArrayList(supplierBoImpl.getAllSuppliers()));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Update Failed");
                alert.setContentText("Failed to update supplier. Please try again.");
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
        if (!supplierIdField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure you want to delete this Supplier?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                boolean isDeleted = supplierBoImpl.deleteSupplierById(supplierIdField.getText());
                if (isDeleted) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Suppplier Deleted");
                    alert2.setContentText("Supplier deleted successfully.");
                    alert2.showAndWait();
                    clearFields();
                    supplierIdField.setText(supplierBoImpl.generateSupplierId());
                    supplierTable.setItems(FXCollections.observableArrayList(supplierBoImpl.getAllSuppliers()));
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
            alert.setContentText("Please enter a valid Supplier ID.");
            alert.showAndWait();
        }

    }

    public void imageViewAction(MouseEvent mouseEvent) {
        clearFields();
        supplierIdField.setText(supplierBoImpl.generateSupplierId());
    }

    public void logoutOnAction(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Are you sure want to logout..?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            sceneSwitch.switchScene(supplierAnchor, "loginForm.fxml");
        }
    }

    public void closeAction(MouseEvent mouseEvent) {
        exitOrClose.exit();
    }
}
