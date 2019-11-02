package com.acme.paymentserver.service.contracts;

import com.acme.paymentserver.dto.ResponseMessageDTO;
import com.acme.paymentserver.model.Payment;

public interface IPaymentService {

    ResponseMessageDTO create(final Payment payment);
}
