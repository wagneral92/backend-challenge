package com.acme.paymentserver.resource;

import com.acme.paymentserver.dto.PaymentDTO;
import com.acme.paymentserver.dto.RefundDTO;
import com.acme.paymentserver.dto.ResponseErrorDTO;
import com.acme.paymentserver.dto.ResponseMessageDTO;
import com.acme.paymentserver.exception.*;
import com.acme.paymentserver.model.Payment;
import com.acme.paymentserver.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     *
     * @param service
     * @param mapper
     * @param messageSource
     */
    @Autowired
    public PaymentResource(PaymentService service, ModelMapper mapper, MessageSource messageSource) {
        this.service = service;
        this.mapper = mapper;
        this.messageSource = messageSource;
    }

    /**
     *
     * @param payment
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDTO> create(@RequestBody @Valid final PaymentDTO payment){
        return ResponseEntity.ok(this.service.create(this.mapper.map(payment, Payment.class)));
    }

    /**
     *
     * @param refundDTO
     * @return
     */
    @PutMapping("/refund")
    public ResponseEntity<ResponseMessageDTO> refunded(@RequestBody @Valid final RefundDTO refundDTO){
        return ResponseEntity.ok(this.service.refund(refundDTO));
    }

    /**
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<ResponseErrorDTO> handleStoreNotFoundException(final OrderNotFoundException ex) {
        return ResponseEntity.badRequest().body(ResponseErrorDTO.builder().erros(Arrays.asList(this.messageSource.getMessage("payment.order-not-found",null, LocaleContextHolder.getLocale()))).build());
    }

    /**
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({PaymentExistsException.class})
    public ResponseEntity<ResponseErrorDTO> handlePaymentExistsException(final PaymentExistsException ex) {
        return ResponseEntity.badRequest().body(ResponseErrorDTO.builder().erros(Arrays.asList(this.messageSource.getMessage("payment.exists",null, LocaleContextHolder.getLocale()))).build());
    }

    /**
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({PaymentNotFoundException.class})
    public ResponseEntity<ResponseErrorDTO> handlePaymentNotFoundException(final PaymentNotFoundException ex) {
        return ResponseEntity.badRequest().body(ResponseErrorDTO.builder().erros(Arrays.asList(this.messageSource.getMessage("payment.not-found",null, LocaleContextHolder.getLocale()))).build());
    }

    /**
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({PaymentNotCompletedException.class})
    public ResponseEntity<ResponseErrorDTO> handlePaymentNotCompletedException(final PaymentNotCompletedException ex) {
        return ResponseEntity.badRequest().body(ResponseErrorDTO.builder().erros(Arrays.asList(this.messageSource.getMessage("payment.not-completed",null, LocaleContextHolder.getLocale()))).build());
    }

    /**
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({OrderItemNotFoundException.class})
    public ResponseEntity<ResponseErrorDTO> handleOrderItemNotFoundException(final OrderItemNotFoundException ex) {
        return ResponseEntity.badRequest().body(ResponseErrorDTO.builder().erros(Arrays.asList(this.messageSource.getMessage("payment.order-item-not-fount",null, LocaleContextHolder.getLocale()))).build());
    }

    /**
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({DateLimitRefundReachException.class})
    public ResponseEntity<ResponseErrorDTO> handleDateLimitRefundReachException(final DateLimitRefundReachException ex) {
        return ResponseEntity.badRequest().body(ResponseErrorDTO.builder().erros(Arrays.asList(this.messageSource.getMessage("payment.order.date-limit-refund",null, LocaleContextHolder.getLocale()))).build());
    }

}
