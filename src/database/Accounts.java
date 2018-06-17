package database;

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
public final class Accounts implements ServletContextListener{
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

	public static User getUser(String username, String password) {
		try(Connection con = src.getConnection();
			PreparedStatement stm = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
			Statement stm2 = con.createStatement(); ) {
			stm.setString(1, username);
			stm.setString(2, password);
			ResultSet rs = stm.executeQuery();
			if (!rs.next()) return null;
			String role = rs.getString("role");
			ResultSet rs2 = stm2.executeQuery("SELECT * FROM "+role+"s WHERE id = "+rs.getString("id")); rs2.next();
			return new User(username, rs2.getString("first_name"), rs2.getString("last_name"), role);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
