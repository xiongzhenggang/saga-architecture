package com.xzg.orchestrator.kit.common;


import com.xzg.orchestrator.kit.event.Message;

import java.util.Optional;

public interface SagaLockManager {

  /**
   *   "INSERT INTO %s(target, saga_type, saga_id) VALUES(?, ?,?)", sagaLockTable);
   * @param sagaType
   * @param sagaId
   * @param target
   * @return
   */
  boolean claimLock(String sagaType, String sagaId, String target);

  /**
   *     "select saga_id from %s WHERE target = ? FOR UPDATE", sagaLockTable);
   * @param sagaType
   * @param sagaId
   * @param target
   * @param message
   */
  void stashMessage(String sagaType, String sagaId, String target, Message message);

  /**
   *     Optional<String> owningSagaId = selectForUpdate(target);
   *     if (!owningSagaId.isPresent()) {
   *       throw new RuntimeException("owningSagaId is not present");
   *     }
   *     if (!owningSagaId.get().equals(sagaId)) {
   *       throw new RuntimeException(String.format("Expected owner to be %s but is %s", sagaId, owningSagaId.get()));
   *     }
   * @param sagaId
   * @param target
   * @return
   */
  Optional<Message> unlock(String sagaId, String target);
}
