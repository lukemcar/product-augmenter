package com.fr.services.productaugmenter.camel.converters;

import java.io.IOException;
import java.io.InputStream;

import org.apache.camel.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fr.services.productaugmenter.productaugmenter.Order;

@Converter
public class OrderConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConverter.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectReader OBJREADER = MAPPER.readerFor(Order.class);
    
    @Converter(allowNull=false)
    public Order toOrder(String jsonData) throws JsonProcessingException, IOException {
        LOGGER.debug("Converting json string to Order");
        return OBJREADER.readValue(jsonData);
    }
    
    @Converter(allowNull=false)
    public Order toOrder(InputStream jsonStream) throws JsonProcessingException, IOException {
        LOGGER.debug("Converting json stream to Order");
        return OBJREADER.readValue(jsonStream);
    }    

    @Converter(allowNull=true)
    public String toString(Order message) throws JsonProcessingException {
        LOGGER.debug("Converting Order to json string");
        if(message == null) {
            return "{}";
        }
        return MAPPER.writeValueAsString(message);
    } 
}
