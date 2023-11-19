package com.xzg.orchestrator.kit.event;

import java.util.Map;
import java.util.Optional;

public interface Message {

    String getId();
    Map<String, String> getHeaders();
    String getPayload();

    String ID = "ID";
    String PARTITION_ID = "PARTITION_ID";
    String DESTINATION = "DESTINATION";
    String DATE = "DATE";

    Optional<String> getHeader(String name);
    String getRequiredHeader(String name);

    boolean hasHeader(String name);

    void setPayload(String payload);
    void setHeaders(Map<String, String> headers);
    void setHeader(String name, String value);
    void removeHeader(String key);
}
