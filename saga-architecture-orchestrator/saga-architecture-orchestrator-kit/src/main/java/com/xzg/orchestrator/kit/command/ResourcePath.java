package com.xzg.orchestrator.kit.command;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.command
 * @className: ResourcePath
 * @author: xzg
 * @description: TODO
 * @date: 18/11/2023-下午 6:52
 * @version: 1.0
 */
public class ResourcePath {

    final String[] splits;

    public ResourcePath(String[] splits) {
        this.splits = splits;
    }

    public ResourcePath(String resource) {
        if (!resource.startsWith("/")) {
            throw new IllegalArgumentException("Should start with / " + resource );
        }

        this.splits = splitPath(resource);
    }

    private String[] splitPath(String path) {
        return path.split("/");
    }

    public static ResourcePath parse(String resource) {
        return new ResourcePath(resource);
    }

    public int length() {
        return splits.length;
    }

    public String toPath() {
        return Arrays.stream(splits).collect(joining("/"));
    }
}

    