package beans;

import java.util.Date;

public class ProfessorAssignment {
	private final int id;
	private final String title;
	private final String filename;
	private final User prof;
	private final int maxGrade;
	private final int maxGroupSize;
	private final Date created;
	private final Date deadline;

	public ProfessorAssignment(int id, String title, User prof) {
		this(id, title, null, prof, 0, 0, null, null);
	}

	public ProfessorAssignment(int id, String title, String filename, Date created, Date deadline) {
		this(id, title, filename, null, 0, 0, created, deadline);
	}
	
	public ProfessorAssignment(int id, String title, String filename, User prof, int maxGrade, int maxGroupSize, Date created, Date deadline) {
		this.id = id;
		this.title = title;
		this.filename = filename;
		this.prof = prof;
		this.maxGrade = maxGrade;
		this.maxGroupSize = maxGroupSize;
		this.created = created;
		this.deadline = deadline;
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
	public Date getCreated() {
		return created;
	}
	public Date getDeadline() {
		return deadline;
	}
}
