package com.acme.orderserver.queue.model;

import lombok.*;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RevertRefundPaymentCommand extends Command {

    private Long orderId;

    private Long paymentId;
}
