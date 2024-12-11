package bookstore.main;

import bookstore.models.*;
import bookstore.members.*;
import bookstore.services.*;
import bookstore.exceptions.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        var bookstore = new Bookstore();

        // Adding books
        bookstore.addBook(new Book("Effective Java", "Joshua Bloch", BookCategory.NON_FICTION, 45.99));
        bookstore.addBook(new Book("Clean Code", "Robert Martin", BookCategory.NON_FICTION, 39.99));
        bookstore.addBook(new Book("Harry Potter", "J.K. Rowling", BookCategory.FICTION, 29.99, LocalDate.of(2000, 7, 21)));

        // Displaying books
        bookstore.displayBooks();

        // Filtering books by category
        System.out.println("\nFiction Books:");
        bookstore.filterBooksByCategory(BookCategory.FICTION).forEach(System.out::println);

        // Creating members
        Member goldMember = new GoldMember("Bhanu", "Bhanu@gmail.com", 0.10);
        Member regularMember = new RegularMember("Teja", "Teja@gmail.com");

        System.out.println(goldMember.getMemberDetails());
        System.out.println(regularMember.getMemberDetails());

        // Printing member type
        bookstore.printMemberType(goldMember);
        bookstore.printMemberType(regularMember);

        // Immutable Address Example
        ImmutableAddress address = new ImmutableAddress("7 Library Street", "Roscommon", "RO1234");
        System.out.println("Store Address: " + address);

        // Using records
        var customer = new Customer("Ram", "ram@gmail.com");
        System.out.println("Customer Details: " + customer);

        // Exception handling
        try {
            Book foundBook = bookstore.findBookByTitle("Unknown Title");
            System.out.println("Found Book: " + foundBook);
        } catch (BookNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
