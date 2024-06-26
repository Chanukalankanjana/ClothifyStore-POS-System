package edu.icet.demo.controller;

import edu.icet.demo.bo.custom.impl.CustomerBoImpl;
import edu.icet.demo.bo.custom.impl.PlaceOrderBoImpl;
import edu.icet.demo.bo.custom.impl.ProductBoImpl;
import edu.icet.demo.model.Customer;
import edu.icet.demo.model.Order;
import edu.icet.demo.model.OrderTable;
import edu.icet.demo.model.Product;
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
import java.util.Date;
import java.util.ResourceBundle;

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

    PlaceOrderBoImpl placeOrderBoImpl = new PlaceOrderBoImpl();
    ProductBoImpl productBoImpl = new ProductBoImpl();
    CustomerBoImpl customerBoImpl = new CustomerBoImpl();
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    private void loadItemCode() {

        ObservableList<Product> allSupplier = productBoImpl.getAllProducts();
        ObservableList itemId = FXCollections.observableArrayList();

        allSupplier.forEach(supplier -> {
            itemId.add(supplier.getId());

        });
        itemIdCombo.setItems(itemId);
    }

    private void loadCustomerId() {

        ObservableList<Customer> allSupplier = customerBoImpl.getAllCustomers();
        ObservableList customerId = FXCollections.observableArrayList();

        allSupplier.forEach(supplier -> {
            customerId.add(supplier.getId());

        });
        cusIdCombo.setItems(customerId);

    }

    private void loadDateAndTime() {

        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
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

    private void setItemDataFroLbl(String ItemCode) {

        Product product = productBoImpl.getProductById(ItemCode);
        itemNameField.setText(product.getName());
        categoryField.setText(product.getCategory());
        priceField.setText(String.valueOf(product.getPrice()));
        sizeField.setText(product.getSize());

    }

    private void setCustomerDataFroLbl(String newValue) {

        Customer customer = customerBoImpl.getCustomerById(newValue);
        cusNameField.setText(customer.getName());
        cusEmailField.setText(customer.getEmail());
        cusAddressField.setText(customer.getAddress());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        loadCustomerId();
        loadItemCode();

        cusIdCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCustomerDataFroLbl((String) newValue);
        });
        itemIdCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setItemDataFroLbl((String) newValue);
        });

        orderIdField.setText(placeOrderBoImpl.generateOrderId());


        itemCodeColumn.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("desc"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        unityPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
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

    ObservableList<OrderTable> orderList = FXCollections.observableArrayList();
    public void addActionBtn(ActionEvent actionEvent) {
        String itemCode = (String) itemIdCombo.getValue();
        String iteName = itemNameField.getText();
        Integer qty = Integer.parseInt(qtyField.getText());
        Double unitPrice = Double.valueOf(priceField.getText());
        Double total = qty * unitPrice;
        OrderTable orderTable1 = new OrderTable(itemCode, iteName, qty, unitPrice, total);
        System.out.println(orderTable1);

        orderList.add(orderTable1);
        orderTable.setItems(orderList);
        calcNetTotal();
    }

    double total = 0;
    private void calcNetTotal() {

        for (OrderTable orderObj : orderList) {
            total += orderObj.getTotal();
        }
        netValueField.setText(String.valueOf(total) + "/=");
    }

    public void placeOrderAction(ActionEvent actionEvent) throws ParseException, IOException {
        String id = orderIdField.getText();
        String Cusid =cusIdCombo.getValue().toString();
        DateFormat format = new SimpleDateFormat("DD-MM-YYYY");
        Date orderDate = format.parse(dateField.getText());
        double amount = total;

        Order order = new Order(
                id,Cusid,orderDate,amount
        );

        boolean isInsert = placeOrderBoImpl.insertOrder(order);
        if (isInsert) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Placed");
            alert.setContentText("Order Placed Successfully..!");
            alert.showAndWait();
            clearFields();
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
        orderTable.setItems(null);
    }

    public void clearActionBtn(ActionEvent actionEvent) {
    }


    public void closerAction(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
