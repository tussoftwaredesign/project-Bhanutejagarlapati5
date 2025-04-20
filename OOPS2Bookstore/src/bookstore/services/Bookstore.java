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
    public void saveInventoryToFile(String filename) throws IOException {
        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Book book : inventory) {
                writer.write("%s,%s,%.2f,%d,%s%n".formatted(
                    book.id(), book.title(), book.price(), book.stock(), book.category()));
            }
            System.out.println("Random book data saved to: " + path.toAbsolutePath());
        }
    }

    public void loadInventoryFromFile(String filename) throws IOException {
        Path path = Paths.get(filename);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            inventory.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 6) continue; // Skip malformed lines
                Book book = new Book(
                    parts[0].trim(),                        // id
                    parts[1].trim(),                        // title
                    parts[2].trim(),                        // author
                    BookCategory.valueOf(parts[3].trim().toUpperCase()), // category
                    Double.parseDouble(parts[4].trim()),    // price
                    Integer.parseInt(parts[5].trim())       // stock
                );
                inventory.add(book);
            }
        }
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