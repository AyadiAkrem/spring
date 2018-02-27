/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.deepit.spring.orm.service;

import be.deepit.spring.orm.model.Product;
import static be.deepit.spring.orm.model.Product.TransactionType.*;
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
public class ProductServiceRequiresNewTest {

    @Autowired
    @Qualifier(value = "productServiceRequiresNew")
    ProductService productService;

    public ProductServiceRequiresNewTest() {
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
     * Test of getProductDao method, of class ProductServiceRequired.
     */
    @Test
    public void testGetProductDao() {
        System.out.println("getProductDao");
        assertTrue(productService.toString().contains("ProductServiceRequiresNew") );
        productService.add(new Product(1, "Product 1",CURRENT_TX));
        productService.add(new Product(2, "Product 2",CURRENT_TX));
        System.out.println("listAll: " + productService.listAll());
        //Test transaction rollback (duplicated key)
        try {
            productService.addAll(Arrays.asList(new Product(3, "Book",INNER_TX), new Product(4, "Soap",INNER_TX), new Product(1, "Computer",INNER_TX)));
        } catch (DataAccessException dataAccessException) {
        }
        assertEquals(productService.listAll().size(), 2);
        // Test transaction rollback in the main tx at the end 
        try {
            productService.addAll(Arrays.asList(new Product(5, "Book",INNER_TX), new Product(6, "Soap",INNER_TX), new Product(7, "Error",FAILED_TX)));
        } catch (IllegalArgumentException illegalArgumentException) {
        }
        assertEquals(productService.listAll().size(), 2);
    }

}
