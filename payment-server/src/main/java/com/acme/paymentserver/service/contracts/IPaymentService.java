package com.acme.paymentserver.service.contracts;

import com.acme.paymentserver.dto.RefundDTO;
import com.acme.paymentserver.dto.ResponseMessageDTO;
import com.acme.paymentserver.model.Payment;
import com.acme.paymentserver.queue.model.FinalizePaymentCommand;
import com.acme.paymentserver.queue.model.RevertPaymentCommand;
import com.acme.paymentserver.queue.model.RevertRefundPaymentCommand;

public interface IPaymentService {

    ResponseMessageDTO create(final Payment payment);
    ResponseMessageDTO refund(final RefundDTO refundDTO);
    void FinalizePayment(final FinalizePaymentCommand finalizePaymentCommand);
    void revertPayment(final RevertPaymentCommand revertPaymentCommand);
    void revertRefundPayment(final RevertRefundPaymentCommand revertRefundPaymentCommand);
}
