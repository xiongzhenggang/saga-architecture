package com.xzg.orchestrator.kit.command;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.command
 * @className: CommandNameMapping
 * @author: xzg
 * @description: TODO
 * @date: 19/11/2023-下午 2:53
 * @version: 1.0
 */
public interface CommandNameMapping {

    String commandToExternalCommandType(Command command);
    String externalCommandTypeToCommandClassName(String commandTypeHeader);

}

    