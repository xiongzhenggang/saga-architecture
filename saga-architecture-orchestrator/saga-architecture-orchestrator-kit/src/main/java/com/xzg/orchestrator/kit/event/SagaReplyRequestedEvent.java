package com.xzg.orchestrator.kit.event;


import lombok.Getter;

import java.util.Map;

@Getter
public class SagaReplyRequestedEvent implements Event {
  private Map<String, String> correlationHeaders;

  private SagaReplyRequestedEvent() {
  }

  public SagaReplyRequestedEvent(Map<String, String> correlationHeaders) {
    this.correlationHeaders = correlationHeaders;
  }

  public void setCorrelationHeaders(Map<String, String> correlationHeaders) {
    this.correlationHeaders = correlationHeaders;
  }


}
