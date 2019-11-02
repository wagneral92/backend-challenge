package com.acme.paymentserver.config.queue;

import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String PAYMENT_ORDER = "payment-order";

    @Bean
    Queue paymentOrderQueue(){
        return QueueBuilder.durable(PAYMENT_ORDER).build();
    }
}
