package com.geekbrains.netty.serial;

import com.geekbrains.model.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileHandler extends SimpleChannelInboundHandler<CloudMessage> {
    private final static List<Channel> channels = new ArrayList<>(); //будем хранить клиентов
    private static int clientConnectIndex;                           //id нового клиента

    private Path serverDir;

    private ClientDB clientDB;

    //добавляем передачу хэндлеру объекта, работающего с БД
    public FileHandler(ClientDB clientDB) {
        this.clientDB = clientDB;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
        clientConnectIndex++;
        log.debug("Client with id: " + clientConnectIndex + " connected..." );
        serverDir = Path.of("server_files");
        ctx.writeAndFlush(new ListMessage(serverDir));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CloudMessage cloudMessage) throws Exception {

        log.debug("Received: {}", cloudMessage.getType());
        if (cloudMessage instanceof FileMessage fileMessage) {
            Files.write(serverDir.resolve(fileMessage.getFileName()), fileMessage.getBytes());
            ctx.writeAndFlush(new ListMessage(serverDir));
        }
        else if (cloudMessage instanceof FileRequest fileRequest) {
            ctx.writeAndFlush(new FileMessage(serverDir.resolve(fileRequest.getFileName())));
        }
        else if(cloudMessage instanceof FileDelete fileDelete){
            Files.deleteIfExists(serverDir.resolve(fileDelete.getFileName()));
            ctx.writeAndFlush(new ListMessage(serverDir));
        }
        //если мы получаем пароль и никнайм, то мы создаем в базе пользователя с такими данными
        else if(cloudMessage instanceof PasswordMessage passwordMessage){
            //здесь реализуем логику обработки чтения пароля и ника, делим строку по символу #
            String[] strings = passwordMessage.getPassword().split("#");
            //добавляем пользователя в бд
            clientDB.insertUserNickAndPass(strings[0],strings[1]);
        }


    }
}
