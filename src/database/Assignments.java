package database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import beans.User;

@WebListener
public final class Assignments implements ServletContextListener{
	private static DataSource src;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			InitialContext context = new InitialContext();
			src = (DataSource) context.lookup("java:comp/env/jdbc/postgres");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

}
