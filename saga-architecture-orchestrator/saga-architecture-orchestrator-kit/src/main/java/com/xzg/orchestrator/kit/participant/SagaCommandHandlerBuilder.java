package com.xzg.orchestrator.kit.participant;


import com.xzg.orchestrator.kit.command.Command;
import com.xzg.orchestrator.kit.command.CommandHandlers;
import com.xzg.orchestrator.kit.message.CommandMessage;
import com.xzg.orchestrator.kit.message.Message;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author xiongzhenggang
 */
public class SagaCommandHandlerBuilder<C extends Command> implements AbstractSagaCommandHandlersBuilder {
  private final SagaCommandHandlersBuilder parent;
  private final SagaCommandHandler h;

  public SagaCommandHandlerBuilder(SagaCommandHandlersBuilder parent, SagaCommandHandler h) {
    super();
    this.parent = parent;
    this.h = h;
  }

  @Override
  public <C extends Command> SagaCommandHandlerBuilder<C> onMessageReturningMessages(Class<C> commandClass, Function<CommandMessage<C>, List<Message>> handler) {
    return parent.onMessageReturningMessages(commandClass, handler);
  }

  @Override
  public <C extends Command> SagaCommandHandlerBuilder<C> onMessageReturningOptionalMessage(Class<C> commandClass, Function<CommandMessage<C>, Optional<Message>> handler) {
    return parent.onMessageReturningOptionalMessage(commandClass, handler);
  }

  @Override
  public <C extends Command> SagaCommandHandlerBuilder<C> onMessage(Class<C> commandClass, Function<CommandMessage<C>, Message> handler) {
    return parent.onMessage(commandClass, handler);
  }

  @Override
  public <C extends Command> SagaCommandHandlerBuilder<C> onMessage(Class<C> commandClass, Consumer<CommandMessage<C>> handler) {
    return parent.onMessage(commandClass, handler);
  }


  public CommandHandlers build() {
    return parent.build();
  }

}
