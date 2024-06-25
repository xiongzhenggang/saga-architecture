package com.xzg.orchestrator.kit.orchestration.saga.enums;


public enum SourceEnum {

    SEND("SEND"),
    RECEIVE("RECEIVE");
    private String value;
    SourceEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
