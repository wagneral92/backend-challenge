package com.acme.orderserver.resource;

import com.acme.orderserver.dto.ResponseErrorDTO;
import com.acme.orderserver.exception.StoreNotFoundException;
import com.acme.orderserver.model.Order;
import com.acme.orderserver.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("order")
public class OrderResource {

    private final OrderService service;

    private final MessageSource messageSource;

    @Autowired
    public OrderResource(OrderService service, MessageSource messageSource) {
        this.service = service;
        this.messageSource = messageSource;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody @Valid final Order order) {
        return ResponseEntity.ok(service.create(order));
    }

    @PutMapping("{id}")
    public ResponseEntity<Order> update(@RequestBody @Valid final Order order, @PathVariable Long id) {
        Optional<Order> optionalOrder = this.service.findById(id);

        if (!optionalOrder.isPresent()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(service.update(order, id));
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        Optional<Order> optionalOrder = this.service.findById(id);

        if (!optionalOrder.isPresent()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(optionalOrder.get());
    }

    @GetMapping
    public ResponseEntity<Page<Order>> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") final int page,
                                              @RequestParam(value = "size", required = false, defaultValue = "10") final int size) {
        return ResponseEntity.ok(this.service.findAll(PageRequest.of(page, size)));
    }

    @ExceptionHandler({StoreNotFoundException.class})
    public ResponseEntity<ResponseErrorDTO> handleStoreNotFoundException(StoreNotFoundException ex) {
        return ResponseEntity.badRequest().body(ResponseErrorDTO.builder().erros(Arrays.asList(this.messageSource.getMessage("order.store-not-found",null, LocaleContextHolder.getLocale()))).build());
    }
}
