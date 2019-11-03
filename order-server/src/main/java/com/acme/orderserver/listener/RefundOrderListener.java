package com.acme.orderserver.listener;

import com.acme.orderserver.event.RefundOrderEvent;
import com.acme.orderserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RefundOrderListener implements ApplicationListener<RefundOrderEvent> {

    @Autowired
    private OrderService orderService;

    @Override
    public void onApplicationEvent(final RefundOrderEvent event) {
        this.orderService.refundOrder(event.getRefundOrderCommand());
    }
}
