package com.fr.services.productaugmenter.productaugmenter;

import java.util.Collection;

public interface OrderRepository {
    Long save(Order order);
    Order findById(Long id);
    Collection<Order> findAll();
}
