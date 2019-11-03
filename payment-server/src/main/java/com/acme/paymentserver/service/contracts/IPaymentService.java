package com.acme.paymentserver.service.contracts;

import com.acme.paymentserver.dto.ResponseMessageDTO;
import com.acme.paymentserver.model.Payment;
import com.acme.paymentserver.queue.model.FinalizePaymentCommand;
import com.acme.paymentserver.queue.model.RevertPaymentCommand;

public interface IPaymentService {

    ResponseMessageDTO create(final Payment payment);
    void FinalizePayment(FinalizePaymentCommand finalizePaymentCommand);
    void revertPayment(RevertPaymentCommand revertPaymentCommand);
}
