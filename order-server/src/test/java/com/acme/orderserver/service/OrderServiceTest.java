package com.acme.orderserver.service;

import com.acme.orderserver.exception.StoreNotFoundException;
import com.acme.orderserver.model.Order;
import com.acme.orderserver.model.OrderItem;
import com.acme.orderserver.repository.OrderItemRepository;
import com.acme.orderserver.repository.OrderRepository;
import com.acme.orderserver.serviceAgents.StoreService;
import com.acme.orderserver.serviceAgents.model.Store;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
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
        final List<OrderItem> items = new ArrayList<>();
        this.order = new Order(null, 1L, "address", LocalDateTime.now(), Order.Status.CREATED, items);
    }

    @Test(expected = StoreNotFoundException.class)
    public void storeNotFoundWhenCreatedOrder() {
        Mockito.when(storeService.getStoreById(1L)).thenReturn(null);
        service.create(this.order);
    }

    @Test
    public void createOrderSuccess(){
        final Store store = new Store(1L, "loja 01", "address loja 01");

        final List<OrderItem> items = new ArrayList<>();
        final Order orderResponse = new Order(1L, 1L, "address", LocalDateTime.now(), Order.Status.CREATED, items);

        Mockito.when(storeService.getStoreById(1L)).thenReturn(store);
        Mockito.when(repository.save(this.order)).thenReturn(orderResponse);

        final Order orderCreated = this.service.create(order);

        Assertions.assertNotNull(orderCreated.getId());
    }

    @Test
    public void UpdateOrderSuccess(){

        final Store store = new Store(1L, "loja 01", "address loja 01");

        final List<OrderItem> items = new ArrayList<>();
        final Order orderResponse = new Order(1L, 1L, "address", LocalDateTime.now(), Order.Status.CREATED, items);

        final  Optional<Order> responseOrderOptional = Optional.of(orderResponse);
        Mockito.lenient().when(this.repository.findById(1L)).thenReturn(responseOrderOptional);
        Mockito.when(storeService.getStoreById(1L)).thenReturn(store);
        Mockito.when(repository.save(responseOrderOptional.get())).thenReturn(responseOrderOptional.get());

        final Order orderUpdate = service.update(order, 1L);

        Assertions.assertNotNull(orderUpdate.getId());
        Assertions.assertEquals(orderUpdate.getAddress(), "address");
    }

    @Test
    public void getOrderById() {
        final List<OrderItem> items = new ArrayList<>();
        final Optional<Order> responseOrder = Optional.of(new Order(null, 1L, "address", LocalDateTime.now(), Order.Status.CREATED, items));
        Mockito.lenient().when(repository.findById(1L)).thenReturn(responseOrder);

        final Optional<Order> order = service.findById(1L);

        Assertions.assertTrue(order.isPresent());

    }

}
