package com.geekbrains.sep22.geekcloudclient;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ThreadFactory;

public class CloudMainController implements Initializable {
    public ListView<String> clientView; //ListView fx:id="clientView"
    public ListView<String> serverView; //ListView fx:id="serverView"
    private String currentDirectory;

    private DataInputStream dis;

    private DataOutputStream dos;

    private Socket socket;
    private boolean needReadMessage = true;

    private static final String SEND_FILE_COMMAND = "file";
    private static final String GET_FILE_LIST_COMMAND = "file_list";
    private static final String READ_FILE_FROM_SERVER = "read_file";
    private static final String SERVER_DIR = "server_files";
    private static final String HOLD_FILE_COMMAND = "hold_file";
    private static final int BATCH_SIZE = 256;

    ThreadFactory serviceThreadFactory = r -> {
        Thread thread = new Thread(r);
        thread.setName("file-handler-thread-%");
        thread.setDaemon(true);
        return thread;
    };

    /**
     * обработка нажатия кнопки > отправки на сервер
     * onAction="#sendToServer"
     * @param actionEvent
     */
    public void sendToServer(ActionEvent actionEvent) {
        String fileName = clientView.getSelectionModel().getSelectedItem(); //выбираем файл из списка
        String filePath = currentDirectory + "/" + fileName; //добаляем имя файла к текущей директории
        File file = new File(filePath);  //создаем файл
        if (file.isFile()) { //если это файл, то
            try {
                dos.writeUTF(SEND_FILE_COMMAND); // отправляем команду ОТПРАВИТЬ ФАЙЛ
                dos.writeUTF(fileName);  // отправляем имя файла
                dos.writeLong(file.length());  // отправляем длину файла
                try (FileInputStream fis = new FileInputStream(file)) { //открываем поток чтения файла
                    byte[] bytes = fis.readAllBytes(); //читаем все из файла
                    dos.write(bytes); //пишем прочитанное в выходной поток
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                System.err.println("e = " + e.getMessage());
            }
        }
    }

    private void readServerMessage() {
        try {
            while (needReadMessage) {
                String command = dis.readUTF();
                System.out.println(command);
                //************************************************
                if (SEND_FILE_COMMAND.equals(command)) {
                }
                else if (GET_FILE_LIST_COMMAND.equals(command)) {
                    int size = dis.readInt();
                    List<String> files = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        files.add(dis.readUTF());
                    }
                    Platform.runLater(() -> fillView(serverView, files));
                } else if (HOLD_FILE_COMMAND.equals(command)) {
                    String fileName = dis.readUTF();
                    System.out.println(command + " " + fileName);
                    long sizeFile = dis.readLong();
                    //тут падает если буфер маленький
                    byte[] batch = new byte[1024];
                    // создаем поток вывода с полным именем файла
                    // и частями, кратными BATCH_SIZE запихиваем его туда
                    try (FileOutputStream fos = new FileOutputStream(currentDirectory + "/" + fileName)) {
                        for (int i = 0; i < (sizeFile + BATCH_SIZE - 1) / BATCH_SIZE; i++) {
                            int read = dis.read(batch);
                            fos.write(batch, 0, read);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch(Exception e){
           System.err.println("Server falls");
           e.printStackTrace();
        }

    }


    /**
     * подготовка соединения
     */

    private void initNetwork() {
        try {
            socket = new Socket("localhost", 8189); // создаем сокет клиента с локалхостом и портом  8189
            dis = new DataInputStream(socket.getInputStream()); //создаем поток ввода
            dos = new DataOutputStream(socket.getOutputStream()); //создаем поток вывода
            serviceThreadFactory.newThread(this::readServerMessage).start();
        } catch (Exception ignored) {}
    }

    /**
     * инициализация
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initNetwork(); // создаем сокет и потоки чтения-записи
        setCurrentDirectory(System.getProperty("user.home")); // устанавливаем корневую директорию пользователя
                                                              // как текущую
        fillView(clientView, getFiles(currentDirectory));     // заполняем элемент просмотра списка
        clientView.setOnMouseClicked(event -> {  // для ListView реализуем обработку двойного клика мышки
            if (event.getClickCount() == 2) {
                // получаем из списка строку с именем файла, которую 2 раза кликанули
                String selected = clientView.getSelectionModel().getSelectedItem();
                // создаем объект File с именем директория + имя из списка
                File selectedFile = new File(currentDirectory + "/" + selected);
                if (selectedFile.isDirectory()) { // если это тоже директория, то ставим ее текущей
                    setCurrentDirectory(currentDirectory + "/" + selected);
                }
            }
        });
    }

    /**
     * устанавливаем текущей директорией элемента ListView директорию, которую получаем на входе
     * @param directory
     */
    private void setCurrentDirectory(String directory) {
        currentDirectory = directory;  // получаем на вход текущую директорию
        fillView(clientView, getFiles(currentDirectory)); // перередаем директорию функции выбора файлов
    }

    /**
     * заполняем ListView view  списком файлов
     * @param view  элемент типа ListView
     * @param data  список файлов
     */
    private void fillView(ListView<String> view, List<String> data) {
        view.getItems().clear();
        view.getItems().addAll(data);
    }

    /**
     * получаем список файлов выбранной директории и возвращаем его как List<String>
     * @param directory
     * @return
     */
    private List<String> getFiles(String directory) {
        // file.txt 125 b
        // dir [DIR]
        File dir = new File(directory); // создаем объект типа File, который тут суть директория
        if (dir.isDirectory()) {        // проверяем, что ОК
            String[] list = dir.list(); // получаем список файлов как строковый массив
            if (list != null) {         // если массив не пустой, то делаем из него ArrayList
                List<String> files = new ArrayList<>(Arrays.asList(list));
                files.add(0, ".."); // на верх списка добавляем элемент циганской магии ".."
                return files; // возвращем список
            }
        }
        return List.of(); // если все плохо, то вермнем нулевой лист
    }


    public void readFromServer(ActionEvent actionEvent) {

        String fileName = serverView.getSelectionModel().getSelectedItem(); //выбираем файл из списка
        System.out.println(fileName);
        //String filePath = SERVER_DIR + "/" + fileName; //добаляем имя файла к текущей директории
        //File file = new File(fileName);  //создаем объект File
        //if (file.isFile()) { //проверяем, что это таки файл
        try {
            dos.writeUTF(READ_FILE_FROM_SERVER); // отправляем команду ОТПРАВИТЬ ФАЙЛ
            dos.writeUTF(fileName);  // отправляем имя файла
            //dos.writeLong(file.length());  // отправляем длину файла
        } catch (Exception e) {
            System.err.println("e = " + e.getMessage());
        }
        //}

    }

}
