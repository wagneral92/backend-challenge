package com.acme.paymentserver.resource;

import com.acme.paymentserver.dto.PaymentDTO;
import com.acme.paymentserver.dto.ResponseErrorDTO;
import com.acme.paymentserver.dto.ResponseMessageDTO;
import com.acme.paymentserver.exception.OrderNotFoundException;
import com.acme.paymentserver.model.Payment;
import com.acme.paymentserver.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("payment")
public class PaymentResource {

    private final PaymentService service;
    private final ModelMapper mapper;
    private final MessageSource messageSource;

    public PaymentResource(PaymentService service, ModelMapper mapper, MessageSource messageSource) {
        this.service = service;
        this.mapper = mapper;
        this.messageSource = messageSource;
    }

    @PostMapping
    public ResponseEntity<ResponseMessageDTO> create(@RequestBody @Valid final PaymentDTO payment){
        return ResponseEntity.ok(this.service.create(this.mapper.map(payment, Payment.class)));
    }

    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<ResponseErrorDTO> handleStoreNotFoundException(OrderNotFoundException ex) {
        return ResponseEntity.badRequest().body(ResponseErrorDTO.builder().erros(Arrays.asList(this.messageSource.getMessage("payment.order-not-found",null, LocaleContextHolder.getLocale()))).build());
    }
}
