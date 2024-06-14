package ru.maxproject.translatebot.service;

import ru.maxproject.translatebot.model.StringValue;

public interface DataSender {
    void send(StringValue value);
}
