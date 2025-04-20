package bookstore.members;

public final class RegularMember extends Member {
    public RegularMember(String name) {
        super(name, 0.05); // 5% discount
    }

    @Override
    public String getMembershipType() {
        return "Regular";
    }
}
