package be.deepit.spring.orm.service;

import be.deepit.spring.orm.dao.ProductDao;
import be.deepit.spring.orm.model.Product;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductServiceRequired implements ProductService {

    @Autowired
    private ProductDao productDao;

    public ProductDao getProductDao() {
        return productDao;
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(Product product) {
        productDao.persist(product);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addAll(Collection<Product> products) {
        for (Product product : products) {
            if (product.getName().contains("Error")) {
                throw new IllegalArgumentException();
            } else {
                add(product);
            }
        }
    }

}
