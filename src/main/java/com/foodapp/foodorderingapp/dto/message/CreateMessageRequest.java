package com.foodapp.foodorderingapp.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessageRequest {
    private String message;
    @JsonProperty("sender_id")
    private Long senderId;
    @JsonProperty("chat_id")
    private Long chatId;
}

