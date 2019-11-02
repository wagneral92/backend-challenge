package com.acme.orderserver.service;

import com.acme.orderserver.exception.StoreNotFoundException;
import com.acme.orderserver.model.Order;
import com.acme.orderserver.repository.OrderRepository;
import com.acme.orderserver.service.contracts.IOrderService;
import com.acme.orderserver.serviceAgents.StoreService;
import com.acme.orderserver.serviceAgents.model.Store;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository repository;
    private final StoreService storeService;
    private final RestTemplate restTemplate;

    @Value("${app.toreService.baseUrl}")
    private String baseUrl;

    /**
     *
     * @param repository
     * @param storeService
     * @param restTemplate
     */
    public OrderService(OrderRepository repository, StoreService storeService, RestTemplate restTemplate) {
        this.repository = repository;
        this.storeService = storeService;
        this.restTemplate = restTemplate;
    }

    /**
     *
     * @param order
     * @return
     */
    @Override
    public Order create(final Order order) {
        ResponseEntity<Store> responseEntity = this.getStoreById(order.getStoreId());

        if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            throw new StoreNotFoundException();
        }

        return repository.save(order);
    }

    /**
     *
     * @param order
     * @param id
     * @return
     */
    @Override
    public Order update(final Order order, final Long id) {
        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Order> findById(final Long id) {
        return Optional.empty();
    }

    /**
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<Order> findAll(final Pageable pageable) {
        return null;
    }


    @HystrixCommand(fallbackMethod = "registerOrderRedis")
    private ResponseEntity<Store> getStoreById(final Long storeId) {
        ResponseEntity<Store> responseEntity = this.restTemplate.getForEntity(this.baseUrl + "store/{id}", Store.class, storeId);

        return responseEntity;
    }

    public ResponseEntity<Store> registerOrderRedis(final Long storeId) {
        return null;
    }
}
