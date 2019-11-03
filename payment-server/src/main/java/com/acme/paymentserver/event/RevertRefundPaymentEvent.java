package com.acme.paymentserver.event;

import com.acme.paymentserver.queue.model.RevertRefundPaymentCommand;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class RevertRefundPaymentEvent extends ApplicationEvent {

    @Getter
    private RevertRefundPaymentCommand revertRefundPaymentCommand;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public RevertRefundPaymentEvent(final Object source, final RevertRefundPaymentCommand revertRefundPaymentCommand) {
        super(source);
        this.revertRefundPaymentCommand = revertRefundPaymentCommand;
    }
}
