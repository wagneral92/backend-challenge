package com.acme.orderserver.queue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalizeOrderCommand extends Command {

    @JsonProperty("paymentId")
    private Long paymentId;

    @JsonProperty("orderId")
    private Long orderId;
}
