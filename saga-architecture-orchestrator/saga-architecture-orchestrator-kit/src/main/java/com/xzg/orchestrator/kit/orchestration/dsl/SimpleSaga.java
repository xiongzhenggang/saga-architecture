package com.xzg.orchestrator.kit.orchestration.dsl;


import com.xzg.orchestrator.kit.orchestration.saga.Saga;


/**
 *
 * @param <Data>
 */
public interface SimpleSaga<Data> extends Saga<Data>, SimpleSagaDsl<Data> {
}
