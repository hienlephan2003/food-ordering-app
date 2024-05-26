package com.foodapp.foodorderingapp.dto.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foodapp.foodorderingapp.enumeration.ChatType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatRequest {
    @NotNull
    ChatType type;
    @JsonProperty("first_user_id")
    Long firstUserId;
    @JsonProperty("second_user_id")
    Long secondUserId;
}
