package com.geekbrains.sep22.geekcloudclient;

import com.geekbrains.DaemonThreadFactory;
import com.geekbrains.model.*;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static java.nio.file.StandardOpenOption.*;

public class CloudMainController implements Initializable {
    public ListView<String> clientView;
    public ListView<String> serverView;
    public PasswordField passwordField;
    private String currentDirectory;

    //****
    private String serverDirectory;

    private Network<ObjectDecoderInputStream, ObjectEncoderOutputStream> network;
    
    private Socket socket;

    private boolean needReadMessages = true;

    private DaemonThreadFactory factory;
    private static final int BATCH_SIZE = 256;
    private String password;

    public void downloadFile(ActionEvent actionEvent) throws IOException {
        String fileName = serverView.getSelectionModel().getSelectedItem();
        network.getOutputStream().writeObject(new FileRequest(Path.of(serverDirectory).resolve(fileName)));
    }

    public void sendToServer(ActionEvent actionEvent) throws IOException {
        String fileName = clientView.getSelectionModel().getSelectedItem();
        network.getOutputStream().writeObject(new FileMessage(Path.of(currentDirectory).resolve(fileName)));
    }

    private void readMessages() {
        try {
            while (needReadMessages) {
                CloudMessage message = (CloudMessage) network.getInputStream().readObject();
                if (message instanceof FileMessage fileMessage) {
                    //Files.write(Path.of(currentDirectory).resolve(fileMessage.getFileName()), fileMessage.getBytes());
                    fileWrite(currentDirectory,Path.of(currentDirectory).resolve(fileMessage.getFileName()), fileMessage.getBytes());
                    Platform.runLater(() -> fillView(clientView, getFiles(currentDirectory)));
                    Platform.runLater(() -> fillView(serverView,getFiles(serverDirectory)));
                } else if (message instanceof ListMessage listMessage) {
                    //Platform.runLater(() -> fillView(serverView, listMessage.getFiles()));
                    Platform.runLater(() -> fillView(serverView,getFiles(serverDirectory)));

                } else if (message instanceof FileRequest fileRequest){
                    System.out.println(serverDirectory);
                    //Files.write(Path.of(serverDirectory).resolve(fileRequest.getFileName()), fileRequest.getBytes());
                    fileWrite(serverDirectory,Path.of(serverDirectory).resolve(fileRequest.getFileName()), fileRequest.getBytes());
                    Platform.runLater(() -> fillView(clientView, getFiles(currentDirectory)));
                    Platform.runLater(() -> fillView(serverView,getFiles(serverDirectory)));
                }
                else if(message instanceof FileDelete fileDelete){
                    Files.deleteIfExists(Path.of(serverDirectory).resolve(fileDelete.getFileName()));
                    Platform.runLater(() -> fillView(clientView, getFiles(currentDirectory)));
                    Platform.runLater(() -> fillView(serverView,getFiles(serverDirectory)));
                }
            }
        } catch (Exception e) {
            System.err.println("Server off");
            e.printStackTrace();
        }
    }

    private void initNetwork() {
        try {
            socket = new Socket("localhost", 8189);
            network = new Network<>(
                    new ObjectDecoderInputStream(socket.getInputStream()),
                    new ObjectEncoderOutputStream(socket.getOutputStream())
            );
            factory.getThread(this::readMessages, "cloud-client-read-thread")
                    .start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        needReadMessages = true;
        factory = new DaemonThreadFactory();
        initNetwork();
        setCurrentDirectory(System.getProperty("user.home"));
        fillView(clientView, getFiles(currentDirectory));
        //******
        setServerDirectory("server_files");
        fillView(serverView,getFiles(serverDirectory));
        //******
        clientView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selected = clientView.getSelectionModel().getSelectedItem();
                File selectedFile = new File(currentDirectory + "/" + selected);
                if (selectedFile.isDirectory()) {
                    setCurrentDirectory(currentDirectory + "/" + selected);
                }
            }
        });
        //*********
        serverView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selected = serverView.getSelectionModel().getSelectedItem();
                File selectedFile = new File(serverDirectory + "/" + selected);
                if (selectedFile.isDirectory()) {
                    setServerDirectory(serverDirectory + "/" + selected);
                }
            }
        });
    }

    //******
    private void setServerDirectory(String directory) {
        serverDirectory = directory;
        fillView(serverView,getFiles(serverDirectory));
    }

    private void setCurrentDirectory(String directory) {
        currentDirectory = directory;
        fillView(clientView, getFiles(currentDirectory));
    }

    private void fillView(ListView<String> view, List<String> data) {
        view.getItems().clear();
        view.getItems().addAll(data);
    }

    private List<String> getFiles(String directory) {
        // file.txt 125 b
        // dir [DIR]
        File dir = new File(directory);
        if (dir.isDirectory()) {
            String[] list = dir.list();
            if (list != null) {
                List<String> files = new ArrayList<>(Arrays.asList(list));
                files.add(0, "..");
                return files;
            }
        }
        return List.of();
    }

    /**
     * запись большого файла с проверкой доступного пространства
     * @param path
     * @param buf
     * @return
     */
    private boolean fileWrite(String dir,Path path, byte[] buf) throws IOException {
        File file = new File(dir);

        if(file.getFreeSpace() <= buf.length){
            System.out.println("Не достаточно свободного пространства на диске ");
            return false;
        }
        //если место есть, то
        ByteBuffer byteBuffer = ByteBuffer.wrap(buf);

        for (int i = 0; i < (byteBuffer.capacity() + BATCH_SIZE - 1) / BATCH_SIZE; i++) {
            byte[] tmpBuff = new byte[BATCH_SIZE];
            byteBuffer.get(tmpBuff,i*BATCH_SIZE,BATCH_SIZE);
            Files.write(path.getFileName(),tmpBuff , APPEND);
        }

        return true;
    }

    public void passConfirm(ActionEvent actionEvent) throws IOException {
        password = passwordField.getText();
        network.getOutputStream().writeObject(new PasswordMessage(password));
    }
}
