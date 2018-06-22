package beans;

public class User {
	private final int id;
	private final String username;
	private final String firstname;
	private final String lastname;
	private final String type;
	
	public User(int id, String username, String firstname, String lastname, String type) {
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.type = type;
	}
	
	public User(String firstname, String lastname) {
		this(0, null, firstname, lastname, null);
	}

	public User(String username, String firstname, String lastname) {
		this(0, username, firstname, lastname, null);
	}

	public String getUsername() {
		return username;
	}
	public String getFirstname() {
		return firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public String getType() {
		return type;
	}
	public int getId() {
		return id;
	}
}
