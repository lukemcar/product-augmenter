package com.fr.services.productaugmenter.productaugmenter;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
class InMemoryOrderRepository implements OrderRepository {
    private final ConcurrentMap<Long, Order> store = new ConcurrentHashMap<>();
    private final Random random = new Random();

    @Override
    public Long save(Order order) {
        long id = random.nextInt(100);

        order.setId(id);
        store.putIfAbsent(id, order);

        return id;
    }

    @Override
    public Order findById(Long id) {
        return store.get(id);
    }

    @Override
    public Collection<Order> findAll() {
        return this.store.values();

    }
}
