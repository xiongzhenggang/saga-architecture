package com.xzg.orchestrator.kit.command;

import java.util.Collections;
import java.util.Map;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.command
 * @className: CommandWithDestinationBuilder
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 3:42
 * @version: 1.0
 */
public class CommandWithDestinationBuilder {
    private Command command;
    private String destinationChannel;
    private String resource;
    private Map<String, String> extraHeaders = Collections.emptyMap();

    public CommandWithDestinationBuilder(Command command) {
        this.command = command;
    }

    public static CommandWithDestinationBuilder send(Command command) {
        return new CommandWithDestinationBuilder(command);
    }

    public CommandWithDestinationBuilder to(String destinationChannel) {
        this.destinationChannel = destinationChannel;
        return this;
    }

    public CommandWithDestinationBuilder forResource(String resource, Object... pathParams) {
        this.resource = new ResourcePathPattern(resource).replacePlaceholders(pathParams).toPath();
        return this;
    }

    public CommandWithDestinationBuilder withExtraHeaders(Map<String, String> headers) {
        this.extraHeaders = headers;
        return this;

    }
    public CommandWithDestination build() {
        return new CommandWithDestination(destinationChannel, resource, command, extraHeaders);
    }
}


    