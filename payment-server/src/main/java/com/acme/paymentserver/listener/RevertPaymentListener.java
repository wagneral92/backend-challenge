package com.acme.paymentserver.listener;

import com.acme.paymentserver.event.RevertPaymentEvent;
import com.acme.paymentserver.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RevertPaymentListener implements ApplicationListener<RevertPaymentEvent> {

    @Autowired
    private PaymentService paymentService;

    @Override
    public void onApplicationEvent(final RevertPaymentEvent event) {
        this.paymentService.revertPayment(event.getRevertPaymentCommand());
    }
}
