package com.xzg.orchestrator.kit.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;


/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.kafka.config
 * @className: KafkaConsumerConfig
 * @author: xzg
 * @description: TODO
 * @date: 20/11/2023-下午 10:17
 * @version: 1.0
 */
@Configuration
@EnableKafka
public class KafkaProduceConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id:default}")
    private String groupId;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        //在偏移量无效的情况下，消费者将从起始位置读取分区的记录
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, byte[]> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }


    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        //生产者幂等性：通过设置enable.idempotence参数为true，Kafka生产者可以保证消息的幂等性，即消息在发送过程中不会因为网络或其他问题而重复发送
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,"true");
        //发生错误后，消息重发的次数 ，0为不启用重试机制，默认int最大值
        props.put(ProducerConfig.RETRIES_CONFIG,"3");
        //生产者发送过来的数据，Leader 收到数据后应答。
        props.put(ProducerConfig.ACKS_CONFIG,"all");
//        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,1);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, byte[]> producerFactory() {
        DefaultKafkaProducerFactory<String, byte[]> producerFactory =  new DefaultKafkaProducerFactory<>(producerConfigs());
        //设置开启生产者事务
        producerFactory.setTransactionIdPrefix("tx-");
        return producerFactory;
    }

    @Bean
    public KafkaTemplate<String, byte[]> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
