package ru.example.producer.serializer;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Serializer;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
public class AvroSerializer<T extends SpecificRecordBase> implements Serializer<T> {

    private static final String AVRO_SERIALIZATION_ERROR_MSG = "Ошибка при сериализации по схеме AVRO %s";

    private final Class<T> avroClass;

    public AvroSerializer(Class<T> avroClass) {
        this.avroClass = avroClass;
    }

    @Override
    public byte[] serialize(String topic, T object) {
        System.out.println("Input object = '" + object + "'");
        if (object == null) return new byte[0];
        SpecificDatumWriter<T> writer = new SpecificDatumWriter<>(avroClass);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Encoder encoder = EncoderFactory.get().binaryEncoder(outputStream, null);
        byte[] result;
        try {
            writer.write(object, encoder);
            encoder.flush();
            outputStream.close();
            result = outputStream.toByteArray();
            System.out.println("Serialized data output = '" + DatatypeConverter.printHexBinary(result) + "'");
        } catch (IOException e) {
            throw new RuntimeException(String.format(AVRO_SERIALIZATION_ERROR_MSG, avroClass.getCanonicalName()), e);
        }
        return result;
    }
}