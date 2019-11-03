package com.acme.paymentserver.service;

import com.acme.paymentserver.config.queue.RabbitConfig;
import com.acme.paymentserver.dto.ResponseMessageDTO;
import com.acme.paymentserver.event.QueueSenderEvent;
import com.acme.paymentserver.exception.OrderNotFoundException;
import com.acme.paymentserver.model.Payment;
import com.acme.paymentserver.queue.model.FinalizeOrderCommand;
import com.acme.paymentserver.queue.model.FinalizePaymentCommand;
import com.acme.paymentserver.queue.model.RevertPaymentCommand;
import com.acme.paymentserver.repository.PaymentRepository;
import com.acme.paymentserver.service.contracts.IPaymentService;
import com.acme.paymentserver.serviceAgents.OrderService;
import com.acme.paymentserver.serviceAgents.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
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

        if (payments.size() <= 0) {

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

        return ResponseMessageDTO.builder().message(this.messageSource.getMessage("payment.exists", null, LocaleContextHolder.getLocale())).build();
    }

    @Override
    public void FinalizePayment(FinalizePaymentCommand finalizePaymentCommand) {
        Optional<Payment> optionalPayment = this.repository.findById(finalizePaymentCommand.getPaymentId());

        if (optionalPayment.isPresent()) {
            Payment paymentBase = optionalPayment.get();

            paymentBase.setStatus(Payment.Status.COMPLETED);
            this.repository.save(paymentBase);
        }
    }

    @Override
    public void revertPayment(RevertPaymentCommand revertPaymentCommand) {
        Optional<Payment> optionalPayment = this.repository.findById(revertPaymentCommand.getPaymentId());

        if (optionalPayment.isPresent()) {
            Payment paymentBase = optionalPayment.get();

            paymentBase.setStatus(Payment.Status.PENDING);
            this.repository.save(paymentBase);
        }
    }

    /**
     * @param id
     * @return
     */
    private Order getOrderById(Long id) {
        Order order = this.orderService.getOrderById(id);

        if (isNull(order)) {
            throw new OrderNotFoundException();
        }

        return order;
    }
}
