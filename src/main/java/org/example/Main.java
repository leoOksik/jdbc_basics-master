package org.example;

import org.example.dao.AuthorDao;
import org.example.dao.AuthorDaoImpl;
import org.example.model.Author;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println("Не удалось загрузить драйвер");
//        }

        String url = "jdbc:postgresql://localhost:5432/lesson_2";
        String user = "postgres";
        String password = "123";

        AuthorDao dao = new AuthorDaoImpl(url, user, password);
        Author author = dao.getById(2);
        System.out.println("Author: " + author.getName());
        System.out.println("Books: ");
        author.getBooks().forEach(book -> System.out.println(book.getTitle()));

        System.out.println("All: ");
        dao.getAll().forEach(System.out::println);

        Author author1 = new Author();
        dao.delete(6);
        author1.setName("Пушкин А.С.");
        dao.save(author1);

        author1.setName("Толстой Л.Н.");
        dao.save(author1);
        dao.delete(7);


    }
}
