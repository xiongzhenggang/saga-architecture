package com.xzg.orchestrator.kit.message.consumer;

import java.nio.charset.StandardCharsets;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.event.consumer
 * @className: EventuateBinaryMessageEncoding
 * @author: xzg
 * @description: TODO
 * @date: 22/11/2023-下午 10:28
 * @version: 1.0
 */
public class StringBinaryMessageEncoding {
    public static String bytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] stringToBytes(String string) {
        return string.getBytes(StandardCharsets.UTF_8);
    }
}


    