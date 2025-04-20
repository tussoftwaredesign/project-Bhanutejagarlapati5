package bookstore.models;

public record ImmutableAddress(String street, String city, String postalCode) {
    public String formatted() {
        return "%s, %s %s".formatted(street, city, postalCode);
    }
}
