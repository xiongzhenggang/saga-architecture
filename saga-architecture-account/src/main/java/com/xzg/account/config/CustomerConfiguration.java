package com.xzg.account.config;

import com.xzg.account.domain.CustomerDao;
import com.xzg.account.service.CustomerCommandHandler;
import com.xzg.account.service.CustomerService;
import com.xzg.orchestrator.kit.command.CommandDispatcher;
import com.xzg.orchestrator.kit.participant.SagaCommandDispatcherFactory;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("com.xzg.account")
@EntityScan(basePackages = "com.xzg.account")
@ComponentScan("com.xzg")
//@Import(OptimisticLockingDecoratorConfiguration.class)
public class CustomerConfiguration {
  @Resource
  private SagaCommandDispatcherFactory sagaCommandDispatcherFactory;
  @Bean
  public CustomerService customerService(CustomerDao customerDao) {
    return new CustomerService(customerDao);
  }

  @Bean
  public CustomerCommandHandler customerCommandHandler(CustomerDao customerDao) {
    return new CustomerCommandHandler(customerDao);
  }

  // TODO Exception handler for CustomerCreditLimitExceededException

  @Bean
  public CommandDispatcher consumerCommandDispatcher(CustomerCommandHandler target) {
    return sagaCommandDispatcherFactory.make("customerCommandDispatcher", target.commandHandlerDefinitions());
  }

}
