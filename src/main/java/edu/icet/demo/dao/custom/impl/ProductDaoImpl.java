package edu.icet.demo.dao.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.demo.dao.custom.ProductDao;
import edu.icet.demo.entity.CustomerEntity;
import edu.icet.demo.entity.ProductEntity;
import edu.icet.demo.model.OrderDetails;
import edu.icet.demo.utill.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public ProductEntity search(String s) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("FROM product WHERE id=:id");
        query.setParameter("id",s);
        ProductEntity productEntity = (ProductEntity) query.uniqueResult();
        session.close();
        return productEntity;
    }

    @Override
    public ObservableList<ProductEntity> searchAll() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<ProductEntity> productList = session.createQuery("FROM product").list();
        session.close();

        ObservableList<ProductEntity> list = FXCollections.observableArrayList();
        productList.forEach(productEntity -> {
            list.add(productEntity);
        });

        return list;
    }

    @Override
    public boolean insert(ProductEntity productEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        session.persist(productEntity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(ProductEntity productEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("UPDATE product SET name =:name,qty =:qty,size =:size,category= :category,price= :price WHERE id =:id");
        query.setParameter("id",productEntity.getId());
        query.setParameter("name",productEntity.getName());
        query.setParameter("qty",productEntity.getQty());
        query.setParameter("size",productEntity.getSize());
        query.setParameter("category",productEntity.getCategory());
        query.setParameter("price",productEntity.getPrice());

        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return i>0;
    }

    @Override
    public boolean delete(String s) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM product WHERE id= :id");
        query.setParameter("id",s);
        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;
    }

    public String getLatestId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM product ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();

        session.close();

        return id;
    }

    public ProductEntity searchById(String id) {

        Session session = HibernateUtil.getSession();
        session.getTransaction();

        Query query = session.createQuery("FROM product WHERE id=:id");
        query.setParameter("id",id);
        ProductEntity productEntity = (ProductEntity) query.uniqueResult();
        session.close();
        return productEntity;

    }

    public ObservableList<String> searchAllIds() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        List<String> list = session.createQuery("SELECT id FROM product").list();
        session.close();
        ObservableList<String> idList = FXCollections.observableArrayList();

        list.forEach(s -> {
            idList.add(s);
        });
        return idList;
    }

    public ObservableList<ProductEntity> getProductBysID(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("FROM product WHERE supId=:id");
        query.setParameter("id",id);
        List<ProductEntity> list = query.list();

        ObservableList<ProductEntity> productEntities = FXCollections.observableArrayList();

        list.forEach(productEntity -> {
            productEntities.add(productEntity);
        });
        return productEntities;

    }

    public void updateQty(String itemId, int qty) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("UPDATE product SET qty=qty-:qty WHERE id=:id");
        query.setParameter("qty",qty);
        query.setParameter("id",itemId);

        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

    }

    public boolean increseQty(ObservableList<OrderDetails> itemIdList) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("UPDATE product SET qty=qty+:qty WHERE id=:id");

        itemIdList.forEach(orderDetails -> {
            query.setParameter("qty",orderDetails.getQty());
            query.setParameter("id",orderDetails.getItemId());
            query.executeUpdate();
        });
        session.getTransaction().commit();
        session.close();
        return true;
    }

}
