package com.gmail.zagurskaya.connection;


import com.gmail.zagurskaya.exception.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnCreator {

    private static final Logger logger = LogManager.getLogger(ConnCreator.class);
    private static final String ERROR_MESSAGE = "Error loading driver";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error(ERROR_MESSAGE, e);
            throw new DatabaseException(ERROR_MESSAGE);
        }
    }

    private static volatile Connection connection;

    private ConnCreator() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            synchronized (ConnCreator.class) {
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection(
                            CN.URL, CN.USER, CN.PASSWORD
                    );
                }
            }

        }
        return connection;
    }

    public static Connection getConnectionChema() throws SQLException {
        if (connection == null || connection.isClosed()) {
            synchronized (ConnCreator.class) {
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection(
                            CN.URLCHEMA, CN.USER, CN.PASSWORD
                    );
                }
            }

        }
        return connection;
    }
}
