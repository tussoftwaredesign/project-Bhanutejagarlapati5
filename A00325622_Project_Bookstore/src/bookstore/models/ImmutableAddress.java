package bookstore.models;

public final class ImmutableAddress {
    private final String street;
    private final String city;
    private final String postalCode;

    public ImmutableAddress(String street, String city, String postalCode) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getPostalCode() { return postalCode; }

    @Override
    public String toString() {
        return String.format("%s, %s - %s", street, city, postalCode);
    }
}
