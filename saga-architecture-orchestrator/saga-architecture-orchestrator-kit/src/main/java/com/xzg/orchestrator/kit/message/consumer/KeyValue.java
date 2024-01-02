package com.xzg.orchestrator.kit.message.consumer;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Collection;
import java.util.Objects;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: KeyValue
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:29
 * @version: 1.0
 */
public class KeyValue {
    public static final int ESTIMATED_BYTES_PER_CHAR = 3;
    public static final int KEY_HEADER_SIZE = 4;
    public static final int VALUE_HEADER_SIZE = 4;

    private String key;
    private String value;

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public int estimateSize() {
        int keyLength = estimatedStringSizeInBytes(key);
        int valueLength = estimatedStringSizeInBytes(value);
        return KEY_HEADER_SIZE + keyLength + VALUE_HEADER_SIZE + valueLength;
    }

    public static int  estimateSize(Collection<? extends KeyValue> kvs) {
        return kvs.stream().mapToInt(KeyValue::estimateSize).sum();
    }

    private int estimatedStringSizeInBytes(String s) {
        return s == null ? 0 : s.length() * ESTIMATED_BYTES_PER_CHAR;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
    