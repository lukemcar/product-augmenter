package com.fr.services.productaugmenter.config;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    public static final String ORDERS_QUEUE = "rabbitmq:orders";
    
    @Bean
    public Endpoint produceOrdersEndpoint(CamelContext camelContext) {
        return camelContext.getEndpoint("direct:produceOrderToQueue");
    }
    
    @Bean
    public RouteBuilder ordersRouteBuilder(final CamelContext camelContext) {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception { 
                // consumer route
                from(ORDERS_QUEUE+"?concurrentConsumers=2")
                    .to("log:com.fr.services.productaugmenter.config.AppConfig?level=INFO")
                    .convertBodyTo(String.class)
                    .to("bean:orderConsumer?method=handleMessage");
                
                // producer route
                from(produceOrdersEndpoint(camelContext))
                    .marshal().json(JsonLibrary.Jackson)
                    .to("log:com.fr.services.productaugmenter.config.AppConfig?level=INFO")
                    .to(ORDERS_QUEUE);
            }
        };
    }
}
