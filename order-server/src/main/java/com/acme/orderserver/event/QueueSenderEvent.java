package com.acme.orderserver.event;

import com.acme.orderserver.queue.model.Command;
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
    public QueueSenderEvent(Object source, Command command, String queue) {
        super(source);
        this.command = command;
        this.queue = queue;
    }
}
