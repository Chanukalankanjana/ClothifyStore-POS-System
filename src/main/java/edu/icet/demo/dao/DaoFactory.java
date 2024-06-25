package edu.icet.demo.dao;

import edu.icet.demo.dao.custom.impl.*;
import edu.icet.demo.utill.DaoType;

public class DaoFactory {
    private static DaoFactory instance;

    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return instance!=null?instance:(instance=new DaoFactory());
    }

    public <T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case USER:return (T)new UserDaoImpl();
            case CUSTOMER:return (T)new CustomerDaoImpl();
            case SUPPLIER:return (T)new SupplierDaoImpl();
            case ITEM:return (T) new ProductDaoImpl();
            case ORDER:return (T) new PlaceOrderDaoImpl();
        }
        return null;
    }
}
