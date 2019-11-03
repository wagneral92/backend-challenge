package com.acme.paymentserver.event;

import com.acme.paymentserver.queue.model.Command;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class QueueSenderEvent extends ApplicationEvent {

    @Getter
    private Command command;

    @Getter
    private String queue;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public QueueSenderEvent(final Object source, Command command, String queue) {
        super(source);
        this.command = command;
        this.queue = queue;
    }
}
