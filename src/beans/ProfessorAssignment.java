package beans;

public class ProfessorAssignment {
	private final int id;
	private final String title;
	private final String filename;
	private final User prof;
	private final int maxGrade;
	private final int maxGroupSize;
	
	public ProfessorAssignment(int id, String title, String filename) {
		this(id, title, filename, null, 0, 0);
	}
	
	public ProfessorAssignment(int id, String title, User prof) {
		this(id, title, null, prof, 0, 0);
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
	public String getTitle() {
		return title;
	}
	public String getFilename() {
		return filename;
	}
	public User getProf() {
		return prof;
	}
	public int getMaxGrade() {
		return maxGrade;
	}
	public int getMaxGroupSize() {
		return maxGroupSize;
	}
}
