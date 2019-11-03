package com.acme.orderserver.config.queue;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    public static final String FINALIZE_ORDER = "finalize-order";
    public static final String FINALIZE_PAYMENT = "finalize-payment";
    public static final String REVERT_PAYMENT = "revert-payment";

    @Bean
    Queue finalizePaymentQueue(){
        return QueueBuilder.durable(FINALIZE_PAYMENT).build();
    }

    @Bean
    Queue revertPaymentQueue(){
        return QueueBuilder.durable(REVERT_PAYMENT).build();
    }

    @Bean
    Queue finalizeOrderQueue(){
        return QueueBuilder.durable(FINALIZE_ORDER).build();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
