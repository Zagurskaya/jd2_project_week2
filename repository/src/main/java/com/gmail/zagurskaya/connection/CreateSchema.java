package com.gmail.zagurskaya.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateSchema {

    public void reset(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS `jd2_project_week2` DEFAULT CHARACTER SET utf8 ;");
        }
    }
}
