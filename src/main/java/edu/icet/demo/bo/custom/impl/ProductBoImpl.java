package edu.icet.demo.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.demo.bo.custom.ProductBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.impl.ProductDaoImpl;
import edu.icet.demo.entity.CustomerEntity;
import edu.icet.demo.entity.ProductEntity;
import edu.icet.demo.model.Customer;
import edu.icet.demo.model.Product;
import edu.icet.demo.utill.DaoType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductBoImpl implements ProductBo {

    ProductDaoImpl productDaoImpl = DaoFactory.getInstance().getDao(DaoType.ITEM);

    public boolean insertProduct(Product product) {

        ProductEntity productEntity = new ObjectMapper().convertValue(product, ProductEntity.class);
        return productDaoImpl.insert(productEntity);
    }

    public ObservableList<Product> getAllProducts() {

        ObservableList<ProductEntity> list = productDaoImpl.searchAll();
        ObservableList<Product> productList = FXCollections.observableArrayList();

        list.forEach(productEntity -> {
            productList.add(new ObjectMapper().convertValue(productEntity, Product.class));
        });
        return productList;
    }

    public Product getProductById(String id) {

        ProductEntity productEntity = productDaoImpl.search(id);
        return new ObjectMapper().convertValue(productEntity, Product.class);

    }

    public boolean updateProduct(Product product) {

        ProductEntity productEntity = new ObjectMapper().convertValue(product, ProductEntity.class);

        return productDaoImpl.update(productEntity);

    }

    public boolean deleteProductById(String text) {

        return productDaoImpl.delete(text);

    }

    public ObservableList<String> getAllProductIds() {

        return productDaoImpl.searchAllIds();
    }

//    public ObservableList<Product> getProductBySupId(String text) {
//        ObservableList<ProductEntity> productEntityList = productDaoImpl.getProductBysID(text);
//
//        ObservableList<Product> products = FXCollections.observableArrayList();
//        productEntityList.forEach(productEntity -> {
//            products.add(new ObjectMapper().convertValue(productEntity, Product.class));
//        });
//        return products;
//    }

    public String generateProductId() {

        String lastProductId = productDaoImpl.getLatestId();
        if (lastProductId==null){
            return "P0001";
        }

        int number = Integer.parseInt(lastProductId.split("P")[1]);
        number++;
        return String.format("P%04d", number);
    }
}
