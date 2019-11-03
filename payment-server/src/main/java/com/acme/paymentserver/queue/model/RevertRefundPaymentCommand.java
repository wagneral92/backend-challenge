package com.acme.paymentserver.queue.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevertRefundPaymentCommand extends Command {

    private Long orderId;

    private Long paymentId;
}
