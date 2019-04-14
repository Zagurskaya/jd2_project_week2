package com.gmail.zagurskaya.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CreateTable {

    private static final Logger logger = LogManager.getLogger(CreateTable.class);

    public void readСommands(Connection connection) throws SQLException {
//public static void main(String[] args) {
        String filePath = getStringPath("document.sql");
        StringBuilder textFromFile = new StringBuilder();
        textFromFile = readTextFile(filePath, textFromFile);
        List<String> commandsCreateList = readCommandsFromSqlToList(textFromFile);
        createTable(connection, commandsCreateList);
    }

    private String getStringPath(String filename) {
        return this.getClass().getResource("/" + filename).getPath();
    }

    private static StringBuilder readTextFile(String filePath, StringBuilder textFromFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            for (String value = br.readLine(); value != null; value = br.readLine()) {
                textFromFile.append(value).append('\n');
                int i = 0;
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return textFromFile;
    }

    private static List<String> readCommandsFromSqlToList(StringBuilder text) {
        return Arrays.stream(Arrays.stream(text.toString()
                .split("\n"))
                .filter(s -> s.length() != 0)
                .filter(s -> !s.startsWith("-- "))
                .collect(Collectors.joining())
                .split(";"))
                .map(s -> s + ";")
                .filter(s -> s.startsWith("CREATE"))
                .collect(Collectors.toList());
    }

    private void createTable(Connection connection, List<String> commands) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            for (String string : commands) {
                statement.executeUpdate(string);
            }
        }
    }
}
