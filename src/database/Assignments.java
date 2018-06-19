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

import beans.ProfessorAssignment;
import beans.User;

@WebListener
public final class Assignments implements ServletContextListener{
	@Resource(name="jdbc/postgres") //to name tou resource sto context.xml
	private static DataSource src;

	public static boolean save(String title, String filename, InputStream stream, int profId, int maxGrade, int maxGroupSize, StringBuilder returnMsg) {
		try(Connection con = src.getConnection();    
			PreparedStatement ps = con.prepareStatement("INSERT INTO assignments(title, filename, file, professor_id, max_grade, max_group_size) VALUES(?, ?, ?, ?, ?, ?)"); ) {
			ps.setString(1, title);
			ps.setString(2, filename);
			ps.setBinaryStream(3, stream);
			ps.setInt(4, profId);
			ps.setInt(5, maxGrade);
			ps.setInt(6, maxGroupSize);
			ps.executeUpdate();
			returnMsg.append("Upload successful");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			if(e.getMessage().contains("bytea_5mb_check")) {
				returnMsg.append("File size limit is 5MB");
			} else {
				returnMsg.append("Error occured during upload");
			}
			return false;
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
	
	public static InputStream getAssignmentFileData(int id) {
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
	
}
