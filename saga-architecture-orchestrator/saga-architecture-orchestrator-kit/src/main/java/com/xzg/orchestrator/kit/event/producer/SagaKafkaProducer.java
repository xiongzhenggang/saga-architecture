package com.xzg.orchestrator.kit.event.producer;

import com.xzg.orchestrator.kit.common.SagaCommandHeaders;
import com.xzg.orchestrator.kit.event.Message;
import com.xzg.orchestrator.kit.event.consumer.StringBinaryMessageEncoding;
import jakarta.annotation.Resource;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * kafka发送
 * @author xiongzhenggang
 */
@Service
public class SagaKafkaProducer implements MessageProducer {

  @Resource
  private KafkaTemplate kafkaTemplate;
  /**
   * Saga发送kafka或者应用内部
   * @param destination the destination channel
   * @param message the message to doSend
   */
  @Override
  public void send(String destination, Message message) {
     send(destination, message.getHeader(SagaCommandHeaders.SAGA_TYPE).orElse(""),message.getPayload());
  }
  public CompletableFuture<?> send(String topic, String key, String body) {
    return send(topic, key, StringBinaryMessageEncoding.stringToBytes(body));
  }

  public CompletableFuture<?> send(String topic, int partition, String key, String body) {
    return send(topic, partition, key, StringBinaryMessageEncoding.stringToBytes(body));
  }

  public CompletableFuture<?> send(String topic, String key, byte[] bytes) {
    return send(new ProducerRecord<>(topic, key, bytes));
  }

  public CompletableFuture<?> send(String topic, int partition, String key, byte[] bytes) {
    return send(new ProducerRecord<>(topic, partition, key, bytes));
  }

  private CompletableFuture<?> send(ProducerRecord<String, byte[]> producerRecord) {
    return kafkaTemplate.send(producerRecord);
//    CompletableFuture<Object> result = new CompletableFuture<>();
//    kafkaTemplate.send(producerRecord, (metadata, exception) -> {
//      if (exception == null)
//        result.complete(metadata);
//      else
//        result.completeExceptionally(exception);
//    });
  }

//  public int partitionFor(String topic, String key) {
//    return eventuateKafkaPartitioner.partition(topic, stringSerializer.serialize(topic, key), partitionsFor(topic));
//  }

//  public List<PartitionInfo> partitionsFor(String topic) {
//    return producer.partitionsFor(topic);
//  }

//  public void close() {
//    producer.close(Duration.ofSeconds(1));
//  }


}
