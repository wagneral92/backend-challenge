package com.acme.paymentserver;

import com.acme.paymentserver.dto.RefundDTO;
import com.acme.paymentserver.dto.ResponseMessageDTO;
import com.acme.paymentserver.exception.DateLimitRefundReachException;
import com.acme.paymentserver.exception.OrderNotFoundException;
import com.acme.paymentserver.exception.PaymentExistsException;
import com.acme.paymentserver.model.Payment;
import com.acme.paymentserver.repository.PaymentRepository;
import com.acme.paymentserver.service.PaymentService;
import com.acme.paymentserver.serviceAgents.OrderService;
import com.acme.paymentserver.serviceAgents.model.Order;
import com.acme.paymentserver.serviceAgents.model.OrderItem;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {

    @Mock
    private OrderService orderService;
    @Mock
    private PaymentRepository repository;
    @Mock
    private ApplicationEventPublisher publisher;
    @Mock
    private MessageSource messageSource;
//    @Mock
//    private Long daysForRefund;

    @InjectMocks
    private PaymentService service;

    @Test(expected = PaymentExistsException.class)
    public void paymentExists() {
        Payment pay = new Payment(1L, 1L, "1256348695874125", LocalDateTime.now(), Payment.Status.COMPLETED);
        List<Payment> payments = Arrays.asList(pay);

        Mockito.when(repository.findByOrderId(1L)).thenReturn(payments);

        service.create(pay);
    }

    @Test
    public void createOrderPaymentSuccess() {
        final Payment pay = new Payment(1L, 1L, "1256348695874125", LocalDateTime.now(), Payment.Status.COMPLETED);
        final List<OrderItem> items = Arrays.asList();
        final Order order = new Order(1L, 1L, "address", LocalDateTime.now(), Arrays.asList());

        final List<Payment> payments = Arrays.asList();

        Mockito.when(repository.findByOrderId(1L)).thenReturn(payments);
        Mockito.when(orderService.getOrderById(1L)).thenReturn(order);
        Mockito.when(repository.save(pay)).thenReturn(pay);

        final ResponseMessageDTO response = service.create(pay);

        Assertions.assertNotNull(response);
    }

    @Test(expected = OrderNotFoundException.class)
    public void orderNotFoundWhenCreatePayment(){
        final Payment pay = new Payment(1L, 1L, "1256348695874125", LocalDateTime.now(), Payment.Status.COMPLETED);
        service.create(pay);
    }

    @Test(expected = DateLimitRefundReachException.class)
    public void dateLimitRefundReachWhenRefund(){
        final Payment pay = new Payment(1L, 1L, "1256348695874125", LocalDateTime.now(), Payment.Status.COMPLETED);
        final List<Payment> payments = Arrays.asList(pay);
        final RefundDTO refundDTO = new RefundDTO(1L,Arrays.asList());
        final List<OrderItem> items = Arrays.asList();
        final Order order = new Order(1L, 1L, "address", LocalDateTime.now().plusDays(-30), items);
        Mockito.when(orderService.getOrderById(1L)).thenReturn(order);
        ReflectionTestUtils.setField(service, "daysForRefund", 10L);

        Mockito.when(repository.findByOrderId(1L)).thenReturn(payments);
        service.refund(refundDTO);
    }

    @Test
    public void refundCreatedSuccess(){
        final Payment pay = new Payment(1L, 1L, "1256348695874125", LocalDateTime.now(), Payment.Status.COMPLETED);
        final List<Payment> payments = Arrays.asList(pay);
        final RefundDTO refundDTO = new RefundDTO(1L,Arrays.asList());
        final List<OrderItem> items = Arrays.asList();
        final Order order = new Order(1L, 1L, "address", LocalDateTime.now(), items);
        Mockito.when(orderService.getOrderById(1L)).thenReturn(order);
        ReflectionTestUtils.setField(service, "daysForRefund", 10L);

        Mockito.when(repository.findByOrderId(1L)).thenReturn(payments);
        final ResponseMessageDTO response = service.refund(refundDTO);

        Assertions.assertNotNull(response);
    }
}
