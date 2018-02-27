package be.deepit.spring.orm.service;

import be.deepit.spring.orm.dao.ProductDao;
import be.deepit.spring.orm.model.Product;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is an example of the {@link Propagation#MANDATORY} propagation type.
 * The MANDATORY behaviour states that an existing opened transaction must already exist.
 * If not an exception will be thrown by the container.
 * 
 * tx -> join tx
 * no -> throw a TransactionRequiredException
 * 
 * @author Akrem AYADI
 */
@Component(value = "psm")
public class ProductServiceMandatory implements ProductService {

    @Autowired
    private ProductDao productDao;

    public ProductDao getProductDao() {
        return productDao;
    }
    
    @Transactional(propagation = Propagation.MANDATORY)
    public void add(Product product) {
        ProductService.super.add(product);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addAll(Collection<Product> products) {
        ProductService.super.addAll(products);
    }

}
