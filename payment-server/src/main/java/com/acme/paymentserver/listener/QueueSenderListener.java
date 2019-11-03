package com.acme.paymentserver.listener;

import com.acme.paymentserver.event.QueueSenderEvent;
import com.acme.paymentserver.queue.QueueSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class QueueSenderListener implements ApplicationListener<QueueSenderEvent> {

    @Autowired
    private QueueSender queueSender;

    @Override
    public void onApplicationEvent(final QueueSenderEvent event) {
        this.queueSender.send(event.getCommand(), event.getQueue());
    }
}
