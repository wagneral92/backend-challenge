package com.acme.orderserver.event;

import com.acme.orderserver.queue.model.FinalizeOrderCommand;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class FinalizeOrderEvent extends ApplicationEvent {

    @Getter
    private FinalizeOrderCommand finalizeOrderCommand;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public FinalizeOrderEvent(final Object source, final FinalizeOrderCommand finalizeOrderCommand) {
        super(source);
        this.finalizeOrderCommand = finalizeOrderCommand;
    }
}
