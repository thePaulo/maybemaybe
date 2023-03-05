package br.gov.sead.pagrn.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ServerWebSocketHandler extends TextWebSocketHandler implements SubProtocolCapable {
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        sessions.add(session);
        TextMessage message = new TextMessage("one-time message from server");

        session.sendMessage(message);
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }
    @Scheduled(fixedRate = 10)
    void sendPeriodicMessages() throws IOException {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                String broadcast = "server periodic message " + LocalTime.now();
                session.sendMessage(new TextMessage(broadcast));
            }
        }
    }
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String request = message.getPayload();
        String response = String.format("response from server to '%s'", HtmlUtils.htmlEscape(request));

        session.sendMessage(new TextMessage(response));
    }
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        //logger.info("Server transport error: {}", exception.getMessage());
    }
    @Override
    public List<String> getSubProtocols() {
        return Collections.singletonList("subprotocol.demo.websocket");
    }
}