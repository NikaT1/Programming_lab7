package com.console_project.shared.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String username;
    private final String password;

    public User() {
        username = "";
        password = "";
    }

}
