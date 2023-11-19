package com.xzg.orchestrator.kit.command;

import java.util.Map;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.command
 * @className: PathVariables
 * @author: xzg
 * @description: TODO
 * @date: 18/11/2023-下午 6:53
 * @version: 1.0
 */
public class PathVariables {

    private Map<String, String> pathVars;

    public PathVariables(Map<String, String> pathVars) {

        this.pathVars = pathVars;
    }

    public String getString(String name) {
        return pathVars.get(name);
    }

    public long getLong(String name) {
        return Long.parseLong(getString(name));
    }

}