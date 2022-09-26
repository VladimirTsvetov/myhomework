package TelNet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class TelnetTerminal {

        /**
         * Support commands:
         * cd path - go to dir работает!
         * touch filename - create file with filename     сделано
         * mkdir dirname - create directory with dirname  сделано
         * cat filename - show filename bytes теперь это  СДЕЛАНО
         * */

        private Path current;
        private ServerSocketChannel server;
        private Selector selector;

        private ByteBuffer buf;

        public TelnetTerminal() throws IOException {
            current = Path.of("server_files");  //ОБЪЕКТ ТИПА Path С НУЖНЫМ НАМ КАТАЛОГОМ
            buf = ByteBuffer.allocate(256);  //СОЗДАЕМ БАЙТБУФЕР РАЗМРОМ 256 БАЙТ
            server = ServerSocketChannel.open();    //СОЗДАЕМ КАНАЛ ДЛЯ СЕРВЕРНОГО СОКЕТА
            selector = Selector.open();             //ПОЛУЧАЕМ СЕЛЕКТОР
            server.bind(new InetSocketAddress(8189)); //СВЯЗЫВАЕМ СЕРВЕРСОКЕТ С ЛОКАЛЬНЫМ АДРЕСОМ ПО ПОРТУ 8189
            server.configureBlocking(false);        //УСТАНАВЛИВАЕМ НЕБЛОКИРУЮЩИЙ РЕЖИМ ДЛЯ СОКЕТА
            server.register(selector, SelectionKey.OP_ACCEPT); //РЕГИСИТРИРУЕМ СОКЕТ НА СЕЛЕКТОРЕ, СЛУШАЕМ СОБЫТИЕ "ПРИЕМ"
            while (server.isOpen()) {  //ПОКА СОКЕТ ОТКРЫТ....
                selector.select();  //БЛОКИРУЕМ СОКЕТ ПОКА КАНАЛ НЕ БУДЕТ ГОТОВ
                Set<SelectionKey> keys = selector.selectedKeys(); // ПОЛУЧАЕМ КЛЮЧИ РЕГИСТРАЦИИ КАНАЛА
                Iterator<SelectionKey> keyIterator = keys.iterator();
                while (keyIterator.hasNext()) { // ЧИТАЕМ КЛЮЧИ И РЕАГИРУЕМ СООТВЕТСТВЕННО
                    SelectionKey key = keyIterator.next();
                    if (key.isAcceptable()) { // ЕСЛИ ПОДКЛЮЧЕНИЕ - ПОДКЛЮЧАЕМ
                        handleAccept();
                    }
                    if (key.isReadable()) { // ЕСЛИ ЧТЕНИЕ - ЧИТАЕМ
                        handleRead(key);
                    }
                    keyIterator.remove();  // ЧИСТИМ ПРОЧИТАННЫЙ КЛЮЧ ИБО КЛЮЧ САМ СЕБЯ НЕ ПОЧИСТИТ
                }
            }
        }

        private void handleRead(SelectionKey key) throws IOException {
            SocketChannel channel = (SocketChannel) key.channel(); // СОЗДАЕМ СОКЕТ-КАНАЛ ПО КЛЮЧУ
            buf.clear(); // ЧИСТИМ БУФЕР
            StringBuilder sb = new StringBuilder(); // СЮДА БУДЕМ ЗАПИСЫВАТЬ
            while (true) {
                int read = channel.read(buf); // ЧИТАЕМ ИЗ КАНАЛА В БУФЕР
                if (read == 0) { // ЕСЛИ ЧИТАТЬ НЕЧЕГО, ТО ФСЁ
                    break;
                }
                if (read == -1) { // ЕСЛИ СОКЕТ ЗАКРЫЛСЯ ТО ЗАКРЫВАЕМ КАНАЛ, ГАСИМ СВЕТ И УХОДИМ
                    channel.close();
                    return;
                }
                buf.flip(); // СТАВИМ ЛИМИТ = ТЕКУЩЕЙ ПОЗИЦИИ, ТЕКУЩУЮ ПОЗИЦИЮ СТАВИМ В НОЛЬ (ТАКАЯ ФУНКЦИЯ!)
                while (buf.hasRemaining()) { // ПОКА ЕСТЬ ЧЕГО В БУФЕРЕ...
                    sb.append((char) buf.get());  // ЧИТАЕМ И ЗАПИХИВАЕМ В СТРИНГБИЛДЕР
                }
                buf.clear(); // ОПЯТЬ ЖЕ ЧИСТИМ БУФЕР
            }
            System.out.println("Received: " + sb); // ПЕЧАТАЕМ ПРОЧИТАННОЕ
            String command = sb.toString().trim(); // СТРИЖЁМ ВПЕРЕДИИДУЩИЕ И ЗАМЫКАЮЩИЕ ПРОБЕЛЫ
            String[] words = command.split(" ");
            //ВОТ ТУТ НАДО ОРГАНИЗОВАТЬ ВСЕ КОМАНДЫ
            //РАЗБИВАЕМ СТРОКУ НА ЧАСТИ И АНАЛИЗИРУЕМ КОМАНДУ И (ЕСЛИ ДОЛЖНЫ БЫТЬ) ПАРАМЕТРЫ
            // ЕСЛИ ПОЛУЧЕНА КОМАНДА LS ТО ВЫВОДИМ СПИСОК ФАЙЛОВ ТЕКУЩЕГО КАТАЛОГА
            //*****************************************************************************
            if("ls".equals(words[0])){
                String files = Files.list(current)
                        .map(p -> p.getFileName().toString())
                        .collect(Collectors.joining("\n\r"));
                channel.write(ByteBuffer.wrap(files.getBytes(StandardCharsets.UTF_8)));
            // ЕСЛИ КОМАДНА MKDIR + ИМЯ КАТАЛОГА, ТО СОЗДАЕМ В ТЕКУЩЕМ КАТАЛОГЕ
            // ТО ПРОВЕРЯЕМ, ЕСТЬ ЛИ УЖЕ ТАКОЙ КАТАЛОГ?
            //*******************************************************************************
            }else if("mkdir".equals(words[0])) {
                Path path = Paths.get(current.toAbsolutePath().toString() + "/" + words[1]);
                if(Files.exists(path)){  // ЕСЛИ ЕСТЬ, ТО СООБЩАЕМ ОБ ЭТОМ
                    StringBuilder message = new StringBuilder();

                    message.append("Directory " + words[1] + " exists!");
                    channel.write(ByteBuffer.wrap(message.toString().getBytes()));
                }
                else { // ЕСЛИ НЕТ, ТО СОЗДАЕМ И СООБЩАЕМ ОБ УСПЕХЕ
                    //java.nio.file.Files;
                    Files.createDirectories(path);
                    StringBuilder message = new StringBuilder();

                    message.append("Directory " + words[1] + "is created success!");
                    channel.write(ByteBuffer.wrap(message.toString().getBytes()));
                    System.out.println("Directory is created!");
                }
            // ЕСЛИ ПОЛУЧЕНА КОМАНДА TOUCH (СОЗДАТЬ ФАЙЛ С УКАЗАНЫМ ИМЕНЕМ), ТО
            // *****************************************************************
            }else if("touch".equals(words[0])){ // ЕСЛИ НЕТ, ТО СОЗДАЕМ И СООБЩАЕМ ОБ УСПЕХЕ
                Path path = Paths.get(current.toAbsolutePath().toString() + "/" + words[1]);
                if(Files.exists(path)) {  // ЕСЛИ ЕСТЬ, ТО СООБЩАЕМ ОБ ЭТОМ
                    StringBuilder message = new StringBuilder();

                    message.append("File " + words[1] + " exists!");
                    channel.write(ByteBuffer.wrap(message.toString().getBytes()));
                }
                else{ // СОЗДАЕМ ФАЙЛ И РАДОСТНО СООБЩАЕМ ОБ ЭТОМ
                    File f = new File(current.toAbsolutePath().toString() + "/" + words[1]);
                    f.createNewFile();
                    StringBuilder message = new StringBuilder();

                    message.append("File " + words[1] + " is created!");
                    channel.write(ByteBuffer.wrap(message.toString().getBytes()));

                }
            // ЕСЛИ ПОЛУЧЕНА КОМАНДА CAT (НЕ В СМЫСЛЕ КОШКА, А ВЫВЕСТИ СОДЕРЖИМОЕ ФАЙЛА), ТО
            }else if("cat".equals(words[0])){
                Path path = Paths.get(current.toAbsolutePath().toString() + "/" + words[1]);
                if(!Files.exists(path)) {  // ЕСЛИ НЕТ ТАКОГО ФАЙЛА, ТО СООБЩАЕМ ОБ ЭТОМ
                    StringBuilder message = new StringBuilder();

                    message.append("File " + words[1] + " is not exists");
                    channel.write(ByteBuffer.wrap(message.toString().getBytes()));
                }
                else{  // СОЗДАЕМ ПОТОК ЧТЕНИЯ ФАЙЛА И БУФЕР
                    FileInputStream fis = new FileInputStream(path.toAbsolutePath().toString());
                    FileChannel fcin = fis.getChannel(); //СОЗДАЕМ КАНАЛ ДЛЯ ЧТЕНИЯ ПОТОКА
                    ByteBuffer buf = ByteBuffer.allocateDirect(1024);

                    // ПОКА ЕСТЬ ЧТО ЧИТАТЬ
                    while (buf.hasRemaining()) {
                        buf.clear();
                        if (fcin.read(buf) < 0) {
                            break;
                        }
                        buf.flip();
                       channel.write(buf);
                    }
                    fis.close();
                    fcin.close();

                }
            } else if ("cd".equals(words[0])) {
                current = Paths.get(words[1]);
                if(Files.exists(current.toAbsolutePath())) {
                    channel.write(ByteBuffer.wrap(current.toAbsolutePath()
                            .toString()
                            .getBytes())); // ПИШЕМ В КАНАЛ ОБЕРНУТЫЙ В БУФЕР МАССИВ БАЙТОВ
                }
                else{
                    StringBuilder message = new StringBuilder();
                    message.append("Wrong path " + words[1] );
                    channel.write(ByteBuffer.wrap(message.toString().getBytes()));
                }

            } else {// ИНАЧЕ ВЕЖЛИВО ВОЗВРАЩАЕМ ОБРАТНО НЕОПОЗНАННОЕ
                byte[] bytes = command.getBytes(StandardCharsets.UTF_8); // ПРЕОБРАЗУЕМ СТРОКУ В МАССИВ БАЙТОВ
                channel.write(ByteBuffer.wrap(bytes)); // ПИШЕМ В КАНАЛ ОБЕРНУТЫЙ В БУФЕР МАССИВ БАЙТОВ
            }
        }


        private void handleAccept() throws IOException {
            SocketChannel socketChannel = server.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            System.out.println("Client accepted");
        }

        public static void main(String[] args) throws IOException {
            new TelnetTerminal();
        }
    }

