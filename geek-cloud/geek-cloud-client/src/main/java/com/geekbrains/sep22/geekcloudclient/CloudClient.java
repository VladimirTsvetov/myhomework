package com.geekbrains.sep22.geekcloudclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CloudClient extends Application {
    /**
     * переопределяем функцию start: прописываем fxml-файл с описанием графического интерфейса
     * geek-cloud-client.fxml
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("geek-cloud-client.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Cloud client!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}