package bookstore.models;

public record Customer(String id, String name, ImmutableAddress address, String customerCode) {

    public String fullDetails() {
        return "Customer: %s (%s)\nCode: %s\nAddress: %s"
            .formatted(name, id, customerCode, address.formatted());
    }

    public double getDiscount() {
        return switch (customerCode.toUpperCase()) {
            case "ALIGOLD016" -> 0.20;
            case "BOBSILVER05" -> 0.10;
            case "CHARLINOR200" -> 0.05;
            default -> 0.0;
        };
    }
}
