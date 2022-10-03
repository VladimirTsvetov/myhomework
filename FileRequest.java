package com.geekbrains.model;

import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public class FileRequest implements CloudMessage {

    private final String fileName;
    private final long size;
    private final byte[] bytes;

    public FileRequest(Path file) throws IOException {

        fileName = file.getFileName().toString();
        bytes = Files.readAllBytes(file);
        size = bytes.length;
    }

    @Override
    public MessageType getType() {
        return MessageType.FILE_REQUEST;
    }
}
