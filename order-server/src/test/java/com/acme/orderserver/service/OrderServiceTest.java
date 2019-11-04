package com.acme.orderserver.service;

import com.acme.orderserver.exception.StoreNotFoundException;
import com.acme.orderserver.model.Order;
import com.acme.orderserver.model.OrderItem;
import com.acme.orderserver.repository.OrderItemRepository;
import com.acme.orderserver.repository.OrderRepository;
import com.acme.orderserver.serviceAgents.StoreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private StoreService storeService;

    @Mock
    private ApplicationEventPublisher publisher;

    @InjectMocks
    private OrderService service;

    private Order order;

    public OrderServiceTest() {
        List<OrderItem> items = new ArrayList<>();
        this.order = new Order(null, 1L, "address", LocalDateTime.now(), Order.Status.CREATED, items);
    }

    @Test
    public void createOrderStoreNotFoundException() {
        List<OrderItem> items = new ArrayList<>();
        Order responseStore = new Order(null, 1L, "address", LocalDateTime.now(), Order.Status.CREATED, items);
        Assertions.assertThrows(StoreNotFoundException.class, () -> {
            Mockito.lenient().when(service.create(this.order)).thenReturn(responseStore);
        });
    }

    @Test
    public void getOrderById() {
        List<OrderItem> items = new ArrayList<>();
        Optional<Order> responseStore = Optional.of(new Order(null, 1L, "address", LocalDateTime.now(), Order.Status.CREATED, items));
        Mockito.lenient().when(service.findById(1L)).thenReturn(responseStore);
        Assertions.assertTrue(responseStore.isPresent());

    }

}
