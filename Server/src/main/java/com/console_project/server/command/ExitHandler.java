package com.console_project.server.command;

import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExitHandler {
    private final Command commandBeforeExit;
    private final UserRequest request;

    public CommandResponse commandBeforeExitExecute() {
        return commandBeforeExit.execute(request);
    }
}
