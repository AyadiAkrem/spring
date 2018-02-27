package be.deepit.spring.orm.dao;

import be.deepit.spring.orm.model.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Akrem AYADI
 */
@Component
public class ProductDao {

    @PersistenceContext
    private EntityManager em;

    public void persist(Product product) {
        em.persist(product);
    }

    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p").getResultList();
    }

    /**
     * Remove all products
     *
     * @return the result of removing all the rows of Product table.
     */
    public int removeAll() {
        return em.createQuery("DELETE FROM Product").executeUpdate();
    }

}
