package edu.icet.demo.controller;

import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.impl.CustomerBoImpl;
import edu.icet.demo.bo.custom.impl.OrderDetailsBoImpl;
import edu.icet.demo.bo.custom.impl.OrderBoImpl;
import edu.icet.demo.bo.custom.impl.SupplierBoImpl;
import edu.icet.demo.model.*;
import edu.icet.demo.utill.BoType;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewOrdersController implements Initializable {
    public AnchorPane viewOrderAnchor;
    public TableColumn orderIdColumn;
    public TableColumn itemIdColumn;
    public TableColumn qtyColumn;
    public TableColumn amountColumn;
    public TextField customerIdField;
    public TextField customerNameField;
    public TextField emailAddressField;
    public TextField categoryField;
    public TextField unitPriceField;
    public TextField totalValueField;
    public TextField availableQtyField;
    public TableView cartTable;
    public TableColumn itemNameColumn;
    public TextField cusAddressField;
    public TextField orderDateField;
    public TextField brandNameField;
    public TextField supIdField;
    public Button closeBtn;

    OrderDetailsBoImpl orderDetailsBoImpl = BoFactory.getInstance().getBo(BoType.PLACE);
    OrderBoImpl orderBoImpl = BoFactory.getInstance().getBo(BoType.ORDER);
    CustomerBoImpl customerBoImpl = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    SupplierBoImpl supplierBoImpl = BoFactory.getInstance().getBo(BoType.SUPPLIER);
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    private boolean isRowSelect;
    int index, selectedRowQty;
    String id;
    String selectedItemIdCol;
    double selectedTotalValue;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        String id = EmployeeData.getInstance().getId();
        System.out.println(id);
        cartTable.setItems(orderDetailsBoImpl.getAllOrderedProductsByEmpId(id));

        cartTable.setItems(orderDetailsBoImpl.getAllOrderedProducts());

        isRowSelect = false;
    }

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

    public void logoutAction(ActionEvent actionEvent) {
    }

    public void onCloseAction(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure want to exit..?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void tableOnClick(MouseEvent mouseEvent) {
        int index = cartTable.getSelectionModel().getSelectedIndex();

        try {
            isRowSelect = true;
            selectedItemIdCol = itemNameColumn.getCellData(index).toString();
            String orderId = orderIdColumn.getCellData(index).toString();
            String itemId = itemIdColumn.getCellData(index).toString();
            Product product = orderDetailsBoImpl.getItemById(itemId);
            selectedRowQty = (int) qtyColumn.getCellData(index);
            selectedTotalValue = (double) amountColumn.getCellData(index);

            id = orderId;

            Order order = orderBoImpl.getOrderById(orderId);
            Customer customer = customerBoImpl.getCustomerById(order.getCusId());
            Supplier supplier = supplierBoImpl.getSupplierById(product.getSupId());

            cusAddressField.setText(customer.getAddress());
            customerIdField.setText(customer.getId());
            emailAddressField.setText(customer.getEmail());
            customerNameField.setText(customer.getName());

            orderDateField.setText(String.valueOf(order.getDate()));
            totalValueField.setText("Rs. "+Double.toString(order.getAmount())+"0");

            supIdField.setText(supplier.getId());
            brandNameField.setText(supplier.getCompany());
            categoryField.setText(product.getCategory());
            unitPriceField.setText("Rs. "+Double.toString(product.getPrice())+"0");
        } catch (Exception e){}
    }
}
