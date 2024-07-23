package com.console_project.server.service;

import com.console_project.server.repository.UserRepository;
import com.console_project.shared.model.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean addUser(User user) {
        return userRepository.add(user);
    }

    public boolean findUser(User user) {
        return userRepository.find(user);
    }
}
