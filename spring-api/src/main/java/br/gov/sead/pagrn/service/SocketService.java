package br.gov.sead.pagrn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SocketService {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    SocketService(final SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessage(){
        messagingTemplate.convertAndSend("/topic/"+"vehicle","vtmnc");
    }
}
