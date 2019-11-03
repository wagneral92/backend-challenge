package com.acme.orderserver.event;

import com.acme.orderserver.queue.model.RefundOrderCommand;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class RefundOrderEvent extends ApplicationEvent {

    @Getter
    private RefundOrderCommand refundOrderCommand;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public RefundOrderEvent(final Object source, final RefundOrderCommand refundOrderCommand) {
        super(source);
        this.refundOrderCommand = refundOrderCommand;
    }
}
