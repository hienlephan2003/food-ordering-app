package com.foodapp.foodorderingapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.TimeZone;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
@Entity
@Getter
@Setter
@Builder
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    private String message;
    private ZonedDateTime sendAt;
    @Column(columnDefinition = "json")
    private String media;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
    @ManyToMany
    @JoinTable(
            name = "message_read_by",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> readBy;

}
