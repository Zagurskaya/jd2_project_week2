package com.gmail.zagurskaya.connection;


import com.gmail.zagurskaya.exception.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public abstract class AbstractDao {
    private static final Logger logger = LogManager.getLogger(AbstractDao.class);
    private static final String ERROR_MESSAGE = "Connection Failed! Check output console.";

    public boolean executeUpdate(String sql) throws SQLException {
        try (Connection connection = ConnCreator.getConnection();
             Statement statement = connection.createStatement()) {
            return 1 == statement.executeUpdate(sql);
        } catch (SQLException e) {
//            logger.error( e, e.printStackTrace());
            logger.error(e);
            throw new DatabaseException(ERROR_MESSAGE);
        }

    }

    public long executeCreate(String sql) throws SQLException {
        try (Connection connection = ConnCreator.getConnection();
             Statement statement = connection.createStatement()) {
            int count = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            if (count == 1) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
//            logger.error( e, e.printStackTrace());
            logger.error(e);
            throw new DatabaseException(ERROR_MESSAGE);
        }
        return 0;
    }

}
