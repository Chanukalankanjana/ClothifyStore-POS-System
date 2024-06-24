package edu.icet.demo.dao.custom.impl;

import edu.icet.demo.dao.custom.SupplierDao;
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
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        session.persist(supplierEntity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(SupplierEntity supplierEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("UPDATE supplier SET name =:name,company =:company,email =:email WHERE id =:id");
        query.setParameter("name",supplierEntity.getName());
        query.setParameter("company",supplierEntity.getCompany());
        query.setParameter("email",supplierEntity.getEmail());
        query.setParameter("id",supplierEntity.getId());

        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        return i>0;
    }

    @Override
    public boolean delete(String id) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        Query query = session.createQuery("DELETE FROM supplier WHERE id=:id");
        query.setParameter("id",id);
        int i = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return i>0;
    }

    public SupplierEntity searchById(String id) {

        Session session = HibernateUtil.getSession();
        session.getTransaction();

        Query query = session.createQuery("FROM supplier WHERE id=:id");
        query.setParameter("id",id);
        SupplierEntity supplierEntity = (SupplierEntity) query.uniqueResult();
        session.close();
        return supplierEntity;

    }


    public String getLatestId() {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM supplier ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();
        session.close();
        return id;
    }
}
