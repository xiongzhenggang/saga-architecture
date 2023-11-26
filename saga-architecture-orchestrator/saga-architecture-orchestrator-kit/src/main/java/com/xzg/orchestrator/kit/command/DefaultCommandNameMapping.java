package com.xzg.orchestrator.kit.command;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.command
 * @className: DefaultCommandNameMapping
 * @author: xzg
 * @description: TODO
 * @date: 20/11/2023-下午 8:20
 * @version: 1.0
 */
public class DefaultCommandNameMapping implements CommandNameMapping {

    @Override
    public String commandToExternalCommandType(Command command) {
        return command.getClass().getName();
    }

    @Override
    public String externalCommandTypeToCommandClassName(String commandTypeHeader) {
        return commandTypeHeader;
    }
}

    