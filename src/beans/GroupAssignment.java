package beans;
import java.util.List;

public class GroupAssignment {
	private final int assignment_id;
	private final int group_id;
	private final float grade;
	private final String filename;
	private final List<User> members;

	public GroupAssignment(int aid, int gid, float grade, String filename, List<User> members) {
		this.assignment_id = aid;
		this.group_id = gid;
		this.grade = grade;
		this.filename = filename;
		this.members = members;
	}

	public int getAssignment_id() {
		return assignment_id;
	}
	public int getGroup_id() {
		return group_id;
	}
	public float getGrade() {
		return grade;
	}
	public String getFilename() {
		return filename;
	}
	public List<User> getMembers(){
		return members;
	}
}
