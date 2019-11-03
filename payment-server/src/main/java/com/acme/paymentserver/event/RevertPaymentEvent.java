package com.acme.paymentserver.event;

import com.acme.paymentserver.queue.model.RevertPaymentCommand;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class RevertPaymentEvent extends ApplicationEvent {

    @Getter
    private RevertPaymentCommand revertPaymentCommand;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public RevertPaymentEvent(Object source, final RevertPaymentCommand revertPaymentCommand) {
        super(source);
        this.revertPaymentCommand = revertPaymentCommand;
    }
}
