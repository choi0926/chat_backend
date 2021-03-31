package com.choi.chat_backend.controller;

import com.choi.chat_backend.model.Chat;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void publishChat(@Payload  Chat chat){
        log.info("publishChat : {}",chat);
        messagingTemplate.convertAndSend("/sub/chat/" + chat.getSender(), chat.getMessage());
    }
}