package br.gov.sead.pagrn.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MQlistener {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    //@SendTo("/stomp-endpoint")
    //@MessageMapping("/stomp-endpoint")
    @RabbitListener(queues = "myvoting_queue")
    @MessageMapping("/stompendpoint")
    @SendTo("/topic")
    public void listener(String txt){
        System.out.println(txt+"kkj");
        //simpMessagingTemplate.convertAndSend(txt+"kkj");

        simpMessagingTemplate.convertAndSend("/topic/stompendpoint",txt+"kkj1");
        simpMessagingTemplate.convertAndSend("/app/topic/stompendpoint",txt+"kkj2");
        simpMessagingTemplate.convertAndSend("/stompendpoint",txt+"kkj3");
    }
}
