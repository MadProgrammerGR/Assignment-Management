package beans;
import java.util.List;

public class GroupAssignment {
	private int assignment_id;
	private int group_id;
	private float grade;
	private String filename;
	private List<User> members;

	public GroupAssignment(int aid, int gid, String filename){
		this(aid,gid,0,filename);
	}
	
	public GroupAssignment(int aid, int gid){
		 this(aid, gid,0,null);
	}
	
	public GroupAssignment(int aid, int gid, float grade , String filename){
		setAssignment_id(aid);
		setGroup_id(gid);
		setGrade(grade);
		setFilename(filename);
	}
	
	public int getAssignment_id() {
		return assignment_id;
	}

	public void setAssignment_id(int assignment_id) {
		this.assignment_id = assignment_id;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<User> getMembers(){
		return members;
	}
	
	public void setMembers(List<User> members) {
		this.members = members;
	}

}
