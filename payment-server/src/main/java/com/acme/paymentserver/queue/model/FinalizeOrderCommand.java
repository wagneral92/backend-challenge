package com.acme.paymentserver.queue.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FinalizeOrderCommand extends Command{

    private Long paymentId;

    private Long orderId;
}
