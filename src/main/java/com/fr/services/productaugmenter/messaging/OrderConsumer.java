package com.fr.services.productaugmenter.messaging;

import com.fr.services.productaugmenter.productaugmenter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("orderConsumer")
public class OrderConsumer {    
    private final PriceService priceService;
    private final OrderRepository orderRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    public OrderConsumer(OrderRepository orderRepository, PriceService priceService) {
        super();
        this.orderRepository = orderRepository;
        this.priceService = priceService;
    }


    public void handleMessage(Order order) {
        Product product = order.getProduct();
        product.setPrice(priceService.getPriceFor(product));
        Long id = this.orderRepository.save(order);

        logger.info("Order saved: {}", id);
    }
}
