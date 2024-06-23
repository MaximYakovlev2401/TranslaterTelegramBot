package ru.maxproject.translatebot.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.kafka.core.KafkaTemplate;
import ru.maxproject.translatebot.model.StringValue;

import java.util.function.Consumer;

public class DataSenderKafka implements DataSender {
    private static final Log log = LogFactory.getLog(DataSender.class);
    private final KafkaTemplate<String, StringValue> template;
    private final Consumer<StringValue> sendAsk;
    private final String topic;

    public DataSenderKafka(String topic,KafkaTemplate<String, StringValue> template, Consumer<StringValue> sendAsk) {
        this.template = template;
        this.sendAsk = sendAsk;
        this.topic = topic;
    }

    @Override
    public void send(StringValue value) {
        try {
            log.info("value:" + value);
            template.send(topic, value)
                    .whenComplete(
                            (result, ex) -> {
                                if (ex == null) {
                                    log.info(String.format("message id:{} was sent, offset:{}",
                                            value.id(),
                                            result.getRecordMetadata().offset()));
                                    sendAsk.accept(value);
                                } else {
                                    log.error(String.format("message id:{} was not sent, offset:{}",
                                            value.id(),
                                            ex));
                                }
                            });
        } catch (Exception ex) {
            log.error(String.format("send error? value:{}", value, ex));
        }
    }
}

