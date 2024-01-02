package com.xzg.orchestrator.kit.message.producer;

import com.xzg.library.config.infrastructure.utility.JsonUtil;
import com.xzg.orchestrator.kit.common.SagaCommandHeaders;
import com.xzg.orchestrator.kit.message.Message;
import com.xzg.orchestrator.kit.message.consumer.StringBinaryMessageEncoding;
import jakarta.annotation.Resource;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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
     send(destination, message.getHeader(SagaCommandHeaders.SAGA_TYPE).orElse(""), JsonUtil.object2JsonStr(message));
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

  /**
   * 事务消息发送
   * 保证consumer处理和发送在同一个事务
   */
  private  void sendTransaction(String destination, Consumer<Message> consumer,Message message) {
    ProducerRecord producerRecord =new ProducerRecord<>(destination,
            message.getHeader(SagaCommandHeaders.SAGA_TYPE).orElse(""),
            StringBinaryMessageEncoding.stringToBytes( JsonUtil.object2JsonStr(message)));
    kafkaTemplate.executeInTransaction(kafkaOperations -> {
      kafkaOperations.send(producerRecord);
      //其他处理
      consumer.accept(message);
      return true;
    });
  }

}
