package com.acme.paymentserver.event;

import com.acme.paymentserver.queue.model.Command;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class QueueSenderEvent extends ApplicationEvent {

    @Getter
    private Command command;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public QueueSenderEvent(Object source, Command command) {
        super(source);
        this.command = command;
    }
}
