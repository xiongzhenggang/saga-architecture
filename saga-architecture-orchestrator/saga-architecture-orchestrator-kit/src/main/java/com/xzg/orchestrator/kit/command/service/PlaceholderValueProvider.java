package com.xzg.orchestrator.kit.command.service;

import java.util.Map;
import java.util.Optional;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.command
 * @className: PlaceholderValueProvider
 * @author: xzg
 * @description: TODO
 * @date: 18/11/2023-下午 6:55
 * @version: 1.0
 */
public interface PlaceholderValueProvider {
    Optional<String> get(String name);
    Map<String, String> getParams();
}


    