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

/**
 *
 * @author EXG503
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
    }

    /**
     * Test of getProductDao method, of class ProductServiceRequired.
     */
    @Test (expected = org.springframework.transaction.IllegalTransactionStateException.class)
    public void testGetProductDao() {
        System.out.println("getProductDao");
        assertTrue(productService.toString().contains("ProductServiceMandatory") );
        //Test transaction)
        try {
            productService.addAll(Arrays.asList(new Product(3, "Book"), new Product(4, "Soap"), new Product(1, "Computer")));
        } catch (DataAccessException dataAccessException) {
        }
        assertEquals(productService.listAll().size(), 3);
        // Test transaction rollback in the main tx at the end 
        try {
            productService.addAll(Arrays.asList(new Product(5, "Book"), new Product(6, "Soap"), new Product(7, "Error")));
        } catch (IllegalArgumentException illegalArgumentException) {
        }
        assertEquals(productService.listAll().size(), 3);
        productService.add(new Product(1, "Product 1"));
    }

}
