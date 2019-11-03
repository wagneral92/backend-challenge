package com.acme.paymentserver.listener;

import com.acme.paymentserver.event.FinalizePaymentEvent;
import com.acme.paymentserver.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class FinalisePaymentListener implements ApplicationListener<FinalizePaymentEvent> {

    @Autowired
    private PaymentService paymentService;

    @Override
    public void onApplicationEvent(final FinalizePaymentEvent event) {
        this.paymentService.FinalizePayment(event.getFinalizePaymentCommand());
    }
}
