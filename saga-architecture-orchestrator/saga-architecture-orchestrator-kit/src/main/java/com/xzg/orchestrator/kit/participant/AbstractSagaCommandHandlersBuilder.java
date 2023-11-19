package com.xzg.orchestrator.kit.participant;


import com.xzg.orchestrator.kit.command.Command;
import com.xzg.orchestrator.kit.event.CommandMessage;
import com.xzg.orchestrator.kit.event.Message;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface AbstractSagaCommandHandlersBuilder {
  <C extends Command> SagaCommandHandlerBuilder<C> onMessageReturningMessages(Class<C> commandClass,
                                                                              Function<CommandMessage<C>, List<Message>> handler);

  <C extends Command> SagaCommandHandlerBuilder<C> onMessageReturningOptionalMessage(Class<C> commandClass,
                                                                     Function<CommandMessage<C>, Optional<Message>> handler);

  <C extends Command> SagaCommandHandlerBuilder<C> onMessage(Class<C> commandClass,
                                             Function<CommandMessage<C>, Message> handler);

  <C extends Command> SagaCommandHandlerBuilder<C> onMessage(Class<C> commandClass, Consumer<CommandMessage<C>> handler);
}
