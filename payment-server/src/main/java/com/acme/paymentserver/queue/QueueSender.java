package com.acme.paymentserver.queue;

import com.acme.paymentserver.queue.model.Command;
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

    public void send(Command command, String queue) {
        rabbitTemplate.convertAndSend(queue, command);
    }
}
