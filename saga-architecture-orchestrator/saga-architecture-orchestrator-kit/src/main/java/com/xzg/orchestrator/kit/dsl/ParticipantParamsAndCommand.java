package com.xzg.orchestrator.kit.dsl;


import com.xzg.orchestrator.kit.command.Command;

import java.util.Map;

public class ParticipantParamsAndCommand<C extends Command> {
  private final Map<String, String> params;
  private final C command;

  public ParticipantParamsAndCommand(Map<String, String> params, C command) {
    this.params = params;
    this.command = command;
  }

  public Map<String, String> getParams() {
    return params;
  }

  public C getCommand() {
    return command;
  }
}
