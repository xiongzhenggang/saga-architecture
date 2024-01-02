package com.xzg.orchestrator.kit.participant;


import com.xzg.orchestrator.kit.command.Command;
import com.xzg.orchestrator.kit.command.CommandHandler;
import com.xzg.orchestrator.kit.command.CommandHandlers;
import com.xzg.orchestrator.kit.command.CommandReplyToken;
import com.xzg.orchestrator.kit.message.CommandMessage;
import com.xzg.orchestrator.kit.message.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class SagaCommandHandlersBuilder implements AbstractSagaCommandHandlersBuilder {
  private String channel;

  private final List<CommandHandler> handlers = new ArrayList<>();

  public static SagaCommandHandlersBuilder fromChannel(String channel) {
    return new SagaCommandHandlersBuilder().andFromChannel(channel);
  }

  private SagaCommandHandlersBuilder andFromChannel(String channel) {
    this.channel = channel;
    return this;
  }

  @Override
  public <C extends Command> SagaCommandHandlerBuilder<C> onMessageReturningMessages(Class<C> commandClass,
                                                                                     Function<CommandMessage<C>, List<Message>> handler) {
    SagaCommandHandler h = new SagaCommandHandler(channel, commandClass, args -> handler.apply(args.getCommandMessage()));
    this.handlers.add(h);
    return new SagaCommandHandlerBuilder<>(this, h);
  }

  @Override
  public <C extends Command> SagaCommandHandlerBuilder<C> onMessageReturningOptionalMessage(Class<C> commandClass,
                                                                            Function<CommandMessage<C>, Optional<Message>> handler) {
    SagaCommandHandler h = new SagaCommandHandler(channel, commandClass, args -> handler.apply(args.getCommandMessage()).map(Collections::singletonList).orElse(Collections.emptyList()));
    this.handlers.add(h);
    return new SagaCommandHandlerBuilder<>(this, h);
  }

  @Override
  public <C extends Command> SagaCommandHandlerBuilder<C> onMessage(Class<C> commandClass,
                                                    Function<CommandMessage<C>, Message> handler) {
    SagaCommandHandler h = new SagaCommandHandler(channel, commandClass,
            args -> Collections.singletonList(handler.apply(args.getCommandMessage())));
    this.handlers.add(h);
    return new SagaCommandHandlerBuilder<>(this, h);
  }

  @Override
  public <C extends Command> SagaCommandHandlerBuilder<C> onMessage(Class<C> commandClass, Consumer<CommandMessage<C>> handler) {
    SagaCommandHandler h = new SagaCommandHandler(channel, commandClass,
            args -> {
              handler.accept(args.getCommandMessage());
              return Collections.emptyList();
            });
    this.handlers.add(h);
    return new SagaCommandHandlerBuilder<>(this, h);
  }

  public <C extends Command> SagaCommandHandlerBuilder<C> onMessage(Class<C> commandClass, BiConsumer<CommandMessage<C>, CommandReplyToken> handler) {
    SagaCommandHandler h = new SagaCommandHandler(channel, commandClass,
            args -> {
              handler.accept(args.getCommandMessage(), args.getCommandReplyToken());
              return Collections.emptyList();
            });
    this.handlers.add(h);
    return new SagaCommandHandlerBuilder<>(this, h);
  }

  public CommandHandlers build() {
    return new CommandHandlers(handlers);
  }

}
