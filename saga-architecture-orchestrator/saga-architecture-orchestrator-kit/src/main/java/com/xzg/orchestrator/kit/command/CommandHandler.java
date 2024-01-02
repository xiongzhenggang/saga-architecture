package com.xzg.orchestrator.kit.command;

import com.xzg.orchestrator.kit.message.Message;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.command
 * @className: CommandHandler
 * @author: xzg
 * @description: TODO
 * @date: 18/11/2023-下午 5:54
 * @version: 1.0
 */
public class CommandHandler extends AbstractCommandHandler<List<Message>>{

    public <C extends Command> CommandHandler(String channel, Optional<String> resource,
                                              Class<C> commandClass,
                                              Function<CommandHandlerArgs<C>, List<Message>> handler) {

        super(channel, resource, commandClass, handler);
    }
}
    