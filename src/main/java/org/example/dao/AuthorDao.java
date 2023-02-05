package org.example.dao;

import org.example.model.Author;

import java.sql.SQLException;
import java.util.List;

//Data Access Object - объект доступа к данным
public interface AuthorDao {
    //CRUD операции - CREATE READ UPDATE DELETE
    Author getById(int id) throws SQLException;
    List<Author> getAll() throws SQLException;
    void save(Author author) throws SQLException;
    void delete(int id) throws SQLException;
}
