package bookstore.services;

public interface BookStoreOperations {
    void addBook(bookstore.models.Book... books);

    default void printStoreMessage() {
        System.out.println("Welcome to the Bookstore!");
    }

    static void printStaticInfo() {
        System.out.println("Static method in an interface!");
    }
}
