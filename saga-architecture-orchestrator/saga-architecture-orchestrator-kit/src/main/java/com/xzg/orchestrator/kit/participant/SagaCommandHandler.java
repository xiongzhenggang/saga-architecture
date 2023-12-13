package com.xzg.orchestrator.kit.participant;


import com.xzg.orchestrator.kit.common.LockTarget;
import com.xzg.orchestrator.kit.command.Command;
import com.xzg.orchestrator.kit.command.CommandHandler;
import com.xzg.orchestrator.kit.command.CommandHandlerArgs;
import com.xzg.orchestrator.kit.command.PathVariables;
import com.xzg.orchestrator.kit.event.CommandMessage;
import com.xzg.orchestrator.kit.event.Message;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SagaCommandHandler extends CommandHandler {

  private Optional<BiFunction<CommandMessage, PathVariables, LockTarget>> preLock = Optional.empty();
  private Optional<PostLockFunction> postLock = Optional.empty();

  public <C extends Command> SagaCommandHandler(String channel, Class<C> commandClass, Function<CommandHandlerArgs<C>, List<Message>> handler) {
    super(channel, Optional.empty(), commandClass, handler);
  }

  public void setPreLock(BiFunction<CommandMessage, PathVariables, LockTarget> preLock) {
    this.preLock = Optional.of(preLock);
  }

  public void setPostLock(PostLockFunction postLock) {
    this.postLock = Optional.of(postLock);
  }

  public Optional<BiFunction<CommandMessage, PathVariables, LockTarget>> getPreLock() {
    return preLock;
  }

  public Optional<PostLockFunction> getPostLock() {
    return postLock;
  }
}
