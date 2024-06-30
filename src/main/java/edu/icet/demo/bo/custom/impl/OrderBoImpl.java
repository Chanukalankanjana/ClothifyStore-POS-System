package edu.icet.demo.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.demo.bo.custom.OrderBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.impl.OrderDaoImpl;
import edu.icet.demo.entity.OrderEntity;
import edu.icet.demo.model.Order;
import edu.icet.demo.utill.DaoType;

public class OrderBoImpl implements OrderBo {
    OrderDaoImpl orderDaoImpl = DaoFactory.getInstance().getDao(DaoType.ORDER);
    public boolean saveOrder(Order order) {
        return new OrderDaoImpl().insert(new ObjectMapper().convertValue(order, OrderEntity.class));
    }

    public String getLatestOrderId() {
        return orderDaoImpl.getLatestOrderId();
    }

    public Order getOrderById(String orderId) {
        return new ObjectMapper().convertValue(orderDaoImpl.search(orderId),Order.class);
    }

    public boolean deleteOrderById(String id) {
        return orderDaoImpl.delete(id);
    }

}
