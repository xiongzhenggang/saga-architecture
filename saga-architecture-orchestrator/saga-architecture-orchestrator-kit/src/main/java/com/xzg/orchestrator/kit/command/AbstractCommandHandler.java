package com.xzg.orchestrator.kit.command;

import com.xzg.orchestrator.kit.event.Message;

import java.util.Optional;
import java.util.function.Function;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.command
 * @className: AbstractCommandHandler
 * @author: xzg
 * @description: TODO
 * @date: 18/11/2023-下午 6:50
 * @version: 1.0
 */
public class AbstractCommandHandler<RESULT> {

    private final String channel;
    private final Optional<String> resource;
    private final Class commandClass;
    private final Function<CommandHandlerArgs<Command>, RESULT> handler;

    /**
     * handler 参数是command相关
     * @param channel
     * @param resource
     * @param commandClass
     * @param handler
     * @param <C>
     */
    public <C extends Command> AbstractCommandHandler(String channel, Optional<String> resource,
                                                      Class<C> commandClass,
                                                      Function<CommandHandlerArgs<C>, RESULT> handler) {
        this.channel = channel;
        this.resource = resource;
        this.commandClass = commandClass;
        // Function<CommandHandlerArgs<Command>, RESULT> handler
        this.handler =(CommandHandlerArgs<Command> args) -> handler.apply((CommandHandlerArgs<C>)args);
    }

    public String getChannel() {
        return channel;
    }

    public boolean handles(Message message) {
        return commandTypeMatches(message) && resourceMatches(message);
    }

    private boolean resourceMatches(Message message) {
        return !resource.isPresent() || message.getHeader(CommandMessageHeaders.RESOURCE).map(m -> resourceMatches(m, resource.get())).orElse(false);
    }

    private boolean commandTypeMatches(Message message) {
        return commandClass.getName().equals(
                message.getRequiredHeader(CommandMessageHeaders.COMMAND_TYPE));
    }

    private boolean resourceMatches(String messageResource, String methodPath) {
        ResourcePathPattern r = ResourcePathPattern.parse(methodPath);
        ResourcePath mr = ResourcePath.parse(messageResource);
        return r.isSatisfiedBy(mr);
    }

    public Class getCommandClass() {
        return commandClass;
    }

    public Optional<String> getResource() {
        return resource;
    }

    public RESULT invokeMethod(CommandHandlerArgs commandHandlerArgs) {
        return (RESULT) handler.apply(commandHandlerArgs);
    }
}

    