package com.xzg.orchestrator.kit.command;

import lombok.ToString;

import java.util.Collections;
import java.util.Map;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.command
 * @className: CommandWithDestination
 * @author: xzg
 * @description: TODO
 * @date: 18/11/2023-下午 5:51
 * @version: 1.0
 */
@ToString
public class CommandWithDestination {
    private final String destinationChannel;
    private final String resource;
    private final Command command;
    private final Map<String, String> extraHeaders;

    public CommandWithDestination(String destinationChannel, String resource, Command command, Map<String, String> extraHeaders) {
        this.destinationChannel = destinationChannel;
        this.resource = resource;
        this.command = command;
        this.extraHeaders = extraHeaders;
    }

    public CommandWithDestination(String destinationChannel, String resource, Command command) {
        this(destinationChannel, resource, command, Collections.emptyMap());
    }


    public String getDestinationChannel() {
        return destinationChannel;
    }

    public String getResource() {
        return resource;
    }

    public Command getCommand() {
        return command;
    }

    public Map<String, String> getExtraHeaders() {
        return extraHeaders;
    }
}

    