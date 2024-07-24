package com.console_project.client.io_util;

import com.console_project.client.account.UserAccount;
import com.console_project.shared.model.User;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

import static com.console_project.client.io_util.Encoder.getEncodedPassword;

@RequiredArgsConstructor
public class IOutilUser implements IOutil<UserAccount> {
    private final String INIT_MESSAGE = "У вас есть аккаунт?";
    private final String WRONG_INPUT_MESSAGE = "Неверный формат данных, повторите ввод!";
    private final Scanner scanner;

    @Override
    public UserAccount readObject() {
        boolean hasAccount = readAnswer(INIT_MESSAGE);
        return new UserAccount(new User(readUsername(), getEncodedPassword(readPassword())), !hasAccount);
    }

    private String readUsername() {
        boolean flag = false;
        String name = null;
        while (!flag) {
            flag = true;
            name = readField("Введите логин:");
            if (name.isEmpty()) {
                write(WRONG_INPUT_MESSAGE);
                flag = false;
            }
        }
        return name;
    }

    private String readPassword() {
        boolean flag = false;
        String name = null;
        while (!flag) {
            flag = true;
            name = readField("Введите пароль:");
            if (name.length() < 7) {
                write(WRONG_INPUT_MESSAGE);
                flag = false;
            }
        }
        return name;
    }

    @Override
    public String readField(String message) {
        write(message);
        return scanner.nextLine();
    }

    @Override
    public boolean readAnswer(String message) {
        while (true) {
            String s = readField(message).toLowerCase();
            switch (s) {
                case "yes":
                    return true;
                case "no":
                    return false;
                default:
                    System.out.println("Неверный ввод! Введите yes/no");
            }
        }
    }
}
