package edu.icet.demo.controller;

import edu.icet.demo.bo.BoFactory;
import edu.icet.demo.bo.custom.impl.CustomerBoImpl;
import edu.icet.demo.bo.custom.impl.OrderDetailsBoImpl;
import edu.icet.demo.bo.custom.impl.OrderBoImpl;
import edu.icet.demo.utill.BoType;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewOrdersController implements Initializable {
    public AnchorPane viewOrderAnchor;
    public TableColumn cartNumColumn;
    public TableColumn orderIdColumn;
    public TableColumn itemIdColumn;
    public TextField itemIdField;
    public TextField itemNameField;
    public TableColumn qtyColumn;
    public TableColumn amountColumn;
    public TextField orderIdField;
    public TextField customerIdField;
    public TextField customerNameField;
    public TextField emailAddressField;
    public TextField categoryField;
    public TextField unitPriceField;
    public TextField totalValueField;
    public TextField availableQtyField;
    public TableView cartTable;

    OrderBoImpl placeOrderBo = BoFactory.getInstance().getBo(BoType.PLACE);
    OrderDetailsBoImpl orderDetailsBo = BoFactory.getInstance().getBo(BoType.ORDER);
    CustomerBoImpl customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cartNumColumn.setCellValueFactory(new PropertyValueFactory<>("cartNum"));
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

//        cartTable.setItems();



    }

}
