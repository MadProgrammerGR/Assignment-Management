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

@WebListener
public final class Assignments implements ServletContextListener{
	@Resource(name="jdbc/postgres") //to name tou resource sto context.xml
	private static DataSource src;

	public static boolean save(String title, String filename, InputStream stream, int profId, int maxGrade, int maxGroupSize) {		    
		try(Connection con = src.getConnection();    
			PreparedStatement ps = con.prepareStatement("INSERT INTO assignments(title, filename, file, professor_id, max_grade, max_group_size) VALUES(?, ?, ?, ?, ?, ?)"); ) {
			ps.setString(1, title);
			ps.setString(2, filename);
			ps.setBinaryStream(3, stream);
			ps.setInt(4, profId);
			ps.setInt(5, maxGrade);
			ps.setInt(6, maxGroupSize);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static List<ProfessorAssignment> getProfessorAssignments(int profId) {
		List<ProfessorAssignment> list = new ArrayList<ProfessorAssignment>();
		try (Connection con = src.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT id, title, filename FROM assignments WHERE professor_id = ?");) {
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
	
}
