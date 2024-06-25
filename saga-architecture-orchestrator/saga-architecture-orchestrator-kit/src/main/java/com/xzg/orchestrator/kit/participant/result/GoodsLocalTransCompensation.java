package com.xzg.orchestrator.kit.participant.result;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.participant.result
 * @className: LocalTransCompensation
 * @author: xzg
 * @description: TODO
 * @date: 25/6/2024-下午 10:44
 * @version: 1.0
 */
public class GoodsLocalTransCompensation extends AbstractReplayResult{
    @Override
    boolean localTransaction() {
        return true;
    }
}


    