/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.deepit.spring.orm.service;

import be.deepit.spring.orm.dao.ProductDao;
import be.deepit.spring.orm.model.Product;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Akrem AYADI
 */
@Component
public interface ProductService {

    ProductDao getProductDao();

    @Transactional(readOnly = true)
    public default List<Product> listAll() {
        return getProductDao().findAll();

    }

    public default void add(Product product) {
        getProductDao().persist(product);
    }

    public default void addAll(Collection<Product> products) {
        for (Product product : products) {
            if (product.getName().contains("Error")) {
                throw new IllegalArgumentException();
            } else {
                add(product);
            }
        }
    }

}
