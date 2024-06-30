package edu.icet.demo.bo.custom.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.demo.bo.custom.OrderDetailsBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.impl.OrderDaoImpl;
import edu.icet.demo.dao.custom.impl.OrderDetailsDaoImpl;
import edu.icet.demo.dao.custom.impl.ProductDaoImpl;
import edu.icet.demo.entity.OrderDetailsEntity;
import edu.icet.demo.entity.ProductEntity;
import edu.icet.demo.model.OrderDetails;
import edu.icet.demo.model.Product;
import edu.icet.demo.utill.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class OrderDetailsBoImpl implements OrderDetailsBo {
    OrderDetailsDaoImpl orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.PLACE);
    ProductDaoImpl productDao = DaoFactory.getInstance().getDao(DaoType.ITEM);
    OrderDaoImpl orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);

    public Product getItemById(String newValue){
        ProductEntity productEntity = productDao.search(newValue);
        return new ObjectMapper().convertValue(productEntity, Product.class);
    }

    public ObservableList<Product> getAllProducts() {
        ObservableList<ProductEntity> list = productDao.searchAll();
        ObservableList<Product> products = FXCollections.observableArrayList();

        list.forEach(productEntity -> {
            products.add(new ObjectMapper().convertValue(productEntity, Product.class));
        });
        return products;
    }

    public String generateOrderId() {
        String id = new OrderDaoImpl().getLatestOrderId();

        if (id==null){
            return "X0001";
        }
        int number = Integer.parseInt(id.split("X")[1]);
        number++;
        return String.format("X%04d", number);
    }

    public boolean saveOrderDetails(ObservableList<OrderDetails> orderDetailsObservableList) {
        orderDetailsObservableList.forEach(orderDetails -> {
            OrderDetailsEntity orderDetailsEntity = new ObjectMapper().convertValue(orderDetails, OrderDetailsEntity.class);
            orderDetailsDao.saveAll((ObservableList<OrderDetails>) orderDetailsEntity);
        });
        return true;
    }

    public ObservableList<OrderDetails> getAllOrderedProducts() {
        return orderDetailsDao.getAll();
    }

    public boolean deleteById(String id) {
        return orderDetailsDao.deleteByOrderId(id);
    }

    public ObservableList<OrderDetails> getProductIdsByOrderId(String id) {

        return orderDetailsDao.getProductIdsByOrderId(id);
    }

    public ObservableList<OrderDetails> getAllOrderedProductsByEmpId(String id) {
        List<String> orderIdList = orderDao.getOrderIdsByEmpId(id);
        return orderDetailsDao.getProductIdsByOrderIds(orderIdList);
    }

}
