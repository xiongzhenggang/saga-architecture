package com.xzg.orchestrator.kit.orchestration.config;

import com.xzg.orchestrator.kit.command.CommandNameMapping;
import com.xzg.orchestrator.kit.command.CommandProducerImpl;
import com.xzg.orchestrator.kit.command.service.CommandProducer;
import com.xzg.orchestrator.kit.event.consumer.CommonMessageConsumer;
import com.xzg.orchestrator.kit.event.consumer.kafka.MessageConsumerKafkaImpl;
import com.xzg.orchestrator.kit.event.producer.MessageProducer;
import com.xzg.orchestrator.kit.kafka.config.KafkaProduceConsumerConfig;
import com.xzg.orchestrator.kit.orchestration.saga.*;
import com.xzg.orchestrator.kit.orchestration.saga.dao.SagaInstanceRepository;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.ConsumerFactory;

import java.util.Collection;

/**
 * @author xiongzhenggang
 */
@Configuration
@Import({KafkaProduceConsumerConfig.class})
public class SagaOrchestratorConfiguration {

  @Resource
  private  MessageProducer messageProducer;
  @Resource
  private CommandNameMapping commandNameMapping;
  @Resource
  private ConsumerFactory<String, byte[]> consumerFactory;
  @Bean
  public CommandProducer commandProducer() {
    return new CommandProducerImpl(messageProducer,commandNameMapping);
  }

  @Bean
  public SagaCommandProducer sagaCommandProducer(CommandProducer commandProducer) {
    return new SagaCommandProducerImpl(commandProducer);
  }
  @Bean CommonMessageConsumer messageConsumerKafka(){
    return new MessageConsumerKafkaImpl(consumerFactory);
  }


}
