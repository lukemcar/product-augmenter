package com.fr.services.productaugmenter.productaugmenter;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {
    /**
     * Given a product with name 'My Product' it should return a slug in the form of 'my-product'
     */
    @Test
    public void getSlugTest() {
        String productName = "My Product";
        String description = "Product description";
        double price = 1.2;

        Product product = new Product(productName, description, price);

        String expectedResult = "my-product";

        assertTrue(product.getSlug().equals(expectedResult));

    }
}