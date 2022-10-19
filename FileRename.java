package com.geekbrains.model;

import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public class FileRename implements CloudMessage{
    String fileName;

    public FileRename(Path file) throws IOException {
        fileName = file.getFileName().toString();
    }

    @Override
    public MessageType getType() {
        return MessageType.RENAME;
    }
}
