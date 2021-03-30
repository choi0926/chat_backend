package com.choi.chat_backend.config;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import javax.security.auth.message.AuthException;

@Component
@Log4j2
public class WebSocketInterceptor implements ChannelInterceptor {

    @Value("auth.chat.token")
    private String chatToken;

    @SneakyThrows
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if(accessor.getCommand() == StompCommand.CONNECT){
            String authToken = accessor.getFirstNativeHeader("auth_chat");
            if(authToken.equals(chatToken)){
                throw new AuthException("Authentication failure");
            }
        }
        return message;
    }
}
