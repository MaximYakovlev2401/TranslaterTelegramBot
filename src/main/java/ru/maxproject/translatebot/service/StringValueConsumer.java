package ru.maxproject.translatebot.service;

import ru.maxproject.translatebot.model.StringValue;

import java.util.List;

public interface StringValueConsumer {

    void accept(List<StringValue> values);
}
