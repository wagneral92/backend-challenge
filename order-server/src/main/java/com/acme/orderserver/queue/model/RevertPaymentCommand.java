package com.acme.orderserver.queue.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RevertPaymentCommand extends Command {

    private Long paymentId;
}
