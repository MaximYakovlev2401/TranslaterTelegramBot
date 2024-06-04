package ru.maxproject.translatebot.model;


import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(columnDefinition = "from", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language from;

    @Column(columnDefinition = "to", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language to;
}
