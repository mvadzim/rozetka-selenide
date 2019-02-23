package com.github.mvadzim.rozetka.selenide.utils;

import java.sql.*;
import java.util.Arrays;

public class Db {
    private static final Util util = new Util();
    private Connection connection = null;
    private Statement statement = null;
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DATABASE_URL = util.getConfigPoperty("db.url");
    private final String USER = util.getConfigPoperty("db.user");
    private final String PASSWORD = util.getConfigPoperty("db.password");

    public void createDbConnect() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            statement = connection.createStatement();
        } catch (ClassNotFoundException ex) {
            System.err.println("ClassNotFoundException\n" + Arrays.toString(ex.getStackTrace()));
        } catch (SQLException ex) {
            System.err.println("SQLException\n" + Arrays.toString(ex.getStackTrace()));
        }
    }

    public void closeDbConnect() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            System.err.println("SQLException\n" + Arrays.toString(ex.getStackTrace()));
        }
    }

    public void executeQuery(String query) {
        try {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            System.err.println("SQLException\n" + Arrays.toString(ex.getStackTrace()));
        }
    }
}
