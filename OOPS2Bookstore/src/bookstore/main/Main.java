package bookstore.main;

import bookstore.models.*;
import bookstore.members.*;
import bookstore.services.Bookstore;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bookstore store = new Bookstore();
        Scanner scanner = new Scanner(System.in);

        // Seed Data
        store.addBook(new Book("111", "Java in Action", "John Doe", BookCategory.SCIENCE, 39.99, 10));
        store.addBook(new Book("222", "Mystery Lane", "Jane Roe", BookCategory.MYSTERY, 19.99, 10));
        store.addBook(new Book("333", "Fantasy World", "George Elf", BookCategory.FANTASY, 29.99, 10));

        ImmutableAddress address = new ImmutableAddress("123 Main St", "Cityville", "12345");

        Customer goldCustomer = new Customer("516830", "Alice", address, "ALIGOLD016");
        Customer silverCustomer = new Customer("163058", "Bob", address, "BOBSILVER05");
        Customer normalCustomer = new Customer("301685", "Charlie", address, "CHARLINOR200");

        store.addCustomer(goldCustomer);
        store.addCustomer(silverCustomer);
        store.addCustomer(normalCustomer);

        List<Customer> customers = store.getCustomers();

        // Select customer by ID
        Customer selectedCustomer = null;
        while (selectedCustomer == null) {
            System.out.print("Enter customer ID to select: ");
            String inputId = scanner.nextLine().trim();

            Optional<Customer> customerOpt = customers.stream()
                    .filter(c -> c.id().equalsIgnoreCase(inputId))
                    .findFirst();

            if (customerOpt.isPresent()) {
                selectedCustomer = customerOpt.get();
            } else {
                System.out.println("Customer ID not found. Please try again.");
            }
        }

        // Show date/time of access
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.printf("%nSelected Customer:%n%s%n", selectedCustomer.fullDetails());
        System.out.printf("Customer Discount: %.0f%%%n", selectedCustomer.getDiscount() * 100);
        System.out.println("Accessed at: " + dateTime);

        // Membership assignment
        Member member;
        String code = selectedCustomer.customerCode().toUpperCase();

        if (code.equals("ALIGOLD016")) {
            member = new GoldMember(selectedCustomer.name());
            System.out.println("Membership: Gold Member");
        } else if (code.equals("BOBSILVER05")) {
            member = new SilverMember(selectedCustomer.name());
            System.out.println("Membership: Silver Member");
        } else {
            member = new RegularMember(selectedCustomer.name());
            System.out.println("Membership: Regular Member");
        }
        System.out.println("\nAvailable books:");
        store.searchBooks(b -> true).forEach(book -> System.out.println("- " + book.id()+"- "+ book.title()+"- "+book.category()));

        // Let customer purchase a book
        Book bookToPurchase = null;
        while (bookToPurchase == null) {
            System.out.print("\nEnter the title of the book to purchase: ");
            String bookTitle = scanner.nextLine();
            Optional<Book> foundBook = store.findBookByTitle(bookTitle);

            if (foundBook.isPresent()) {
                bookToPurchase = foundBook.get();
                double discount = selectedCustomer.getDiscount();
                double finalPrice = bookToPurchase.price() * (1 - discount);
                System.out.printf("Original Price: €%.2f%n", bookToPurchase.price());
                System.out.printf("Discounted Price for %s: €%.2f%n", selectedCustomer.name(), finalPrice);
            } else {
                System.out.println("Book not found. Please try again.");
            }
        }

        // Optionally continue with main menu
        boolean running = true;

        while (running) {
            System.out.println("""
                \n--- Bookstore Menu ---
                1. View all books
                2. Search by title
                3. Group books by category
                4. Total inventory value
                5. Count books above price
                6. Process bulk order
                0. Exit
                Choose an option:
            """);

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> store.searchBooks(b -> true).forEach(System.out::println);

                case 2 -> {
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    Optional<Book> found = store.findBookByTitle(title);
                    found.ifPresentOrElse(
                        book -> System.out.println("Found: " + book),
                        () -> System.out.println("Book not found.")
                    );
                }

                case 3 -> store.printBooksGroupedByCategory();

                case 4 -> {
                    double total = store.getTotalInventoryValue();
                    System.out.printf("Total inventory value: €%.2f%n", total);
                }

                case 5 -> {
                    System.out.print("Enter minimum price: ");
                    double price = scanner.nextDouble();
                    long count = store.countBooksAbovePrice(price);
                    System.out.println("Books above price: " + count);
                }

                case 6 -> {
                    System.out.println("Processing bulk order for all available books...");
                    try {
                        List<Book> all = store.searchBooks(Book::isAvailable);
                        store.processBulkOrders(all);
                    } catch (InterruptedException e) {
                        System.out.println("Error during order processing.");
                    }
                }

                case 0 -> {
                    System.out.println("Please enter below Customer Order Details!");
                    running = false;
                }

                default -> System.out.println("Invalid option.");
            }
        }

        // Final membership discount display
        if (member instanceof GoldMember gm) {
            System.out.println("Gold Member Discount: " + gm.getDiscount()*100 +"%");
        } else if (member instanceof RegularMember rm) {
            System.out.println("Regular Member Discount: " + rm.getDiscount()*100+"%");
        }
          else if (member instanceof SilverMember sm) {
                System.out.println("Silver Member Discount: " + sm.getDiscount()*100+"%");
        } else {
            System.out.println("No discount.");
        }
        System.out.println("\nAvailable books in Bookstore:");
        store.searchBooks(b -> true).forEach(book -> System.out.println("- " + book.id()+"- "+ book.title()+"- "+book.category()));
        store.saveInventoryToFile("All_Books.txt");
        store.readBookDataFromFile("All_Books.txt");
        scanner.close();
        System.out.println("  Goodbye!");
    }
}
