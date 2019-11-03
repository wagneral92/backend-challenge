package com.acme.paymentserver.serviceAgents;

import com.acme.paymentserver.serviceAgents.fallBack.OrderServiceFallBack;
import com.acme.paymentserver.serviceAgents.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "order-server", fallback = OrderServiceFallBack.class)
public interface OrderService {

    @RequestMapping("/order/{id}")
    Order getOrderById(@PathVariable final Long id);
}
