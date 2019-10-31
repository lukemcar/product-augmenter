package com.fr.services.productaugmenter.productaugmenter;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RemotePriceService implements PriceService {
    private final Random random = new Random();

    @Override
    public Double getPriceFor(Product product) {
        return random.nextDouble();
    }
}
