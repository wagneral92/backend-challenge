package com.acme.paymentserver.queue.listener;

import com.acme.paymentserver.config.queue.RabbitConfig;
import com.acme.paymentserver.event.FinalizePaymentEvent;
import com.acme.paymentserver.event.RevertPaymentEvent;
import com.acme.paymentserver.queue.model.FinalizePaymentCommand;
import com.acme.paymentserver.queue.model.RevertPaymentCommand;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PaymentQueueListener {

    @Autowired
    private ApplicationEventPublisher publisher;

    @RabbitListener(queues = RabbitConfig.FINALIZE_PAYMENT)
    public void processPayment(final FinalizePaymentCommand finalizePaymentCommand) {
        this.publisher.publishEvent(
                new FinalizePaymentEvent(
                        this,
                        finalizePaymentCommand
                )
        );
    }

    @RabbitListener(queues = RabbitConfig.REVERT_PAYMENT)
    public void processRevertPayment(final RevertPaymentCommand revertPaymentCommand) {
        System.out.println(revertPaymentCommand.getPaymentId());
        this.publisher.publishEvent(
                new RevertPaymentEvent(
                        this,
                        revertPaymentCommand
                )
        );
    }
}
