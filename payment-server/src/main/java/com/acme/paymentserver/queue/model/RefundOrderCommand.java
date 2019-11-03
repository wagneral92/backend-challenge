package com.acme.paymentserver.queue.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefundOrderCommand extends Command {

    private Long orderId;
    private Long paymentId;
    private List<Long> items;
}
