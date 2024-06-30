package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.dao.custom.OrderDao;
import edu.icet.demo.entity.OrderEntity;
import edu.icet.demo.utill.HibernateUtil;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public OrderEntity search(String s) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("FROM order_table WHERE id=:id");
        query.setParameter("id",s);
        return (OrderEntity)query.uniqueResult();
    }

    @Override
    public ObservableList<OrderEntity> searchAll() {

        return null;
    }

    @Override
    public boolean insert(OrderEntity orderEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist((OrderEntity)orderEntity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(OrderEntity orderEntity) {
        return false;
    }

    @Override
    public boolean delete(String s) {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM order_table WHERE id=:id");
        query.setParameter("id",s);
        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;
    }

    public String getLatestOrderId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM order_table ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();

        session.close();

        return id;
    }

    public List<String> getOrderIdsByEmpId(String id){
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("SELECT id FROM order_table WHERE empId=:id");
        query.setParameter("id",id);
        List<String > list = query.list();
        session.close();
        return list;
    }
}
