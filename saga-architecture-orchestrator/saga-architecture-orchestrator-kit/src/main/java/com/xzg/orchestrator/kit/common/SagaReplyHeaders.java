package com.xzg.orchestrator.kit.common;


import com.xzg.orchestrator.kit.command.CommandMessageHeaders;

public class SagaReplyHeaders {

  public static final String REPLY_SAGA_TYPE = CommandMessageHeaders.inReply(SagaCommandHeaders.SAGA_TYPE);
  public static final String REPLY_SAGA_ID = CommandMessageHeaders.inReply(SagaCommandHeaders.SAGA_ID);

  public static final String REPLY_LOCKED = "saga-locked-target";
}
