package edu.icet.demo.controller;

import edu.icet.demo.bo.custom.impl.CustomerBoImpl;
import edu.icet.demo.bo.custom.impl.OrderBoImpl;
import edu.icet.demo.bo.custom.impl.OrderDetailsBoImpl;
import edu.icet.demo.bo.custom.impl.ProductBoImpl;
import edu.icet.demo.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class PlaceOrderController implements Initializable {
    public AnchorPane orderAnchor;
    public Button mangeOrderBtn;
    public Button manageProductsBtn;
    public Button manageCusBtn;
    public Button manageSupBtn;
    public Button addOrderBtn;
    public Button placeOrderBtn;
    public Button clearOrderBtn;
    public ComboBox cusIdCombo;
    public ComboBox itemIdCombo;
    public TextField timeField;
    public TextField dateField;
    public TextField orderIdField;
    public TextField cusNameField;
    public TextField cusEmailField;
    public TextField cusAddressField;
    public TextField itemNameField;
    public TextField categoryField;
    public TextField priceField;
    public TextField sizeField;
    public TextField qtyField;
    public TableView orderTable;
    public TableColumn itemCodeColumn;
    public TableColumn descriptionColumn;
    public TableColumn qtyColumn;
    public TableColumn unityPriceColumn;
    public TableColumn totalColumn;
    public TextField netValueField;
    public Button closeBtn;

    private String empId;

    OrderBoImpl orderBoImpl = new OrderBoImpl();
    OrderDetailsBoImpl orderDetailsBoImpl = new OrderDetailsBoImpl();
    ProductBoImpl productBoImpl = new ProductBoImpl();
    CustomerBoImpl customerBoImpl = new CustomerBoImpl();
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    ObservableList<Product> productList = FXCollections.observableArrayList();
    private void setItemDataFroLbl(String ItemCode) {

        Product product = productBoImpl.getProductById(ItemCode);
        itemNameField.setText(product.getName());
        priceField.setText(String.valueOf(product.getPrice()));
        sizeField.setText(product.getSize());

    }

    private void setCustomerDataFroLbl(String newValue) {

        Customer customer = customerBoImpl.getCustomerById(newValue);
        cusNameField.setText(customer.getName());
        cusEmailField.setText(customer.getEmail());
        cusAddressField.setText(customer.getAddress());
        empId = customer.getId();
    }

    private void loadItemCodes() {

        ObservableList<Product> allSupplier = productBoImpl.getAllProducts();
        ObservableList ids = FXCollections.observableArrayList();

        allSupplier.forEach(supplier -> {
            ids.add(supplier.getId());

        });
        itemIdCombo.setItems(ids);
    }

    private void loadCustomerIDs() {

        ObservableList<Customer> allSupplier = customerBoImpl.getAllCustomers();
        ObservableList ids = FXCollections.observableArrayList();

        allSupplier.forEach(supplier -> {
            ids.add(supplier.getId());

        });
        cusIdCombo.setItems(ids);

    }

    private void loadDateAndTime() {

        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        dateField.setText(f.format(date));


        //Time
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime time = LocalTime.now();
            timeField.setText(
                    time.getHour() + " : " + time.getMinute() + " : " + time.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    ObservableList<CartTable> orderList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadDateAndTime();
        loadCustomerIDs();
        loadItemCodes();

        itemCodeColumn.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("desc"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        unityPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        cusIdCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCustomerDataFroLbl((String) newValue);
        });
        itemIdCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setItemDataFroLbl((String) newValue);
        });

        orderIdField.setText(orderDetailsBoImpl.generateOrderId());
        productList = orderDetailsBoImpl.getAllProducts();

    }

    public void manageOrdersAction(ActionEvent actionEvent) {}

    public void manageProductsAction(ActionEvent actionEvent) {}

    public void manageCustomersAction(ActionEvent actionEvent) {}

    public void manageSuppliersAction(ActionEvent actionEvent) {}

    public void reportGenAction(ActionEvent actionEvent) {}

    public void logoutAction(ActionEvent actionEvent) {}

    public void addActionBtn(ActionEvent actionEvent) {
        String itemCode = (String) itemIdCombo.getValue();
        String desc = itemNameField.getText();
        Integer qtyFroCus = Integer.parseInt(qtyField.getText());
        Double unitPrice = Double.valueOf(priceField.getText());
        Double total = qtyFroCus * unitPrice;
        CartTable cartTable = new CartTable(itemCode, desc, qtyFroCus, unitPrice, total);
        System.out.println(cartTable);

        orderList.add(cartTable);
        orderTable.setItems(orderList);
        calcNetTotal();
    }

    double total = 0;
    private void calcNetTotal() {
        total = 0;
        for (CartTable orderObj : orderList) {
            total += orderObj.getTotal();
        }
        netValueField.setText(String.valueOf(total) + "/=");
    }

    public void placeOrderAction(ActionEvent actionEvent) throws ParseException, IOException {
        String id = orderIdField.getText();
        String cusId =cusIdCombo.getValue().toString();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date orderDate = format.parse(dateField.getText());
        double amount = total;

        ObservableList<OrderDetails> orderDetailsObservableList =FXCollections.observableArrayList();

        for (CartTable cartTable : orderList) {
            String oId = orderIdField.getText();
            String itemCode = cartTable.getItemCode();
            Integer qty = cartTable.getQty();
            String itemName = cartTable.getDesc();
            Double tamount = cartTable.getTotal();
            orderDetailsObservableList.add(new OrderDetails(null,oId,itemName,qty,tamount,itemCode));
        }

        orderDetailsBoImpl.saveOrderDetails(orderDetailsObservableList);

        Order order = new Order(id,cusId,orderDate,amount,empId);

        boolean isInsert = orderBoImpl.saveOrder(order);
        if (isInsert) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Placed");
            alert.setContentText("Order Placed Successfully..!");
            alert.showAndWait();

            productList = orderDetailsBoImpl.getAllProducts();
            clearFields();
            orderIdField.setText(orderDetailsBoImpl.generateOrderId());

        } else {
            new Alert(Alert.AlertType.ERROR, "Somthing Wrong..!!!").show();
        }
    }

    private void clearFields() {
        cusNameField.setText("");
        cusEmailField.setText("");
        cusAddressField.setText("");
        itemNameField.setText("");
        categoryField.setText("");
        priceField.setText("");
        sizeField.setText("");
        qtyField.setText("");
        netValueField.setText("");
        orderIdField.setText("");
        orderTable.setItems(null);
    }

    public void clearActionBtn(ActionEvent actionEvent) {}

    public void closeAction(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure want to exit..?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void updateActionBtn(ActionEvent actionEvent) {
    }
}


