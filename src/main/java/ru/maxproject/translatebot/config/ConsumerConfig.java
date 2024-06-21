package ru.maxproject.translatebot.config;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import ru.maxproject.translatebot.model.StringValue;
import ru.maxproject.translatebot.service.StringValueConsumer;
import ru.maxproject.translatebot.service.StringValueConsumerLogger;

import java.util.List;
import java.util.concurrent.Executors;

import static org.springframework.kafka.support.serializer.JsonDeserializer.TYPE_MAPPINGS;

@Configuration
@Slf4j
public class ConsumerConfig {
    public final String topicName;

    public ConsumerConfig(@Value("${application.kafka.topic}") String topicName) {
        this.topicName = topicName;
    }


    @Bean
    public ConsumerFactory<String, StringValue> consumerFactory(
            KafkaProperties kafkaProperties, ObjectMapper mapper) {
        var props = kafkaProperties.buildProducerProperties();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(TYPE_MAPPINGS, "ru.maxproject.translatebot.model.StringValue");
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 3_000);


        var kafkaConsumerFactory = new DefaultKafkaConsumerFactory<String, StringValue>(props);
        kafkaConsumerFactory.setValueDeserializer(new org.springframework.kafka.support.serializer.JsonDeserializer<>(mapper));
        return kafkaConsumerFactory;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, StringValue>>
    listenerContainerFactory(ConsumerFactory<String, StringValue> consumerFactory) {
        int concurrency = 1;
        var factory = new ConcurrentKafkaListenerContainerFactory<String, StringValue>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
        factory.setConcurrency(1);
        factory.getContainerProperties().setIdleBetweenPolls(1_000);
        factory.getContainerProperties().setPollTimeout(1_000);
        var executor =
                Executors.newFixedThreadPool(concurrency, task -> new Thread(task, "kafka-task"));
        var listenerTaskExecutor = new ConcurrentTaskExecutor(executor);
        factory.getContainerProperties().setListenerTaskExecutor(listenerTaskExecutor);
        return factory;
    }

    @Bean
    public StringValueConsumer stringValueConsumerLogger() {
        return new StringValueConsumerLogger();
    }

    public KafkaClient stringValueConsumer(StringValueConsumer stringValueConsumer) {
        return new KafkaClient(stringValueConsumer);
    }

    public static class KafkaClient {
        private final StringValueConsumer stringValueConsumer;

        public KafkaClient(StringValueConsumer stringValueConsumer) {
            this.stringValueConsumer = stringValueConsumer;
        }

        @KafkaListener(topics = "${application.kafka.topic}", containerFactory = "listenerContainerFactory")
        public void listen(@Payload List<StringValue> values) {
            log.info("values, values.size " + values.size());
            stringValueConsumer.accept(values);
        }

    }
}
