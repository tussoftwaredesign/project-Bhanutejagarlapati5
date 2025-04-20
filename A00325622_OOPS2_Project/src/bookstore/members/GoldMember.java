package bookstore.members;

public final class GoldMember extends Member {
    public GoldMember(String name) {
        super(name, 0.20); // 20% discount
    }

    @Override
    public String getMembershipType() {
        return "Gold";
    }
}
