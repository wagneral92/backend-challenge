package com.acme.paymentserver.service;

import com.acme.paymentserver.config.queue.RabbitConfig;
import com.acme.paymentserver.dto.RefundDTO;
import com.acme.paymentserver.dto.ResponseMessageDTO;
import com.acme.paymentserver.event.QueueSenderEvent;
import com.acme.paymentserver.exception.*;
import com.acme.paymentserver.model.Payment;
import com.acme.paymentserver.queue.model.*;
import com.acme.paymentserver.repository.PaymentRepository;
import com.acme.paymentserver.service.contracts.IPaymentService;
import com.acme.paymentserver.serviceAgents.OrderService;
import com.acme.paymentserver.serviceAgents.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class PaymentService implements IPaymentService {

    private final OrderService orderService;
    private final PaymentRepository repository;
    private final ApplicationEventPublisher publisher;
    private final MessageSource messageSource;

    @Value("${app.days-for-refund}")
    private Long daysForRefund;

    /**
     * @param orderService
     * @param repository
     * @param publisher
     * @param messageSource
     */
    @Autowired
    public PaymentService(OrderService orderService, PaymentRepository repository, ApplicationEventPublisher publisher, MessageSource messageSource) {
        this.orderService = orderService;
        this.repository = repository;
        this.publisher = publisher;
        this.messageSource = messageSource;
    }

    /**
     * @param payment
     * @return
     */
    @Override
    public ResponseMessageDTO create(final Payment payment) {

        List<Payment> payments = this.repository.findByOrderId(payment.getOrderId());

        if (payments.size() > 0) {
            throw new PaymentExistsException();
        }

        Order order = this.getOrderById(payment.getOrderId());

        payment.setDate(LocalDateTime.now());
        payment.setStatus(Payment.Status.PROCESSING);
        repository.save(payment);

        publisher.publishEvent(new QueueSenderEvent(
                this,
                FinalizeOrderCommand.builder()
                        .paymentId(payment.getId())
                        .orderId(payment.getOrderId())
                        .build(),
                RabbitConfig.FINALIZE_ORDER
        ));

        return ResponseMessageDTO.builder().message(this.messageSource.getMessage("payment.created", null, LocaleContextHolder.getLocale())).build();
    }

    /**
     * @param refundDTO
     * @return
     */
    @Override
    public ResponseMessageDTO refund(final RefundDTO refundDTO) {
        List<Payment> payments = this.repository.findByOrderId(refundDTO.getOrderId());

        this.validRefund(refundDTO, payments);

        Payment payment = payments.get(0);
        if (refundDTO.getItems().size() <= 0) {
            payment.setStatus(Payment.Status.REFUNDED);
            this.repository.save(payment);
        }

        this.publisher.publishEvent(
                new QueueSenderEvent(
                        this,
                        RefundOrderCommand.builder()
                                .items(refundDTO.getItems())
                                .paymentId(payment.getId())
                                .orderId(refundDTO.getOrderId())
                                .build(),
                        RabbitConfig.REFUND_ORDER
                )
        );

        return ResponseMessageDTO.builder().message(this.messageSource.getMessage("payment.order.created-refund", null, LocaleContextHolder.getLocale())).build();
    }

    /**
     * @param finalizePaymentCommand
     */
    @Override
    public void FinalizePayment(final FinalizePaymentCommand finalizePaymentCommand) {
        Optional<Payment> optionalPayment = this.repository.findById(finalizePaymentCommand.getPaymentId());

        if (optionalPayment.isPresent()) {
            Payment paymentBase = optionalPayment.get();

            paymentBase.setStatus(Payment.Status.COMPLETED);
            this.repository.save(paymentBase);
        }
    }

    /**
     * @param revertPaymentCommand
     */
    @Override
    public void revertPayment(final RevertPaymentCommand revertPaymentCommand) {
        Optional<Payment> optionalPayment = this.repository.findById(revertPaymentCommand.getPaymentId());

        if (optionalPayment.isPresent()) {
            Payment paymentBase = optionalPayment.get();

            paymentBase.setStatus(Payment.Status.PENDING);
            this.repository.save(paymentBase);
        }
    }

    /**
     * @param revertRefundPaymentCommand
     */
    @Override
    public void revertRefundPayment(final RevertRefundPaymentCommand revertRefundPaymentCommand) {
        Optional<Payment> optionalPayment = this.repository.findById(revertRefundPaymentCommand.getPaymentId());

        if (optionalPayment.isPresent()) {
            Payment paymentBase = optionalPayment.get();

            paymentBase.setStatus(Payment.Status.COMPLETED);
            this.repository.save(paymentBase);
        }
    }

    /**
     * @param id
     * @return
     */
    private Order getOrderById(final Long id){
        Order order = this.orderService.getOrderById(id);

        if (isNull(order)) {
            throw new OrderNotFoundException();
        }

        return order;
    }

    /**
     *
     * @param refundDTO
     * @param payments
     */
    private void validRefund(final RefundDTO refundDTO, final List<Payment> payments) {

        Order order = this.getOrderById(refundDTO.getOrderId());

        if (payments.size() <= 0) {
            throw new PaymentNotFoundException();
        }

        if (payments.get(0).getStatus() != Payment.Status.COMPLETED) {
            throw new PaymentNotCompletedException();
        }

        if (!this.validItemsRefund(refundDTO, order)) {
            throw new OrderNotFoundException();
        }

        if (order.getConfirmationDate().plusDays(this.daysForRefund).compareTo(LocalDateTime.now()) <= 0) {
            throw new DateLimitRefundReachException();
        }
    }

    private Boolean validItemsRefund(final RefundDTO refundDTO, final Order order) {

        for (Long item : refundDTO.getItems()) {
            if (order.getItems().stream().filter(i -> i.getId() == item).count() <= 0) {
                return false;
            }
        }

        return true;

    }
}
