package com.acme.paymentserver.event;

import com.acme.paymentserver.queue.model.FinalizePaymentCommand;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class FinalizePaymentEvent extends ApplicationEvent {

    @Getter
    private FinalizePaymentCommand finalizePaymentCommand;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public FinalizePaymentEvent(final Object source, final FinalizePaymentCommand finalizePaymentCommand) {
        super(source);
        this.finalizePaymentCommand = finalizePaymentCommand;
    }
}
