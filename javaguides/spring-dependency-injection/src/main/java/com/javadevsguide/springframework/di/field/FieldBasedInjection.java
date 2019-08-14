package com.javadevsguide.springframework.di.field;

import com.javadevsguide.springframework.di.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FieldBasedInjection {

    @Autowired
    @Qualifier("TwitterService")
    private MessageService messageService;

    public void processMsg(String message) {
        messageService.sendMsg(message);
    }
}
