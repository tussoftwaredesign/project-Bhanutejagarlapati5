package bookstore.members;

public sealed abstract class Member permits RegularMember, GoldMember, SilverMember {
    protected final String name;
    protected final double discount;

    protected Member(String name, double discount) {
        this.name = name;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public double getDiscount() {
        return discount;
    }

    public abstract String getMembershipType();
}
