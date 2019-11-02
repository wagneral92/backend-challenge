package com.acme.orderserver.listener;

import com.acme.orderserver.event.QueueSenderEvent;
import com.acme.orderserver.queue.QueueSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class QueueSenderListener implements ApplicationListener<QueueSenderEvent> {

    @Autowired
    private QueueSender queueSender;

    @Override
    public void onApplicationEvent(QueueSenderEvent event) {
        this.queueSender.send(event.getCommand(), event.getQueue());
    }
}
