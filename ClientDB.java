package com.geekbrains.netty.serial;

import java.sql.*;

public class ClientDB {
    private static Connection connection;
    private static Statement statement;

    public ClientDB(){
        try {
            connect();  //конектимся к БД
            createTableEx();     // если таблицы еще нет, то создаем
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:javadb.db");
        statement = connection.createStatement();
    }
    public static void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            if(statement != null){
                statement.close();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTableEx() throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS clients (\n" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                " nick TEXT,\n" +
                " pass TEXT\n" +
                " );");
    }

    private static void insertUserNickAndPass(String user_nick, String user_pass) throws SQLException {

        int res = statement.executeUpdate("INSERT INTO clients (nick, pass) VALUES ('" + user_nick + "','"
            + user_pass + "');");
        System.out.println(res);




    }

    private static void readUserNickAndPass() throws SQLException {
        try (ResultSet rs = statement.executeQuery("SELECT * FROM clients;")) {
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString("nick") + " " +
                        rs.getString("pass"));
            }
        }
    }


}


