package com.xzg.orchestrator.kit.orchestration.dsl;


import com.xzg.orchestrator.kit.command.Command;
import lombok.Data;

import java.util.Set;

@Data
public class CommandEndpoint<C extends Command> {

  private String commandChannel;
  private Class<C> commandClass;
  private Set<Class> replyClasses;

  public CommandEndpoint(String commandChannel, Class<C> commandClass, Set<Class> replyClasses) {
    this.commandChannel = commandChannel;
    this.commandClass = commandClass;
    this.replyClasses = replyClasses;
  }

}
