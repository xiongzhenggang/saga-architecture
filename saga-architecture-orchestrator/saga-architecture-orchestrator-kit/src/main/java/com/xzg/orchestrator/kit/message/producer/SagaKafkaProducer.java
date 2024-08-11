package com.xzg.orchestrator.kit.message.producer;

import com.xzg.library.config.infrastructure.utility.JsonUtil;
import com.xzg.orchestrator.kit.common.SagaCommandHeaders;
import com.xzg.orchestrator.kit.message.Message;
import com.xzg.orchestrator.kit.message.consumer.StringBinaryMessageEncoding;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
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
  private KafkaTemplate<String,byte[]> kafkaTemplate;
  /**
   * Saga发送kafka或者应用内部
   * @param destination the destination channel
   * @param message the message to doSend
   */
  @Override
  public void send(String destination, Message message) {
//     send(destination, message.getHeader(SagaCommandHeaders.SAGA_TYPE).orElse(""), JsonUtil.object2JsonStr(message));
    sendTransaction(destination,message);
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
  }

  /**
   *  事务消息发送
   * @param destination
   * @param message
   */
  public   void sendTransaction(String destination, Message message){
    String jsonObject  = JsonUtil.object2JsonStr(message);
    if(StringUtils.isEmpty(jsonObject)){
      throw new RuntimeException("message is null");
    }
    byte[] bytes = StringBinaryMessageEncoding.stringToBytes(jsonObject);
    ProducerRecord<String, byte[]> producerRecord = new ProducerRecord<>(destination, message.getHeader(SagaCommandHeaders.SAGA_TYPE).orElse(""), bytes);
    sendTransaction(producerRecord);
  }
  /**
   * 事务消息发送
   * 保证consumer处理和发送在同一个事务
   */
  private  void sendTransaction(ProducerRecord<String, byte[]> producerRecord) {
      kafkaTemplate.executeInTransaction(kafkaOperations -> {
      kafkaOperations.send(producerRecord);
      //其他处理
      return true;
    });
  }

}
