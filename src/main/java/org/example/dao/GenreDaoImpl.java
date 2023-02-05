package org.example.dao;

import org.example.model.Genre;

import java.sql.*;

public class GenreDaoImpl implements GenreDao {
    private String url;
    private String user;
    private String password;

    public GenreDaoImpl(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Genre getById(int id) throws SQLException {
        try(Connection cnn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM Genre WHERE genre_id = ?";
            PreparedStatement statement = cnn.prepareStatement(query);
            statement.setInt(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if(!resultSet.next()) {
                return null;
            }
            Genre genre = new Genre();
            genre.setId(resultSet.getInt("genre_id"));
            genre.setName(resultSet.getString("name_genre"));
            return genre;
        }
    }
}
