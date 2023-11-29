package com.xzg.orchestrator.kit;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hello world!
 *
 */
@Configuration
@ComponentScan("com.xzg.orchestrator.kit")
@EnableJpaRepositories("com.xzg.orchestrator.kit.orchestration.saga.dao")
@EntityScan(basePackages = "com.xzg.orchestrator.kit.orchestration.saga.dao")
public class SagaKitConfig {
}
