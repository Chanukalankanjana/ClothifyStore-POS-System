package edu.icet.demo.bo.custom.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.demo.bo.custom.OrderDetailsBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.impl.OrderDaoImpl;
import edu.icet.demo.dao.custom.impl.OrderDetailsDaoImpl;
import edu.icet.demo.dao.custom.impl.ProductDaoImpl;
import edu.icet.demo.entity.ProductEntity;
import edu.icet.demo.model.OrderDetails;
import edu.icet.demo.model.Product;
import edu.icet.demo.utill.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderDetailsBoImpl implements OrderDetailsBo {
    OrderDetailsDaoImpl orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.PLACE);
    ProductDaoImpl productDao = DaoFactory.getInstance().getDao(DaoType.ITEM);
    OrderDaoImpl orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);

    public ObservableList<String> getProductIds(){
        return productDao.searchAllIds();
    }

    public Product getProductById(String newValue){
        ProductEntity productEntity = productDao.search(newValue);
        Product product = new ObjectMapper().convertValue(productEntity, Product.class);
        return product;
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
        return orderDetailsDao.saveAll(orderDetailsObservableList);
    }

    public int getLatestCartId() {
        return orderDetailsDao.getLatestId() + 1;
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


    public boolean increseQty(ObservableList<OrderDetails> itemIdList) {
        return productDao.increseQty(itemIdList);
    }

    public boolean updateNewQty(String id, int qty) {
        productDao.updateQty(id, qty);
        return true;
    }

    public boolean updateOrderAmount(String id, double newAmount) {
        return orderDao.updateAmountById(id, newAmount);
    }

    public boolean updateCartById(int id, int i, double newAmount) {
        return orderDetailsDao.updateQtyAndAmount(id, i, newAmount);
    }


    public boolean increaseQtyOfProduct(String id, int qty) {

        return productDao.updateQtyOfProduct(id, qty);
    }

    public boolean decreaseAmountByOrderId(String id, double amount) {
        return orderDao.deacreseAmount(id, amount);
    }

    public boolean removeFromCart(String oId, String pId) {
        return orderDetailsDao.removeItem(oId, pId);
    }

}
