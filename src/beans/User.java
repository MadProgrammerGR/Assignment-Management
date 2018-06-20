package beans;

public class User {
	private int id;
	private String username;
	private String firstname;
	private String lastname;
	private String type;
	
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

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
