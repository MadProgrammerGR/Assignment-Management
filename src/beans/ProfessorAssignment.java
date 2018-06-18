package beans;

public class ProfessorAssignment {
	private int id;
	private String title;
	private String filename;

	public ProfessorAssignment(int id, String title, String filename) {
		this.id = id;
		this.title = title;
		this.filename = filename;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
