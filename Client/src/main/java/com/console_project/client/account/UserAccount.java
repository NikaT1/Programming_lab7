package com.console_project.client.account;

import com.console_project.shared.model.User;

public record UserAccount(User user, boolean isNewAccount) {
}
