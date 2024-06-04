package ru.maxproject.translatebot.model;

import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "user")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "request_chat_id", referencedColumnName = "chat_id")
    private Request request;

    private String mail;
}