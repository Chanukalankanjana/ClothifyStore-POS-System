package edu.icet.demo.dao.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.OrderDetailsDao;
import edu.icet.demo.entity.OrderDetailsEntity;
import edu.icet.demo.model.OrderDetails;
import edu.icet.demo.model.ProductSummary;
import edu.icet.demo.utill.DaoType;
import edu.icet.demo.utill.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {
    ProductDaoImpl productDao = DaoFactory.getInstance().getDao(DaoType.ITEM);


    @Override
    public OrderDetailsEntity search(Integer integer) {
        return null;
    }

    @Override
    public ObservableList<OrderDetailsEntity> searchAll() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<OrderDetailsEntity> list = session.createQuery("FROM order_details").list();
        session.close();

        ObservableList<OrderDetailsEntity> observableList = FXCollections.observableArrayList();
        list.forEach(orderDetailsEntity -> {
            observableList.add(orderDetailsEntity);
        });
        return observableList;
    }

    @Override
    public boolean insert(OrderDetailsEntity orderDetailsEntity) {
        return true;
    }

    @Override
    public boolean update(OrderDetailsEntity orderDetailsEntity) {
        return false;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    public boolean saveAll(ObservableList<OrderDetails> orderDetailsObservableList) {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        orderDetailsObservableList.forEach(orderDetails -> {
            OrderDetailsEntity orderDetailsEntity = new ObjectMapper().convertValue(orderDetails, OrderDetailsEntity.class);

            productDao.updateQty(orderDetailsEntity.getItemId(),orderDetailsEntity.getQty());
            session.persist(orderDetailsEntity);
        });
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public int getLatestId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM order_details ORDER BY id DESC LIMIT 1");
        int id = (int) query.uniqueResult();

        session.close();

        return id;
    }

    public ObservableList<OrderDetails> getAll() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<OrderDetailsEntity> list = session.createQuery("FROM order_details").list();
        session.close();
        ObservableList<OrderDetails> observableList = FXCollections.observableArrayList();

        list.forEach(orderDetailsEntity -> {
            observableList.add(new ObjectMapper().convertValue(orderDetailsEntity, OrderDetails.class));
        });

        return observableList;
    }

    public boolean deleteByOrderId(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM order_details WHERE orderId=:id");
        query.setParameter("id",id);
        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return i>0;
    }

    public ObservableList<OrderDetails> getProductIdsByOrderId(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("FROM order_details WHERE orderId=:id");
        query.setParameter("id",id);
        List<OrderDetailsEntity> list = query.list();
        session.close();

        ObservableList<OrderDetails> observableList=FXCollections.observableArrayList();

        list.forEach(s -> {
            observableList.add(new ObjectMapper().convertValue(s, OrderDetails.class));
        });
        return observableList;

    }

    public boolean removeItem(String oId, String iId) {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM order_details WHERE orderId=:oId AND itemId=:iId");
        query.setParameter("oId",oId);
        query.setParameter("iId",iId);
        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;
    }

    public ObservableList<OrderDetails> getProductIdsByOrderIds(List<String> orderIdList) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("FROM order_details WHERE orderId=:id");
        ObservableList<OrderDetailsEntity> list = FXCollections.observableArrayList();
        orderIdList.forEach(id -> {
            query.setParameter("id",id);
            List<OrderDetailsEntity> list1 = query.list();
            list.addAll(list1);

        });
        session.close();

        ObservableList<OrderDetails> observableList=FXCollections.observableArrayList();

        list.forEach(s -> {
            observableList.add(new ObjectMapper().convertValue(s, OrderDetails.class));
        });
        return observableList;

    }

}
