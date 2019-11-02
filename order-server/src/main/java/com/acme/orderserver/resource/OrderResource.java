package com.acme.orderserver.resource;

import com.acme.orderserver.dto.ResponseError;
import com.acme.orderserver.exception.StoreNotFoundException;
import com.acme.orderserver.model.Order;
import com.acme.orderserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.Arrays;

@RestController
@RequestMapping("order")
public class OrderResource {

    private final OrderService service;

    private final RestTemplate restTemplate;

    @Autowired
    public OrderResource(OrderService service, RestTemplate restTemplate) {
        this.service = service;
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody @Valid final Order order){
        return ResponseEntity.ok(service.create(order));
    }

    @ExceptionHandler({StoreNotFoundException.class})
    public ResponseEntity<ResponseError> handleStoreNotFoundException(StoreNotFoundException ex) {
        return ResponseEntity.badRequest().body(ResponseError.builder().erros(Arrays.asList("A loja informada não foi localizada")).build());
    }
}
