package com.acme.orderserver.queue.listener;

import com.acme.orderserver.config.queue.RabbitConfig;
import com.acme.orderserver.event.FinalizeOrderEvent;
import com.acme.orderserver.event.RefundOrderEvent;
import com.acme.orderserver.queue.model.FinalizeOrderCommand;
import com.acme.orderserver.queue.model.RefundOrderCommand;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrderQueueListener {

    @Autowired
    private ApplicationEventPublisher publisher;

    @RabbitListener(queues = RabbitConfig.FINALIZE_ORDER)
    public void processOrder(final FinalizeOrderCommand command) {
        this.publisher.publishEvent(
                new FinalizeOrderEvent(
                        this,
                        command
                )
        );
    }

    @RabbitListener(queues = RabbitConfig.REFUND_ORDER)
    public void refundOrder(final RefundOrderCommand command) {
        this.publisher.publishEvent(
                new RefundOrderEvent(
                        this,
                        command
                )
        );
    }
}
