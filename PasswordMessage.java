package com.geekbrains.model;

public class PasswordMessage implements CloudMessage{
    private final String password;

    public PasswordMessage(String password) {
        this.password = password;
    }

    @Override
    public MessageType getType() {
        return MessageType.PASSWORD;
    }
}
