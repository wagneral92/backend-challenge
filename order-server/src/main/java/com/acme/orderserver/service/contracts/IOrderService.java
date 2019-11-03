package com.acme.orderserver.service.contracts;

import com.acme.orderserver.model.Order;
import com.acme.orderserver.queue.model.FinalizeOrderCommand;
import com.acme.orderserver.queue.model.RefundOrderCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IOrderService {

    Order create(final Order order);

    Order update(final Order order, final Long id);

    Optional<Order> findById(final Long id);

    Page<Order> findAll(final Pageable pageable);

    void finalizeOrder(final FinalizeOrderCommand command);

    void refundOrder(final RefundOrderCommand command);
}
