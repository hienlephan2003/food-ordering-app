package com.foodapp.foodorderingapp.controller;

import com.foodapp.foodorderingapp.dto.message.CreateMessageRequest;
import com.foodapp.foodorderingapp.entity.Message;
import com.foodapp.foodorderingapp.repository.MessageJpaRepository;
import com.foodapp.foodorderingapp.repository.UserJpaRepository;
import com.foodapp.foodorderingapp.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
    private final MessageService messageService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public MessageController(MessageJpaRepository messageJpaRepository, UserJpaRepository userJpaRepository, MessageService messageService) {
        this.messageService = messageService;
    }
    @MessageMapping("/newMessage")
    public Message getContent(@Payload CreateMessageRequest createMessageRequest){
         System.out.println("Go to message mapping");
        Message message = messageService.save(createMessageRequest);
        messageService.sendToUser(message);
        return message;
    }
    @GetMapping("/messages")
    public Page<Message> getMessages(@RequestParam int offset, @RequestParam int limit, @RequestParam long chatId) {
        return messageService.getMessages(offset, limit, chatId);
    }
}
