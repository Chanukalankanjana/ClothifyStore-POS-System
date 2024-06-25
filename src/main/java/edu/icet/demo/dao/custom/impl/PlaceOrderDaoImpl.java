package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.dao.custom.PlaceOrderDao;
import edu.icet.demo.entity.OrderEntity;
import javafx.collections.ObservableList;

public class PlaceOrderDaoImpl implements PlaceOrderDao {
    @Override
    public OrderEntity search(String s) {
        return null;
    }

    @Override
    public ObservableList<OrderEntity> searchAll() {
        return null;
    }

    @Override
    public boolean insert(OrderEntity orderEntity) {
        return false;
    }

    @Override
    public boolean update(OrderEntity orderEntity) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }
}
