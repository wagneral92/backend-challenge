package com.acme.orderserver.queue.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefundOrderCommand extends Command{

    private Long orderId;
    private Long PaymentId;
    private List<Long> items;
}
