package com.geekbrains.model;

import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public class FileDelete implements CloudMessage{
    String fileName;

    public FileDelete(Path file) throws IOException {

        fileName = file.getFileName().toString();
    }
    @Override
    public MessageType getType() {
        return MessageType.DELETE;
    }
}
