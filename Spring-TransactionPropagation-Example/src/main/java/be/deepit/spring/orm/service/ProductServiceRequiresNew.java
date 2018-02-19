package be.deepit.spring.orm.service;

import be.deepit.spring.orm.dao.ProductDao;
import be.deepit.spring.orm.model.Product;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductServiceRequiresNew implements ProductService {

    @Autowired
    private ProductDao productDao;

    public ProductDao getProductDao() {
        return productDao;
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void add(Product product) {
        ProductService.super.add(product);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addAll(Collection<Product> products) {
        ProductService.super.addAll(products);
    }

}
