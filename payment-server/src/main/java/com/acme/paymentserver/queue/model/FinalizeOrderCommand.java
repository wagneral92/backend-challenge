package com.acme.paymentserver.queue.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FinalizeOrderCommand extends Command{

    private Long paymentId;

    private Long orderId;
}
