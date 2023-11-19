package com.xzg.orchestrator.kit.dsl;


import com.xzg.orchestrator.kit.command.Command;

import java.util.Map;

import static java.util.Collections.singletonMap;

public class ParticipantInvocationBuilder {
  private Map<String, String> params;


  public ParticipantInvocationBuilder(String key, String value) {
    this.params = singletonMap(key, value);
  }

  public <C extends Command>  ParticipantParamsAndCommand<C> withCommand(C command) {
    return new ParticipantParamsAndCommand<>(params, command);
  }
}
