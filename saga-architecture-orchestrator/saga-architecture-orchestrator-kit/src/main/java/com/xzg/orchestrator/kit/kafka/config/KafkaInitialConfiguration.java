package com.xzg.orchestrator.kit.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.kafka.config
 * @className: KafkaInitialConfiguration
 * @author: xzg
 * @description: TODO
 * @date: 20/11/2023-下午 10:57
 * @version: 1.0
 */
@Configuration
public class KafkaInitialConfiguration {
    @Value("${application.saga.topic.name}")
    private String orderSagaTopic;
    // 创建一个名为testtopic的Topic并设置分区数为3，分区副本数为2
    @Bean
    public NewTopic initialTopic() {
        return new NewTopic(orderSagaTopic,3, (short) 2 );
    }
}


    