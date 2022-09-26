package com.geekbrains;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadFactory;

public class CloudServer {

    public static void main(String[] args){
        /*
        Создаем ссылку на функциональный интерфейс ThreadFactory
        с помощью которого помот будем запускать FileHandler
        поток будет демоном
         */
        ThreadFactory serviceThreadFactory = r -> {
            Thread thread = new Thread(r);
            thread.setName("file-handler-thread-%");
            thread.setDaemon(true);
            return thread;
        };

        //создаем серверный сокет
        try(ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println("Server started success");
            while (true) {
                System.out.println("Waiting for client connections");
                Socket socket = serverSocket.accept(); // ожидаем подключения клиента
                // полученный клиентский сокет передаем в поток FileHandler-а
                serviceThreadFactory.newThread(new FileHandler(socket)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
