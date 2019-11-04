package com.acme.orderserver.resource;

import com.acme.orderserver.dto.OrderDTO;
import com.acme.orderserver.dto.ResponseErrorDTO;
import com.acme.orderserver.exception.StoreNotFoundException;
import com.acme.orderserver.model.Order;
import com.acme.orderserver.service.OrderService;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    private final MessageSource messageSource;

    /**
     * @param service
     * @param modelMapper
     * @param messageSource
     */
    @Autowired
    public OrderResource(OrderService service, ModelMapper modelMapper, MessageSource messageSource) {
        this.service = service;
        this.modelMapper = modelMapper;
        this.messageSource = messageSource;
    }

    /**
     * @param order
     * @return
     */
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody @Valid final OrderDTO order) {
        return ResponseEntity.ok(service.create(this.modelMapper.map(order, Order.class)));
    }

    /**
     * @param order
     * @param id
     * @return
     */
    @PutMapping("{id}")
    public ResponseEntity<Order> update(@RequestBody @Valid final OrderDTO order, @PathVariable Long id) {
        Optional<Order> optionalOrder = this.service.findById(id);

        if (!optionalOrder.isPresent()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(service.update(this.modelMapper.map(order, Order.class), id));
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        Optional<Order> optionalOrder = this.service.findById(id);

        if (!optionalOrder.isPresent()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(optionalOrder.get());
    }

    /**
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<Order>> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") final int page,
                                              @RequestParam(value = "size", required = false, defaultValue = "10") final int size,
                                              @RequestParam(value = "search", required = false, defaultValue = "") final String search) {
        return ResponseEntity.ok(this.service.findAll(PageRequest.of(page, size), search));
    }

    /**
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({StoreNotFoundException.class})
    public ResponseEntity<ResponseErrorDTO> handleStoreNotFoundException(final StoreNotFoundException ex) {
        return ResponseEntity.badRequest().body(ResponseErrorDTO.builder().errors(Arrays.asList(this.messageSource.getMessage("order.store-not-found", null, LocaleContextHolder.getLocale()))).build());
    }
}
