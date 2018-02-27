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

    default void addWithoutTransaction(Product product) {
          getProductDao().persist(product);
    }

    default void add(Product product) {
        getProductDao().persist(product);
    }

    default void addAll(Collection<Product> products) {
        for (final Product product : products) {
            switch (product.getTransactionType()) {
                case INNER_TX:
                    add(product);
                    break;
                case CURRENT_TX:
                    getProductDao().persist(product);
                    break;
                case FAILED_TX:
                    throw new IllegalArgumentException();
                case OUT_OF_TX:
                    addWithoutTransaction(product);
                    break;

            }
        }
    }

    @Transactional
    public default void removeAll() {
        getProductDao().removeAll();
    }

}
