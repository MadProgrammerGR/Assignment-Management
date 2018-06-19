package beans;

public class ProfessorAssignment {
	private int id;
	private String title;
	private String filename;
	private User prof;
	private int maxGrade;
	private int maxGroupSize;
	
	public ProfessorAssignment(int id, String title, String filename) {
		this(id, title, filename, null);
	}
	
	public ProfessorAssignment(int id, String title, User prof) {
		this(id, title, null, prof);
	}
	
	public ProfessorAssignment(int id, String title, String filename, User prof) {
		this(id, title, filename, prof, 0, 0);
	}

	public ProfessorAssignment(int id, String title, String filename, User prof, int maxGrade, int maxGroupSize) {
		this.id = id;
		this.title = title;
		this.filename = filename;
		this.prof = prof;
		this.maxGrade = maxGrade;
		this.maxGroupSize = maxGroupSize;
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
	public User getProf() {
		return prof;
	}
	public void setProf(User prof) {
		this.prof = prof;
	}
	public int getMaxGrade() {
		return maxGrade;
	}
	public void setMaxGrade(int maxGrade) {
		this.maxGrade = maxGrade;
	}
	public int getMaxGroupSize() {
		return maxGroupSize;
	}
	public void setMaxGroupSize(int maxGroupSize) {
		this.maxGroupSize = maxGroupSize;
	}
}
