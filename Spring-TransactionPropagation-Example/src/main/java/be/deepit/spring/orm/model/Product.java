package be.deepit.spring.orm.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Example of entity to test all the propagation types.
 *
 * @author Akrem AYADI
 */
@Entity
public class Product {

    @Id
    private Integer id;
    private String name;

    // This field is added to specify in which transaction this entity will be executed 
    @Transient
    private final TransactionType transactionType;

    public Product() {
        transactionType = TransactionType.CURRENT_TX;
    }

    public Product(Integer id, String name, TransactionType transactionType) {
        this.id = id;
        this.name = name;
        this.transactionType = transactionType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + "]";
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public enum TransactionType {
        INNER_TX, CURRENT_TX, FAILED_TX, OUT_OF_TX;
    }
}
