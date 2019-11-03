package com.acme.paymentserver.queue.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RevertPaymentCommand extends Command {

    private Long paymentId;
}
