package com.acme.paymentserver.serviceAgents.fallBack;

import com.acme.paymentserver.serviceAgents.OrderService;
import com.acme.paymentserver.serviceAgents.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderServiceFallBack implements OrderService {

    @Override
    public Order getOrderById(Long id) {
        return null;
    }
}
