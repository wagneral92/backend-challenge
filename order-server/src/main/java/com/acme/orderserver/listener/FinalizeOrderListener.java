package com.acme.orderserver.listener;

import com.acme.orderserver.event.FinalizeOrderEvent;
import com.acme.orderserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class FinalizeOrderListener implements ApplicationListener<FinalizeOrderEvent> {

    @Autowired
    private OrderService orderService;

    @Override
    public void onApplicationEvent(FinalizeOrderEvent event) {
        this.orderService.finalizeOrder(event.getFinalizeOrderCommand());
    }
}
