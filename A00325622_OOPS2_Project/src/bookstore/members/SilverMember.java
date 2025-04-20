package bookstore.members;

public final class SilverMember extends Member {

	public SilverMember(String name) {
		super(name, 0.10);
	}

	@Override
	public String getMembershipType() {
		return "Silver";
	}

}
