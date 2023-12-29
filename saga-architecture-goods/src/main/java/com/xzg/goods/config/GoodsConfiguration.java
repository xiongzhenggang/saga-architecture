package com.xzg.goods.config;

import com.xzg.goods.dao.GoodsDao;
import com.xzg.goods.service.GoodsCommandHandler;
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
@EnableJpaRepositories("com.xzg.goods")
@EntityScan(basePackages = "com.xzg.goods")
@ComponentScan("com.xzg")
public class GoodsConfiguration {
  @Resource
  private SagaCommandDispatcherFactory sagaCommandDispatcherFactory;

  @Bean
  public GoodsCommandHandler goodsCommandHandler(GoodsDao goodsDao) {
    return new GoodsCommandHandler(goodsDao);
  }


  @Bean
  public CommandDispatcher consumerCommandDispatcher(GoodsCommandHandler target) {
    return sagaCommandDispatcherFactory.make("goodsCommandDispatcher", target.commandHandlerDefinitions());
  }

}
