package com.company.project.util;

import com.alibaba.fastjson.JSON;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Conversation;
import com.company.project.model.Message;
import com.company.project.model.MessageVo;
import com.company.project.service.ConversationService;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import javax.websocket.*;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint(value = "/websocket/{uid}")
@Component
public class WebSocketChatServer {
    private final Logger log = LoggerFactory.getLogger(WebSocketChatServer.class);

    ConversationService conversationService = SpringUtil.getBean(ConversationService.class);

    /**
     * 用于存放所有在线客户端
     */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    private Gson gson = new Gson();



    @OnOpen
    public void onOpen(@PathParam("uid") String uid, Session session) {
        log.info("有新的客户端上线: {}", session.getId());
        clients.put(uid,session);
    }

    @OnClose
    public void onClose(Session session) {
        String sessionId = session.getId();
        log.info("有客户端离线: {}", sessionId);
        clients.remove(sessionId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        String sessionId = session.getId();
        if (clients.get(sessionId) != null) {
            log.info("发生了错误,移除客户端: {}", sessionId);
            clients.remove(sessionId);
        }
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("收到客户端发来的消息: {}", message);
        Message messageObj = gson.fromJson(message, Message.class);
        Conversation conversation = new Conversation();
        conversation.setConid((Integer.parseInt(messageObj.getFromId())  < Integer.parseInt(messageObj.getToId()) ?
                String.format("%s_%s",messageObj.getFromId(),messageObj.getToId()) :
                String.format("%s_%s",messageObj.getToId(),messageObj.getFromId())));
        conversation.setFromid(Integer.parseInt(messageObj.getFromId()));
        conversation.setToid(Integer.parseInt(messageObj.getToId()));
        conversation.setMessage(messageObj.getMessage());
        conversation.setDate(new Date());
        if (clients.containsKey(messageObj.getToId())){//在线
            log.info(messageObj.getToId()+"号用户在线");
            conversation.setState("已读");
            conversationService.save(conversation);
            this.sendTo(new MessageVo(conversation));
        }else {
            conversation.setState("未读");
            conversationService.save(conversation);
        }
    }

    /**
     * 发送消息
     *
     * @param message 消息对象
     */
    private void sendTo(MessageVo message) {
        Session s = clients.get(message.getToId().toString());
        if (s != null) {
            try {
                s.getBasicRemote().sendText(JSON.toJSONString(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
