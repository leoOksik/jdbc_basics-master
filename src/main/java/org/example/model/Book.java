package org.example.model;

import lombok.Getter;
import lombok.Setter;

//POJO - plain old java object
@Getter
@Setter
public class Book {
    private int id;
    private String title;
    private Author author;
    private Genre genre;
    private double price;
    private int amount;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre=" + genre +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
