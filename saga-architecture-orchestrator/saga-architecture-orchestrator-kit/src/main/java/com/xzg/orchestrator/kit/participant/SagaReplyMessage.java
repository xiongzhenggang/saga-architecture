package com.xzg.orchestrator.kit.participant;


import com.xzg.orchestrator.kit.common.LockTarget;
import com.xzg.orchestrator.kit.event.MessageImpl;

import java.util.Map;
import java.util.Optional;

public class SagaReplyMessage extends MessageImpl {
  private Optional<LockTarget> lockTarget;

  public SagaReplyMessage(String body, Map<String, String> headers, Optional<LockTarget> lockTarget) {
    super(body, headers);
    this.lockTarget = lockTarget;
  }

  public Optional<LockTarget> getLockTarget() {
    return lockTarget;
  }

  public boolean hasLockTarget() {
    return lockTarget.isPresent();
  }
}
