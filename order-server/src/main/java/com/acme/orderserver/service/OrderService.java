package com.acme.orderserver.service;

import com.acme.orderserver.exception.StoreNotFoundException;
import com.acme.orderserver.model.Order;
import com.acme.orderserver.repository.OrderItemRepository;
import com.acme.orderserver.repository.OrderRepository;
import com.acme.orderserver.service.contracts.IOrderService;
import com.acme.orderserver.serviceAgents.StoreService;
import com.acme.orderserver.serviceAgents.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static java.util.Objects.isNull;

import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository repository;
    private final OrderItemRepository orderItemRepository;
    private final StoreService storeService;

    /**
     * @param repository
     * @param orderItemRepository
     * @param storeService
     */
    public OrderService(OrderRepository repository, OrderItemRepository orderItemRepository, StoreService storeService) {
        this.repository = repository;
        this.orderItemRepository = orderItemRepository;
        this.storeService = storeService;
    }

    /**
     * @param order
     * @return
     */
    @Override
    public Order create(final Order order) {

        Store store = this.getStoreById(order.getStoreId());
        order.setStatus(Order.Status.CREATED);

        return repository.save(order);

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

            return repository.save(orderBase);
        }

        return null;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Optional<Order> findById(final Long id) {
        return repository.findById(id);
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Order> findAll(final Pageable pageable) {
        return repository.findAll(pageable);
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
