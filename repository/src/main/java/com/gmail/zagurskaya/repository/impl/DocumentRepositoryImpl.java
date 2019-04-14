package com.gmail.zagurskaya.repository.impl;

import com.gmail.zagurskaya.connection.AbstractDao;
import com.gmail.zagurskaya.connection.ConnCreator;
import com.gmail.zagurskaya.connection.InitDataBase;
import com.gmail.zagurskaya.exception.DatabaseException;
import com.gmail.zagurskaya.model.Document;
import com.gmail.zagurskaya.repository.DocumentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DocumentRepositoryImpl extends AbstractDao implements DocumentRepository {

    private static final Logger logger = LogManager.getLogger(DocumentRepositoryImpl.class);

    @PostConstruct
    public void postConstruct() {
        logger.info("postConstruct");
        initDB();
    }

    @Override
    public Document add(Document document) {
        boolean documentHasId = document.getId() != null;
        String sql = documentHasId ?
                String.format("INSERT INTO `document`(`id`, `uniqueNumber`, `description`) VALUES ('%d','%s','%s')",
                        document.getId(), document.getUniqueNumber(), document.getDescription()) :
                String.format("INSERT INTO `document`(`uniqueNumber`, `description`) VALUES ('%s','%s')",
                        document.getUniqueNumber(), document.getDescription());

        try {
            document.setId(executeCreate(sql));
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Database exception during adding document", e);
        }
        return document.getId() > 0 ? document : null;
    }

    @Override
    public Document getDocumentById(Long id) {
        List<Document> documents = null;
        documents = getAll(" WHERE id=" + id);
        return documents.size() == 0 ? null : documents.get(0);
    }

    @Override
    public void delete(Long id) {
        String sql = String.format(
                "DELETE FROM `document` WHERE `id`='%s'", id);
        try {
            executeUpdate(sql);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Database exception during deleting document", e);
        }
    }


    public List<Document> getAll() {
        return getAll("");
    }

    public List<Document> getAll(String where) {
        List<Document> result = new ArrayList<>();
        try (Connection connection = ConnCreator.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = String.format(
                    "SELECT * FROM `document` " + where);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Document document = new Document();
                document.setId(resultSet.getLong("id"));
                document.setUniqueNumber(resultSet.getString("uniqueNumber"));
                document.setDescription(resultSet.getString("description"));
                result.add(document);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Database exception during deleting All document", e);
        }
        return result;
    }

    private void initDB() {
        InitDataBase.createDBFromSQL();
    }
}
