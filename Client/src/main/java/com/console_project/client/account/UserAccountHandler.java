package com.console_project.client.account;

import com.console_project.client.connection.ConnectionHandler;
import com.console_project.shared.connection_model.CommandResponse;
import com.console_project.shared.connection_model.UserRequest;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class UserAccountHandler {
    private final UserAccount userAccount;
    private final ConnectionHandler connectionHandler;

    public CommandResponse authenticateUser() throws IOException {
        if (userAccount.isNewAccount()) {
            return createAccount();
        } else {
            return logInAccount();
        }
    }

    private CommandResponse createAccount() throws IOException {
        return connectionHandler.sendCommand(
                new UserRequest("", "add_user", null, userAccount.user()));
    }

    private CommandResponse logInAccount() throws IOException {
        return connectionHandler.sendCommand(
                new UserRequest("", "check_user", null, userAccount.user()));
    }
}
