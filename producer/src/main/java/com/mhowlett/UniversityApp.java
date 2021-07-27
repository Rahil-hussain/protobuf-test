package com.mhowlett;

import com.mhowlett.LogLineOuterClass.LogLine;
import com.redshelf.grpc.product.ProductOuterClass;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class UniversityApp
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

        String topic = "Monday";

        Producer<String, ProductOuterClass.Product> producer = new KafkaProducer<>(props);

        Random rnd = new Random();

        for (long i = 0; i<nEvents; ++i) {
            ProductOuterClass.Product event = ProductEventGenerator.getNext(rnd);
            ProducerRecord<String, ProductOuterClass.Product> record = new ProducerRecord<String, ProductOuterClass.Product>(topic, event.getId(), event);
            producer.send(record).get();
        }

        producer.close();

        System.out.println("Done!");
    }
}
