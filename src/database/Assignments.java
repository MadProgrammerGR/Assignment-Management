package database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import beans.GroupAssignment;
import beans.ProfessorAssignment;
import beans.User;

@WebListener
public final class Assignments implements ServletContextListener{
	@Resource(name="jdbc/postgres") //to name tou resource sto context.xml
	private static DataSource src;

	public static int save(String title, String filename, InputStream stream, int profId, int maxGrade, int maxGroupSize) {
		try(Connection con = src.getConnection();    
			PreparedStatement ps = con.prepareStatement("INSERT INTO assignments(title, filename, file, professor_id, max_grade, max_group_size) VALUES(?, ?, ?, ?, ?, ?)"); ) {
			ps.setString(1, title);
			ps.setString(2, filename);
			ps.setBinaryStream(3, stream);
			ps.setInt(4, profId);
			ps.setInt(5, maxGrade);
			ps.setInt(6, maxGroupSize);
			ps.executeUpdate();
			return 0;
		} catch (SQLException e) {
			if(e.getMessage().contains("bytea_5mb_check"))
				return -2;
			e.printStackTrace();
			return -1;
		}
	}
	
	public static int saveGroupAssignment(int assignmentId, int groupId, InputStream stream, String filename) {
		try(Connection con = src.getConnection();    
				PreparedStatement ps = con.prepareStatement("INSERT INTO assignment_groups(assignment_id, group_id, file, filename) VALUES(?, ?, ?, ?)"); ) {
			ps.setInt(1, assignmentId);
			ps.setInt(2, groupId);
			ps.setBinaryStream(3, stream);
			ps.setString(4, filename);
			ps.executeUpdate();
			return 0;
		} catch (SQLException e) {
			if(e.getMessage().contains("bytea_5mb_check"))
				return -2;
			e.printStackTrace();
			return -1;
		}
	}
	
	public static ProfessorAssignment get(int id) {
		try (Connection con = src.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT a.title, a.filename, a.max_grade, a.max_group_size, p.first_name, p.last_name"
						+ " FROM assignments a INNER JOIN professors p on a.professor_id=p.id"
						+ " WHERE a.id = ?");) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				User prof = new User(rs.getString(5), rs.getString(6));
				return new ProfessorAssignment(id, rs.getString(1), rs.getString(2), prof, rs.getInt(3), rs.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<ProfessorAssignment> getAll() {
		List<ProfessorAssignment> list = new ArrayList<ProfessorAssignment>();
		try (Connection con = src.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT a.id, a.title, p.first_name, p.last_name"
						+ " FROM assignments a INNER JOIN professors p on a.professor_id=p.id ORDER BY a.title");) {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				User p = new User(rs.getString(3), rs.getString(4));
				ProfessorAssignment pa = new ProfessorAssignment(rs.getInt(1), rs.getString(2), p);
				list.add(pa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<ProfessorAssignment> getFromProfessor(int profId) {
		List<ProfessorAssignment> list = new ArrayList<ProfessorAssignment>();
		try (Connection con = src.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT id, title, filename FROM assignments WHERE professor_id = ? ORDER BY title");) {
			ps.setInt(1, profId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ProfessorAssignment pa = new ProfessorAssignment(rs.getInt(1), rs.getString(2), rs.getString(3));
				list.add(pa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static InputStream getDescriptionFileData(int id) {
		try (Connection con = src.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT file FROM assignments WHERE id = ?");) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				return rs.getBinaryStream("file");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static InputStream getGroupAssignmentFileData(int assignmentId, int groupId) {
		try (Connection con = src.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT file FROM assignment_groups"
						+ " WHERE assignment_id = ? AND group_id = ?");) {
			ps.setInt(1, assignmentId);
			ps.setInt(2, groupId);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				return rs.getBinaryStream("file");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int getProfessorId(int id) {
		try (Connection con = src.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT professor_id FROM assignments WHERE id = ?");) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static GroupAssignment getGroupAssignment(int group_id,int assignment_id) {
		try (Connection con = src.getConnection();
				PreparedStatement ps = con.prepareStatement(
						"SELECT student_id, group_id, assignment_id, filename, grade FROM group_members "+
				"NATURAL JOIN assignment_groups WHERE student_id = ? AND assignment_id = ?");)
		{
			ps.setInt(1, group_id);
			ps.setInt(2 ,assignment_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int gid = rs.getInt("group_id");
				int aid = rs.getInt("assignment_id");
				String fn;
				int grade;
				try{
					fn = rs.getString("filename");
				} catch(Exception e) {
					fn = " ";
				}
				try{
					grade = rs.getInt("grade");
				}catch(Exception e){
					grade = -1;
				}
				return new GroupAssignment(aid,gid,grade,fn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<GroupAssignment> getGroupAssignments(int assignment_id){
		List<GroupAssignment> list = new ArrayList<GroupAssignment>();
		try (Connection con = src.getConnection();
				PreparedStatement ps = con.prepareStatement(
					"SELECT group_id, filename, grade FROM assignment_groups WHERE assignment_id = ?");) {
			ps.setInt(1, assignment_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				GroupAssignment g = new GroupAssignment(assignment_id, rs.getInt("group_id"), rs.getFloat("grade"), rs.getString("filename"));
				g.setMembers(Accounts.getGroupMembers(rs.getInt("group_id")));
				list.add(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static int setGroupGrade(int assignment_id, int group_id, float grade) {
		try(Connection con = src.getConnection();
				PreparedStatement ps = con.prepareStatement(
						"UPDATE assignment_groups SET grade = ? WHERE assignment_id = ? AND group_id = ?"); ) {
				ps.setInt(3, group_id);
				ps.setInt(2, assignment_id);
				ps.setFloat(1, grade);
				return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
}
