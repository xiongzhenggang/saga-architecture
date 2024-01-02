package com.xzg.orchestrator.kit.command;

import com.xzg.orchestrator.kit.message.Message;

import java.util.List;

/**
 * @projectName: saga-architecture
 * @package: com.xzg.orchestrator.kit.common.command
 * @className: CommandExceptionHandler
 * @author: xzg
 * @description: TODO
 * @date: 18/11/2023-下午 6:53
 * @version: 1.0
 */
public class CommandExceptionHandler {
    public List<Message> invoke(Throwable cause) {
        throw new UnsupportedOperationException();
    }
}

    