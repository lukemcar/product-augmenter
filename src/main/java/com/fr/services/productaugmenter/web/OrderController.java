package com.fr.services.productaugmenter.web;

import com.fr.services.productaugmenter.productaugmenter.Order;
import com.fr.services.productaugmenter.productaugmenter.OrderRepository;

import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController("/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final ProducerTemplate producerTemplate;
    private final ApplicationContext applicationContext;

    public OrderController(OrderRepository orderRepository, ProducerTemplate producerTemplate, ApplicationContext applicationContext) {
        super();
        this.orderRepository = orderRepository;
        this.producerTemplate = producerTemplate;
        this.applicationContext = applicationContext;
    }

    @GetMapping
    public ResponseEntity<Collection<Order>> getOrders(){
        return ResponseEntity.ok(orderRepository.findAll());
    }
    
    @PostMapping
    public ResponseEntity<?> publishOrderToQueue(@RequestBody Order order) {
        Endpoint produceOrdersEndpoint = applicationContext.getBean("produceOrdersEndpoint", Endpoint.class);
        producerTemplate.sendBody(produceOrdersEndpoint, order);
        return ResponseEntity.ok("Order sucessfully produced to queue");
    }
}
