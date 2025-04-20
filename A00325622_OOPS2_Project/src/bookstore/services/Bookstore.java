package bookstore.services;

import bookstore.models.Book;
import bookstore.models.BookCategory;
import bookstore.models.Customer;

import java.util.function.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Bookstore {

    private final List<Book> inventory;
    private final List<Customer> customers;

    public Bookstore() {
        this.inventory = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public void addBook(Book book) {
        inventory.add(book);
    }

    public void removeBook(String isbn) {
        inventory.removeIf(b -> b.id().equalsIgnoreCase(isbn));
    }

    public Optional<Book> findBookByTitle(String title) {
        return inventory.stream()
                .filter(b -> b.title().equalsIgnoreCase(title))
                .findFirst();
    }

    public List<Book> searchBooks(Predicate<Book> filter) {
        return inventory.stream()
                .filter(filter)
                .sorted(Comparator.comparing(Book::title))
                .toList();
    }

    public void printBooksGroupedByCategory() {
        Map<BookCategory, List<Book>> grouped = inventory.stream()
                .collect(Collectors.groupingBy(Book::category));
        grouped.forEach((category, books) -> {
            System.out.println("\nCategory: " + category);
            books.forEach(book -> System.out.println(" - " + book.title()));
        });
    }

    public double getTotalInventoryValue() {
        return inventory.stream()
                .mapToDouble(book -> book.price() * book.stock())
                .sum();
    }

    public Map<String, Double> getTitlePriceMap() {
        return inventory.stream()
                .collect(Collectors.toMap(Book::title, Book::price));
    }

    public void processBulkOrders(List<Book> booksToOrder) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        List<Callable<String>> tasks = booksToOrder.stream()
                .map(book -> (Callable<String>) () -> {
                    Thread.sleep(100); // simulate processing
                    return "Processed order for: " + book.title();
                })
                .toList();

        List<Future<String>> results = executor.invokeAll(tasks);
        results.forEach(future -> {
            try {
                System.out.println(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executor.shutdown();
    }

    public long countBooksAbovePrice(double minPrice) {
        return inventory.stream()
                .filter(book -> book.price() > minPrice)
                .count();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Book> filterBooks(Predicate<Book> condition) {
        return inventory.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    public Map<BookCategory, List<Book>> groupBooksByCategory() {
        return inventory.stream()
                .collect(Collectors.groupingBy(Book::category));
    }

    public List<Book> sortBooksByPrice() {
        return inventory.stream()
                .sorted(Comparator.comparing(Book::price))
                .collect(Collectors.toList());
    }

    public Optional<Book> findMostExpensiveBook() {
        return inventory.stream()
                .max(Comparator.comparing(Book::price));
    }

    public void displayBooks(Consumer<Book> displayLogic) {
        inventory.forEach(displayLogic);
    }

    public void simulateConcurrentBookProcessing() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Callable<String>> tasks = inventory.stream()
                .map(book -> (Callable<String>) () -> {
                    Thread.sleep(500); // simulate delay
                    return "Processed: " + book.title() + " at $" + book.price();
                })
                .toList();

        try {
            List<Future<String>> results = executor.invokeAll(tasks);
            for (Future<String> result : results) {
                System.out.println(result.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
    public void saveInventoryToFile(String filename) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Book ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Book Price After Discount: ");
        double price = scanner.nextDouble();

        System.out.print("Enter Book Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); 
        
        System.out.print("Enter Book Category: ");
        String category = scanner.nextLine();

	    double totalCost = price * quantity;	

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write("ID: " + id);
            writer.newLine();
            writer.write("Title: " + title);
            writer.newLine();
            writer.write("Enter your Discounted Price: " + price);
            writer.newLine();
            writer.write("Stock: " + quantity);
            writer.newLine();
            writer.write("Category: " + category);
            writer.newLine();
            writer.write("Total Cost: " + totalCost);
            writer.newLine();
            writer.write("------------------------");
            writer.newLine();
            writer.write(" Thank you for visiting our Bookstore :)");
            writer.newLine();
            System.out.println("Book information saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving book data: " + e.getMessage());
        }
        scanner.close();
    }

    public void readBookDataFromFile(String filename) {
        Path path = Paths.get(filename);

        try {
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                System.out.println("📘 Book data from file:");
                for (String line : lines) {
                    System.out.println("  " + line);
                }
            } else {
                System.out.println("⚠ File not found: " + filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 }