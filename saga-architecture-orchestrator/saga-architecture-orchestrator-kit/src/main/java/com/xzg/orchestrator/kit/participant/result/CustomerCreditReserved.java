package com.xzg.orchestrator.kit.participant.result;

/**
 * @author xiongzhenggang
 */
public class CustomerCreditReserved extends AbstractReplayResult {
    @Override
    public boolean localTransaction() {
        return true;
    }
}
