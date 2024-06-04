package ru.maxproject.translatebot.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;
@Data
@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "message")
    private String message;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(columnDefinition = "email_from", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language from;

    @Column(columnDefinition = "email_to", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language to;
}
