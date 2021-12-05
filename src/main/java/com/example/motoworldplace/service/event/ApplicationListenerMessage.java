package com.example.motoworldplace.service.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Component
public class ApplicationListenerMessage {


    @EventListener(MessageEventCreate.class)
    public void onApplicationEvent(MessageEventCreate event){
        //event.
    }
}
