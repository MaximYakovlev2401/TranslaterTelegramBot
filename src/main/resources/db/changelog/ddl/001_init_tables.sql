-- liquibase
-- formatted sql
-- changeset MaximYakovlev:init_tables

CREATE SCHEMA IF NOT EXISTS translatebot;

create type language as enum ('EN','RU');

create table if not exists user
(
    id      uuid primary key not null,
    chat_id varchar(50)
        constraint uk_user_chat_id
            unique,
    mail    varchar(50)      not null
);


create table if not exists request
(
    id         uuid primary key not null,
    chat_id    varchar(50)      not null
        references user (chat_id) on delete cascade on update cascade,
    message  varchar(200),
    from language,
    to language,
    created_at timestamp
);

CREATE TABLE IF NOT EXISTS shedlock.shedlock
(
    name       VARCHAR(64),
    lock_until TIMESTAMP(3) NULL,
    locked_at  TIMESTAMP(3) NULL,
    locked_by  VARCHAR(255),
    PRIMARY KEY (name)
)