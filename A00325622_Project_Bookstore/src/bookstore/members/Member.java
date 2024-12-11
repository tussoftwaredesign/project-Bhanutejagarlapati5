package bookstore.members;

public sealed abstract class Member permits GoldMember, RegularMember {
    private final String name;
    private final String email;

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }

    public abstract String getMemberDetails();
}
