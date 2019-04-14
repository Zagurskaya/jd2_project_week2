package com.gmail.zagurskaya.connection;


import com.gmail.zagurskaya.exception.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class InitDataBase {

    private static final Logger logger = LogManager.getLogger(InitDataBase.class);

    public static void createDBFromSQL() {
        try (Connection connectionSchema = ConnCreator.getConnectionChema()) {
            new CreateSchema().reset(connectionSchema);
        } catch (SQLException e) {
            logger.error(e);
            throw new DatabaseException("Error on reset schema", e);
        }
        try (Connection connection = ConnCreator.getConnection()) {
            new Reset().reset(connection);
            new CreateTable().readСommands(connection);
        } catch (SQLException e) {
            logger.error(e);
            throw new DatabaseException("Error on init", e);
        }
    }
}
