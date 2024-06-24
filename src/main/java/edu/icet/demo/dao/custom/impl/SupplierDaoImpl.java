package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.dao.custom.SupplierDao;
import edu.icet.demo.entity.CustomerEntity;
import edu.icet.demo.entity.SupplierEntity;
import edu.icet.demo.utill.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class SupplierDaoImpl implements SupplierDao {
    @Override
    public SupplierEntity search(String s) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("FROM supplier WHERE email=:email");
        query.setParameter("email",s);
        SupplierEntity supplierEntity = (SupplierEntity) query.uniqueResult();
        session.close();
        return supplierEntity;
    }

    @Override
    public ObservableList<SupplierEntity> searchAll() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();

        List<SupplierEntity> supplierList = session.createQuery("FROM supplier").list();
        ObservableList<SupplierEntity> list= FXCollections.observableArrayList();
        session.close();
        supplierList.forEach(supplierEntity -> {
            list.add(supplierEntity);
        });
        return list;
    }

    @Override
    public boolean insert(SupplierEntity supplierEntity) {
        return false;
    }

    @Override
    public boolean update(SupplierEntity supplierEntity) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }
}
