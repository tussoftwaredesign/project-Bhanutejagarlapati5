package bookstore.models;

public record Book(String id, String title, String author, BookCategory category, double price, int stock) {
    public boolean isAvailable() {
        return stock > 0;
    }
    public String getTitle() {
        return title;
    }


    public double getPrice() {
        return price;
    }

}


    