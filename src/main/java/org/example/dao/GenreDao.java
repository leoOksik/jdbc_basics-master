package org.example.dao;

import org.example.model.Genre;

import java.sql.SQLException;

public interface GenreDao {
    Genre getById(int id) throws SQLException;
}
