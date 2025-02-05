package ru.example.producer.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.example.producer.avro.schemas.Person;
import ru.example.producer.avro.schemas.Car;
import ru.example.producer.serializer.AvroSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class Config {

    private final KafkaProperties properties;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers().get(0));
        return props;
    }

    @Bean
    public ProducerFactory<String, Person> personProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs(), new StringSerializer(), new AvroSerializer<>(Person.class));
    }

    @Bean
    public KafkaTemplate<String, Person> personKafkaTemplate() {
        return new KafkaTemplate<>(personProducerFactory());
    }

    @Bean
    public ProducerFactory<String, Car> carProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs(), new StringSerializer(), new AvroSerializer<>(Car.class));
    }

    @Bean
    public KafkaTemplate<String, Car> carKafkaTemplate() {
        return new KafkaTemplate<>(carProducerFactory());
    }
}