package com.gmail.zagurskaya.connection;

//import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Reset {

    public void reset(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
               statement.executeUpdate("DROP SCHEMA IF EXISTS `jd2_project_week2` ;");
        }
    }
}
