package com.acme.paymentserver.queue.listener;

import com.acme.paymentserver.config.queue.RabbitConfig;
import com.acme.paymentserver.event.FinalizePaymentEvent;
import com.acme.paymentserver.event.RevertPaymentEvent;
import com.acme.paymentserver.event.RevertRefundPaymentEvent;
import com.acme.paymentserver.queue.model.FinalizePaymentCommand;
import com.acme.paymentserver.queue.model.RevertPaymentCommand;
import com.acme.paymentserver.queue.model.RevertRefundPaymentCommand;
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
        this.publisher.publishEvent(
                new RevertPaymentEvent(
                        this,
                        revertPaymentCommand
                )
        );
    }

    @RabbitListener(queues = RabbitConfig.REVERT_REFUND)
    public void revertRefund(final RevertRefundPaymentCommand revertRefundPaymentCommand) {
        this.publisher.publishEvent(
                new RevertRefundPaymentEvent(
                        this,
                        revertRefundPaymentCommand
                )
        );
    }
}
