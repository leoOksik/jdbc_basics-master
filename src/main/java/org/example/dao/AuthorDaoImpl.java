package org.example.dao;

import org.example.model.Author;
import org.example.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoImpl implements AuthorDao {
    private String url;
    private String user;
    private String password;

    private GenreDao genreDao;

    public AuthorDaoImpl(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.genreDao = new GenreDaoImpl(url, user, password);
    }

    @Override
    public Author getById(int id) throws SQLException {
        List<Book> books = new ArrayList<>();

        try (Connection cnn = DriverManager.getConnection(url, user, password)) {
            String query =
                    "SELECT * FROM author a\n" +
                            "INNER JOIN book b on a.author_id = b.author_id\n" +
                            "WHERE a.author_id = ?";
            PreparedStatement statement = cnn.prepareStatement(query);

            Author author = new Author();
            getData(cnn, id, query, books,author);
            author.setBooks(books);
            return author;
        }
    }

    @Override
    public List<Author> getAll() throws SQLException {

        List<Book> books = new ArrayList<>();
        List<Author> authors = new ArrayList<>();

        int id = 1;
        int num = 0;

        try (Connection cnn = DriverManager.getConnection(url, user, password)) {
            String query =
                    "SELECT * FROM author a " +
                            "INNER JOIN book b on a.author_id = b.author_id " +
                            "INNER JOIN genre g on g.genre_id = b.genre_id \n" +
                            "WHERE a.author_id = ?";

            PreparedStatement st = cnn.prepareStatement("SELECT MAX(author_id) as id FROM book");
            ResultSet resultSet = st.executeQuery();

            if (resultSet.next()) {
                num = resultSet.getInt("id");
            }
            while (id <= num) {
                Author author = new Author();
                getData(cnn, id, query, books,author);
                author.setBooks(new ArrayList<>(books));
                authors.add(author);
                books.clear();
                id++;
            }
            return authors;
        }
    }

    @Override
    public void save(Author author) throws SQLException {

        try (Connection cnn = DriverManager.getConnection(url, user, password)) {
            String query =
                    "INSERT INTO author VALUES(?,?)";
            PreparedStatement statement = cnn.prepareStatement(query);
            PreparedStatement st = cnn.prepareStatement("SELECT MAX(author_id) as id FROM author");
            ResultSet resultSet = st.executeQuery();

            int x = 0;
            if (resultSet.next()) {
                x = resultSet.getInt("id");
            }
            statement.setInt(1, x + 1);
            statement.setString(2, author.getName());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (Connection cnn = DriverManager.getConnection(url, user, password)) {
            String query =
                    "DELETE FROM author WHERE author_id = ?";
            PreparedStatement statement = cnn.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();

        }
    }

    public void getData(Connection cnn, int id, String query,
                        List<Book> books, Author author) throws SQLException {

        PreparedStatement statement = cnn.prepareStatement(query);
        statement.setInt(1, id);
        statement.execute();
        ResultSet data = statement.getResultSet();
        data.next();

        author.setName(data.getString("name_author"));
        author.setId(data.getInt("author_id"));


        do {
            Book book = new Book();
            book.setAuthor(author);
            book.setId(data.getInt("book_id"));
            book.setAmount(data.getInt("amount"));
            book.setPrice(data.getDouble("price"));
            book.setTitle(data.getString("title"));
            book.setGenre(genreDao.getById(data.getInt("genre_id")));
            books.add(book);
        } while (data.next());

    }
}
