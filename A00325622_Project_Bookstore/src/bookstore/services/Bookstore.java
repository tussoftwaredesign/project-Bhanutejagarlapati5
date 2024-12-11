package bookstore.services;

import bookstore.models.*;
import bookstore.members.*;
import bookstore.exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Bookstore implements BookStoreOperations {
    private final List<Book> books = new ArrayList<>();

    public void addBook(Book... newBooks) {
        for (Book book : newBooks) {
            books.add(book);
        }
    }

    public List<Book> filterBooksByCategory(BookCategory category) {
        Predicate<Book> byCategory = book -> book.getCategory() == category;
        return books.stream().filter(byCategory).collect(Collectors.toList());
    }

    public Book findBookByTitle(String title) throws BookNotFoundException {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book with title '" + title + "' not found."));
    }

    public void displayBooks() {
        System.out.println("\nBooks in Store:");
        books.forEach(System.out::println);
    }

    public void printMemberType(Member member) {
        if (member instanceof GoldMember gold) {
            System.out.println("Gold Member: " + gold.getName());
        } else if (member instanceof RegularMember regular) {
            System.out.println("Regular Member: " + regular.getName());
        } else {
            System.out.println("Unknown Member Type.");
        }
    }
}
