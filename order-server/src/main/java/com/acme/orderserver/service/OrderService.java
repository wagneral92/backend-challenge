package com.acme.orderserver.service;

import com.acme.orderserver.config.queue.RabbitConfig;
import com.acme.orderserver.event.QueueSenderEvent;
import com.acme.orderserver.exception.StoreNotFoundException;
import com.acme.orderserver.model.Order;
import com.acme.orderserver.queue.model.FinalizeOrderCommand;
import com.acme.orderserver.queue.model.FinalizePaymentCommand;
import com.acme.orderserver.queue.model.RevertPaymentCommand;
import com.acme.orderserver.repository.OrderItemRepository;
import com.acme.orderserver.repository.OrderRepository;
import com.acme.orderserver.service.contracts.IOrderService;
import com.acme.orderserver.serviceAgents.StoreService;
import com.acme.orderserver.serviceAgents.model.Store;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static java.util.Objects.isNull;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository repository;
    private final OrderItemRepository orderItemRepository;
    private final StoreService storeService;
    private final ApplicationEventPublisher publisher;

    /**
     * @param repository
     * @param orderItemRepository
     * @param storeService
     * @param publisher
     */
    public OrderService(OrderRepository repository, OrderItemRepository orderItemRepository, StoreService storeService, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.orderItemRepository = orderItemRepository;
        this.storeService = storeService;
        this.publisher = publisher;
    }

    /**
     * @param order
     * @return
     */
    @Override
    public Order create(final Order order) {

        Store store = this.getStoreById(order.getStoreId());
        order.setStatus(Order.Status.CREATED);

        return this.repository.save(order);

    }

    /**
     * @param order
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Order update(final Order order, final Long id) {
        Optional<Order> orderOptional = this.repository.findById(id);

        if (orderOptional.isPresent()) {
            Store store = this.getStoreById(order.getStoreId());

            Order orderBase = orderOptional.get();

            orderBase.setAddress(order.getAddress());
            orderBase.setStoreId(order.getStoreId());
            orderBase.getItems().clear();
            orderBase.getItems().addAll(order.getItems());

            return this.repository.save(orderBase);
        }

        return null;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Optional<Order> findById(final Long id) {
        return this.repository.findById(id);
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Order> findAll(final Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    /**
     * @param command
     */
    @Override
    public void finalizeOrder(FinalizeOrderCommand command) {
        Optional<Order> optionalOrder = this.repository.findById(command.getOrderId());

        if (optionalOrder.isPresent()) {
            try {
                Order baseOrder = optionalOrder.get();

                baseOrder.setStatus(Order.Status.PAY);
                baseOrder.setConfirmationDate(LocalDateTime.now());
                repository.save(new Order());

                this.publisher.publishEvent(
                        new QueueSenderEvent(
                                this,
                                FinalizePaymentCommand.builder()
                                        .orderId(command.getOrderId())
                                        .paymentId(command.getPaymentId())
                                        .build(),
                                RabbitConfig.FINALIZE_PAYMENT
                        )
                );

            } catch (Exception e) {

                this.publisher.publishEvent(
                        new QueueSenderEvent(
                                this,
                                RevertPaymentCommand.builder()
                                        .paymentId(command.getPaymentId())
                                        .build(),
                                RabbitConfig.REVERT_PAYMENT
                        )
                );
            }

        }


    }

    /**
     * @param id
     * @return
     */
    private Store getStoreById(Long id) {
        Store store = this.storeService.getStoreById(id);

        if (isNull(store)) {
            throw new StoreNotFoundException();
        }

        return store;
    }

}
