package ru.example.consumer1.errorhandlers;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

@Slf4j
public class KafkaErrorHandler implements CommonErrorHandler {

    @Override
    public void handleOtherException(Exception thrownException, Consumer<?, ?> consumer, MessageListenerContainer container, boolean batchListener) {
        handle(thrownException, consumer);
    }

    void handle(Exception exception, Consumer<?, ?> consumer) {
        log.error("Exception thrown: {} with cause: {}", exception.getMessage(), exception.getCause().getMessage());
        if (exception instanceof RecordDeserializationException ex) {
            consumer.seek(ex.topicPartition(), ex.offset() + 1L);
            consumer.commitSync();
        } else {
            log.error("Exception not handled", exception);
        }
    }
}