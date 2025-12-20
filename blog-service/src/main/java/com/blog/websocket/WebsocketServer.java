package com.blog.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@ServerEndpoint("/ws/{id}")
public class WebsocketServer {

    private static Map<String,Session> sessionMaps = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id){
        //建立连接时回调的方法
        log.info("与{}建立websocket连接",id);
        sessionMaps.put(id,session);
    }
    @OnClose
    public void onClose(Session session,@PathParam("id") String id){
        //关闭连接时回调的方法
        log.info("与{}断开websocket连接",id);
        sessionMaps.remove(id);
    }
    @OnMessage
    public void onMessage(String message,@PathParam("id") String id) throws JsonProcessingException {
        //接收到消息时回调的方法
        log.info("收到{}发来的消息{}",id,message);
        ObjectMapper objectMapper = new ObjectMapper();

        if(objectMapper.readValue(message,String.class).equals("ping")){
            try {
                log.info("pong");
                sendMessageToAdmin("pong");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessageToAdmin(String message) throws IOException {
        Session session = sessionMaps.get("admin");
        session.getBasicRemote().sendText(message);
    }
}
