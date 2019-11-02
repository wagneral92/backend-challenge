package com.acme.orderserver.queue;

import com.acme.orderserver.queue.model.Command;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public QueueSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(final Command command, final String queue) {
        rabbitTemplate.convertAndSend(queue, command);
    }
}
