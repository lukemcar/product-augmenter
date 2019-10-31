package com.fr.services.productaugmenter.messaging;

import com.fr.services.productaugmenter.productaugmenter.Order;
import com.fr.services.productaugmenter.productaugmenter.OrderRepository;
import com.fr.services.productaugmenter.productaugmenter.PriceService;
import com.fr.services.productaugmenter.productaugmenter.Product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;


@RunWith(MockitoJUnitRunner.class)
public class OrderConsumerTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private PriceService priceService;
    private OrderConsumer orderConsumer;
    
    @Before
    public void initTest() {
        orderConsumer = new OrderConsumer(orderRepository, priceService);
    }
    
    /**
     * Given a message with an order
     * it should be saved in the repository with the price set
     * it should have a price of 2.35
     */
    @Test
    public void handleMessageAssertPriceTest() {
        // test data
        final double expectedPrice = 2.35;
        final Order message = createOrder();

        // mock price service getPriceFor
        when(priceService.getPriceFor(any(Product.class))).thenReturn(expectedPrice);
        
        // mock order repository save and assert price equals expectedPrice
        doAnswer((invocation)->{
            Order savedOrder = Order.class.cast(invocation.getArguments()[0]);
            assertNotNull("order was null when passed to OrderRepository.save", savedOrder);
            double actualPrice = savedOrder.getProduct().getPrice();
            assertEquals("Order price did not match expected price", expectedPrice, actualPrice, 0);
            return 1L;
        }).when(orderRepository).save(any(Order.class));
        
        // execute 
        orderConsumer.handleMessage(message);
    }

    private final Order createOrder() {
        Product product = new Product();
        product.setName("Super product");
        product.setDescription("Test product");
        Order order = new Order();
        order.setProduct(product);
        return order;
    }
}
