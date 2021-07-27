package com.mhowlett;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import com.mhowlett.LogLineOuterClass.LogLine;

public class App
{
    public static void main(String[] args) throws ExecutionException, InterruptedException  {

        long nEvents = 10;

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer");
        props.put("schema.registry.url", "http://127.0.0.1:8081");

        String topic = "Florida";

        Producer<String, LogLine> producer = new KafkaProducer<>(props);

        Random rnd = new Random();

        for (long i = 0; i<nEvents; ++i) {
            LogLine event = EventGenerator.getNext(rnd);
            ProducerRecord<String, LogLine> record = new ProducerRecord<>(topic, event.getIp(), event);
            producer.send(record).get();
        }

        producer.close();

        System.out.println("Done!");
    }
}
