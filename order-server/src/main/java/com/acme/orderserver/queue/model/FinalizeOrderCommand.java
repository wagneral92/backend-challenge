package com.acme.orderserver.queue.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class FinalizeOrderCommand extends Command {

    @JsonProperty("paymentId")
    private Long paymentId;

    @JsonProperty("orderId")
    private Long orderId;
}
