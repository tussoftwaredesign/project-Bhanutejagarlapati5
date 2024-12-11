package bookstore.models;

import java.time.LocalDate;

public class Book {
    private String title;
    private String author;
    private BookCategory category;
    private double price;
    private LocalDate publicationDate;

    public Book(String title, String author, BookCategory category, double price) {
        this(title, author, category, price, LocalDate.now());
    }

    public Book(String title, String author, BookCategory category, double price, LocalDate publicationDate) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.publicationDate = publicationDate;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public BookCategory getCategory() { return category; }
    public double getPrice() { return price; }
    public LocalDate getPublicationDate() { return publicationDate; }

    @Override
    public String toString() {
        return String.format("%s by %s [%s] - $%.2f (Published: %s)", 
                title, author, category, price, publicationDate);
    }
}
