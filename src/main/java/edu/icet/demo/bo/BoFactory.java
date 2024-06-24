package edu.icet.demo.bo;

import edu.icet.demo.bo.custom.impl.CustomerBoImpl;
import edu.icet.demo.bo.custom.impl.SupplierBoImpl;
import edu.icet.demo.bo.custom.impl.UserBoImpl;
import edu.icet.demo.utill.BoType;

public class BoFactory {
    private static BoFactory instance;
    private  BoFactory(){}
    public static BoFactory getInstance(){ return instance!=null?instance:(instance=new BoFactory());}
    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case USER:return (T) new UserBoImpl();
            case CUSTOMER:return (T) new CustomerBoImpl();
            case SUPPLIER:return (T) new SupplierBoImpl();
        }
        return null;
    }
}
