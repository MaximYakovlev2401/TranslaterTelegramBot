package ru.maxproject.translatebot.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.maxproject.translatebot.model.StringValue;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class StringValueSource implements ValueSource {
    private static final Logger log = LoggerFactory.getLogger(StringValueSource.class);
    private final AtomicLong nextValue = new AtomicLong(1);

    public StringValueSource(DataSender dataSender) {
        this.valueConsumer = dataSender;
    }

    private final DataSender valueConsumer;

    @Override
    public void generate() {
        var executor = Executors.newScheduledThreadPool(1);
executor.scheduleAtFixedRate(() -> valueConsumer.send(makeStringValue()), 0, 1, TimeUnit.SECONDS);
    }

    private StringValue makeStringValue() {
        var id = nextValue.getAndIncrement();
        return new StringValue(id, "stVal:" +id);
    }
}
