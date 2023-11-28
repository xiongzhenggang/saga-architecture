package com.xzg.orchestrator.kit.orchestration.dsl;

import com.xzg.library.config.infrastructure.utility.JsonUtil;

public class SagaExecutionStateJsonSerde {
  public static SagaExecutionState decodeState(String currentState) {
    return JsonUtil.jsonStr2obj(currentState, SagaExecutionState.class);
  }

  public static String encodeState(SagaExecutionState state) {
    return  JsonUtil.object2JsonStr(state);
  }
}
