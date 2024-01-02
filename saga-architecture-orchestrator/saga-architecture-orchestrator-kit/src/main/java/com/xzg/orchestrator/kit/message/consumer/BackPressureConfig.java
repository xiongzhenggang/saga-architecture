package com.xzg.orchestrator.kit.message.consumer;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: BackPressureConfig
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:13
 * @version: 1.0
 */
public class BackPressureConfig {

    private int low = 0;
    private int high = Integer.MAX_VALUE;

    public BackPressureConfig() {
    }

    public BackPressureConfig(int low, int high) {
        this.low = low;
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }
}

    