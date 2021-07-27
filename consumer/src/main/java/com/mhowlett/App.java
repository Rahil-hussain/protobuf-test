package com.mhowlett;

import java.time.Duration;
import java.util.Properties;
import java.util.UUID;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import com.mhowlett.LogLineOuterClass.LogLine;

public class App
{
    public static void main(String[] args) throws ExecutionException, InterruptedException  {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", UUID.randomUUID().toString());
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer");
        props.put("schema.registry.url", "http://127.0.0.1:8081");

        String topic = "Florida";

        Consumer<String, LogLine> consumer = new KafkaConsumer<String, LogLine>(props);

        consumer.subscribe(Arrays.asList(topic));

        while (true) {
            ConsumerRecords<String, LogLine> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, LogLine> record : records) {
                System.out.printf("%s%n", record.value());
            }
        }

    }
}
