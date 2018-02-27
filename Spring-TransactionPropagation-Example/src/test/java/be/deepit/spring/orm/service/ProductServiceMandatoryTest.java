/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.deepit.spring.orm.service;

import be.deepit.spring.orm.model.Product;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static be.deepit.spring.orm.model.Product.TransactionType.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.IllegalTransactionStateException;

/**
 *
 * @author Akrem AYADI
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
public class ProductServiceMandatoryTest {

    @Autowired
    @Qualifier(value = "productServiceMandatory")
    ProductService productService;

    public ProductServiceMandatoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        productService.removeAll();
    }

    /**
     * Test the mandatory propagation *
     *
     * tx -> join tx </br>
     * no -> throw a TransactionRequiredException
     */
    @Test()
    public void testGetProductDao() {
        assertTrue(productService.toString().contains("ProductServiceMandatory"));
        // test the case : no -> throw a TransactionRequiredException 
        assertThatThrownBy(() -> {
            productService.add(new Product(1, "Product 1", INNER_TX));
        }).isInstanceOf(IllegalTransactionStateException.class);
        // test the case : tx -> join tx
        // Add curent tx  -> inner tx -> current tx
        productService.addAll(Arrays.asList(new Product(1, "Book", CURRENT_TX), new Product(2, "Soap", INNER_TX), new Product(3, "Computer", CURRENT_TX)));
        assertEquals(productService.listAll().size(), 3);
        // Test transaction rollback in the main tx at the end 
        assertThatThrownBy(() -> {
            productService.addAll(Arrays.asList(new Product(5, "Book", CURRENT_TX), new Product(6, "Soap", INNER_TX), new Product(7, "SOAP", FAILED_TX)));
        }).isInstanceOf(IllegalArgumentException.class);
        assertEquals(productService.listAll().size(), 3);

        // Test transaction rollback in the inner tx (Mandatory propagation)
        // The second product will fail because the ID= 1 already taken 
        assertThatThrownBy(() -> {
            productService.addAll(Arrays.asList(new Product(5, "Book", CURRENT_TX), new Product(1, "Soap", INNER_TX), new Product(7, "SOAP", CURRENT_TX)));
        }).isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("Violation of unique constraint");
        assertEquals(productService.listAll().size(), 3);
    }

}
