package com.acme.paymentserver.listener;

import com.acme.paymentserver.event.RevertRefundPaymentEvent;
import com.acme.paymentserver.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RevertRefundPaymentListener implements ApplicationListener<RevertRefundPaymentEvent> {

    @Autowired
    private PaymentService paymentService;

    @Override
    public void onApplicationEvent(RevertRefundPaymentEvent event) {
        this.paymentService.revertRefundPayment(event.getRevertRefundPaymentCommand());
    }
}
