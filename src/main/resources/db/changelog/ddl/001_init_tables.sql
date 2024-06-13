-- liquibase
-- formatted sql
-- changeset MaximYakovlev:init_tables

CREATE SCHEMA IF NOT EXISTS translatebot;

create table if not exists client
(
    id      uuid primary key not null,
    chat_id varchar(50)
        constraint uk_client_chat_id
            unique,
    mail    varchar(50)      not null
);


create table if not exists request
(
    id         uuid primary key not null,
    chat_id    varchar(50)      not null
        references client (chat_id) on delete cascade on update cascade,
    message  varchar(200),
    email_from varchar(50),
    email_to varchar(50),
    created_at timestamp
);
