package com.xzg.orchestrator.kit.orchestration.dsl;

/**
 * @author xiongzhenggang
 */
public interface SimpleSagaDsl<Data> {

  /**
   *定义发执行步骤
   * @return
   */
  default StepBuilder<Data> step() {
    SimpleSagaDefinitionBuilder<Data> builder = new SimpleSagaDefinitionBuilder<>();
    return new StepBuilder<>(builder);
  }

}
