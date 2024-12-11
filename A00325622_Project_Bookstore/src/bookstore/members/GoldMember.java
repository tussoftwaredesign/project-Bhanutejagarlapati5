package bookstore.members;

public final class GoldMember extends Member {
    private final double discount;

    public GoldMember(String name, String email, double discount) {
        super(name, email);
        this.discount = discount;
    }

    @Override
    public String getMemberDetails() {
        return "Gold Member: " + getName() + " (Discount: " + (discount * 100) + "%)";
    }
}
