package org.example;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class Dao implements AutoCloseable {
    private String url;
    private String driver;
    private String username;
    private String password;
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    private void connect() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(true);
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public Dao(File config) throws SQLException, ClassNotFoundException, IOException {
        Map<String, String> mapOfParam = XMLParser.parseXML(config);
        if (mapOfParam == null) {
            throw new IOException("Config file is wrong.");
        }
        String url = mapOfParam.get("connectionUrl");
        String username = mapOfParam.get("userName");
        String password = mapOfParam.get("password");
        String driver = mapOfParam.get("driverClass");
        this.url = url;
        this.driver = driver;
        this.username = username;
        this.password = password;
        connect();
    }
}
