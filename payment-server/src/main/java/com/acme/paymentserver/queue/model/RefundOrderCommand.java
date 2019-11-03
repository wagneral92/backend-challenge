package com.acme.paymentserver.queue.model;

import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundOrderCommand extends Command {

    private Long orderId;
    private Long paymentId;
    private List<Long> items;
}
