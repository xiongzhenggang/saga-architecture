package com.xzg.orchestrator.kit.common;


import com.xzg.orchestrator.kit.command.CommandMessageHeaders;

public class SagaCommandHeaders {
  public static final String SAGA_TYPE = CommandMessageHeaders.COMMAND_HEADER_PREFIX + "saga_type";
  public static final String SAGA_ID = CommandMessageHeaders.COMMAND_HEADER_PREFIX + "saga_id";

}
