package com.xzg.orchestrator.kit.participant.result;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.exception
 * @className: CustomerNotFound
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 3:24
 * @version: 1.0
 */
public class CustomerNotFound extends AbstractReplayResult {
    @Override
    public boolean localTransaction() {
        return false;
    }
}


    