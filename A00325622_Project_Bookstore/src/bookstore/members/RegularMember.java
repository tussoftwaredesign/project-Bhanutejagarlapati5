package bookstore.members;

public final class RegularMember extends Member {
    public RegularMember(String name, String email) {
        super(name, email);
    }

    @Override
    public String getMemberDetails() {
        return "Regular Member: " + getName();
    }
}
