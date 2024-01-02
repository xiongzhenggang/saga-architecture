package com.xzg.orchestrator.kit.command;

import com.xzg.orchestrator.kit.message.Message;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.command
 * @className: CommandHandlers
 * @author: xzg
 * @description: TODO
 * @date: 18/11/2023-下午 5:52
 * @version: 1.0
 */
public class CommandHandlers {
    private List<CommandHandler> handlers;

    public CommandHandlers(List<CommandHandler> handlers) {
        this.handlers = handlers;
    }

    public Set<String> getChannels() {
        return handlers.stream().map(CommandHandler::getChannel).collect(toSet());
    }

    public Optional<CommandHandler> findTargetMethod(Message message) {
        return handlers.stream().filter(h -> h.handles(message)).findFirst();
    }

    public Optional<CommandExceptionHandler> findExceptionHandler(CommandHandler commandHandler, Throwable cause) {
        throw new UnsupportedOperationException(String.format("A command handler for command of type %s on channel %s threw an exception",
                commandHandler.getCommandClass().getName(),
                commandHandler.getChannel()),
                cause);
    }

    public List<CommandHandler> getHandlers() {
        return handlers;
    }
}
    