package ru.example.consumer.deserializer;

import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Deserializer;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

public class AvroDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {

    private static final String AVRO_DESERIALIZATION_ERROR_MSG = "Ошибка при десериализации по схеме AVRO %s";

    private final Class<T> avroClass;

    public AvroDeserializer(Class<T> avroClass) {
        this.avroClass = avroClass;
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        System.out.println("Serialized data input = '" + DatatypeConverter.printHexBinary(data) + "'");
        if (data == null) return null;
        SpecificDatumReader<T> reader = new SpecificDatumReader<>(avroClass);
        Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);
        T result;
        try {
            result = reader.read(null, decoder);
            System.out.println("Output object = '" + result + "'");
        } catch (IOException e) {
            throw new RuntimeException(String.format(AVRO_DESERIALIZATION_ERROR_MSG, avroClass.getCanonicalName()), e);
        }
        return result;
    }
}