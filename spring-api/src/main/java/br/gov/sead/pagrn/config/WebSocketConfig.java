package br.gov.sead.pagrn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
/*@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/stompendpoint")
                .setAllowedOrigins("http://localhost:4200")
                .withSockJS()
                .setWebSocketEnabled(true)
                .setHeartbeatTime(25000)
                .setDisconnectDelay(5000)
                .setClientLibraryUrl("/webjars/sockjs-client/1.1.2/sockjs.js")
                .setSessionCookieNeeded(false);
        //registry.addHandler(new ServerWebSocketHandler(), "/stompendpoint")
        //        .addInterceptors(new HttpSessionHandshakeInterceptor())
        //        .setAllowedOrigins("*")
        //        .setHandshakeHandler(new DefaultHandshakeHandler());
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new ServerWebSocketHandler();
    }*/
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override

    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stompendpoint").setAllowedOrigins("http://localhost:4200").withSockJS();
                //.withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

}