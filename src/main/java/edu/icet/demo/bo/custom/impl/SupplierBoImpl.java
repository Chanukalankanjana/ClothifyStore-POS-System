package edu.icet.demo.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.demo.bo.custom.SupplierBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.impl.CustomerDaoImpl;
import edu.icet.demo.dao.custom.impl.SupplierDaoImpl;
import edu.icet.demo.entity.CustomerEntity;
import edu.icet.demo.entity.SupplierEntity;
import edu.icet.demo.model.Customer;
import edu.icet.demo.model.Supplier;
import edu.icet.demo.utill.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SupplierBoImpl implements SupplierBo {
    SupplierDaoImpl supplierDaoImpl = DaoFactory.getInstance().getDao(DaoType.SUPPLIER);

    public boolean insertSupplier(Supplier supplier) {

        SupplierEntity supplierEntity = new ObjectMapper().convertValue(supplier, SupplierEntity.class);
        return supplierDaoImpl.insert(supplierEntity);
    }

    public ObservableList<Supplier> getAllSuppliers() {

        ObservableList<SupplierEntity> list = supplierDaoImpl.searchAll();
        ObservableList<Supplier> supplerList = FXCollections.observableArrayList();

        list.forEach(supplierEntity -> {
            supplerList.add(new ObjectMapper().convertValue(supplierEntity, Supplier.class));
        });
        return supplerList;
    }

    public Supplier getSupplierById(String id) {

        SupplierEntity supplierEntity = supplierDaoImpl.searchById(id);
        return new ObjectMapper().convertValue(supplierEntity, Supplier.class);

    }

    public boolean updateSupplier(Supplier supplier) {

        SupplierEntity supplierEntity = new ObjectMapper().convertValue(supplier, SupplierEntity.class);

        return supplierDaoImpl.update(supplierEntity);

    }

    public boolean deleteSupplierById(String text) {

        return supplierDaoImpl.delete(text);

    }

    public boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public String generateSupplierId() {

        String lastSupplierId = supplierDaoImpl.getLatestId();
        if (lastSupplierId==null){
            return "S0001";
        }

        int number = Integer.parseInt(lastSupplierId.split("C")[1]);
        number++;
        return String.format("S%04d", number);
    }
}
