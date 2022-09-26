package com.geekbrains;

import java.io.*;
import java.net.Socket;

/**
 * Класс FileHandler имплементирует интерфейс Runnable - запускаем его в потоке
 */
public class FileHandler implements Runnable {

    private static final String SERVER_DIR = "server_files"; //серверная директория
    private static final String SEND_FILE_COMMAND = "file";
    private static final String GET_FILE_LIST_COMMAND = "file_list";
    private static final String READ_FILE_FROM_SERVER = "read_file";
    private static final String HOLD_FILE_COMMAND = "hold_file";

    private static final Integer BATCH_SIZE = 256;  // размер пересылаемой части файла

    private final Socket socket;

    private final DataInputStream dis;

    private final DataOutputStream dos;

    private byte[] batch;

    /**
     * получаем на вход клиентский сокет
     * создаем соответствующие потоки приема-передачи данных от клиента
     * @param socket
     * @throws IOException
     */
    public FileHandler(Socket socket) throws IOException {
        this.socket = socket;
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        batch = new byte[BATCH_SIZE];
        File file = new File(SERVER_DIR); // если серверная директория не создана, то создаем ее
        if (!file.exists()) {
            file.mkdir();
        }
        sendServerFile();
        System.out.println("Client accepted...");


    }

    public void sendServerFile() throws IOException{
        File dir = new File(SERVER_DIR);
        String[] files = dir.list();
        assert files != null;
        dos.writeUTF(GET_FILE_LIST_COMMAND);
        dos.writeInt(files.length);
        for(String file:files) {
            dos.writeUTF(file);
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Start listening...");
            //читаем команду от клиента в счастливом предвкушении правильной последовательности данных)
            while (true) {
                String command = dis.readUTF();
                // если команда FILE, то далее мы читаем строку с именем файла и его размер
                if (SEND_FILE_COMMAND.equals(command)) {
                    String fileName = dis.readUTF();
                    long size = dis.readLong();
                    // создаем поток вывода с полным именем файла
                    // и частями, кратными BATCH_SIZE запихиваем его туда
                    try (FileOutputStream fos = new FileOutputStream(SERVER_DIR + "/" + fileName)) {
                        for (int i = 0; i < (size + BATCH_SIZE - 1) / BATCH_SIZE; i++) {
                            int read = dis.read(batch);
                            fos.write(batch, 0, read);
                        }
                    } catch (Exception ignored) {}
                    sendServerFile();
                }
                else if(READ_FILE_FROM_SERVER.equals(command)){
                    String readFileName = dis.readUTF();
                    System.out.println(readFileName);
                    File file = new File(readFileName);
                    long readSize = file.length();
                    try {
                        dos.writeUTF(HOLD_FILE_COMMAND); // отправляем команду ОТПРАВИТЬ ФАЙЛ
                        dos.writeUTF(readFileName);  // отправляем имя файла
                        dos.writeLong(readSize);  // отправляем длину файла
                        try (FileInputStream fis = new FileInputStream(SERVER_DIR + "/" + readFileName)) { //открываем поток чтения файла
                            byte[] bytes = fis.readAllBytes(); //читаем все из файла
                            dos.write(bytes); //пишем прочитанное в выходной поток
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (Exception e) {
                        System.err.println("e = " + e.getMessage());
                    }

                }
                else System.out.println("Unknown command received: " + command);
            }
        } catch (Exception ignored) {
            System.out.println("Client disconnected...");
        }
    }
}
