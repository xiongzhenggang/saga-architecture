package com.xzg.orchestrator.kit.orchestration.saga;

import com.xzg.orchestrator.kit.orchestration.saga.model.SagaInstance;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author xiongzhenggang
 */
public class SagaInstanceFactory {

  private ConcurrentMap<Saga<?>, SagaManager<?>> sagaManagers = new ConcurrentHashMap<>();

  public SagaInstanceFactory(SagaManagerFactory sagaManagerFactory, Collection<Saga<?>> sagas) {
    for (Saga<?> saga : sagas) {
      sagaManagers.put(saga, makeSagaManager(sagaManagerFactory, saga));
    }
  }

  public <SagaData> SagaInstance create(Saga<SagaData> saga, SagaData data) {
    SagaManager<SagaData>  sagaManager = (SagaManager<SagaData>)sagaManagers.get(saga);
    if (sagaManager == null) {
      throw new RuntimeException(("No SagaManager for " + saga));
    }
    return sagaManager.create(data);
  }

  private <SagaData> SagaManager<SagaData> makeSagaManager(SagaManagerFactory sagaManagerFactory, Saga<SagaData> saga) {
    SagaManagerImpl<SagaData> sagaManager = sagaManagerFactory.make(saga);
    sagaManager.subscribeToReplyChannel();
    return sagaManager;
  }
}
