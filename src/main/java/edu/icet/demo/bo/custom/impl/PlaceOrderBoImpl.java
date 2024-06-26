package edu.icet.demo.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.demo.bo.custom.PlaceOrderBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.impl.PlaceOrderDaoImpl;
import edu.icet.demo.entity.OrderEntity;
import edu.icet.demo.model.Order;
import edu.icet.demo.utill.DaoType;

public class PlaceOrderBoImpl implements PlaceOrderBo {

    PlaceOrderDaoImpl placeOrderDaoImpl = DaoFactory.getInstance().getDao(DaoType.ORDER);

    public String generateOrderId(){
        String lastOrderId =placeOrderDaoImpl.getLatestId();
        if (lastOrderId==null){
            return "O0001";
        }

        int number = Integer.parseInt(lastOrderId.split("O")[1]);
        number++;
        return String.format("O%04d", number);
    }

    public boolean insertOrder(Order order) {

        OrderEntity orderEntity = new ObjectMapper().convertValue(order, OrderEntity.class);
        PlaceOrderDaoImpl placeOrderDaoImpl = new PlaceOrderDaoImpl();
        boolean insert = placeOrderDaoImpl.insert(orderEntity);
        return insert;
    }
}
