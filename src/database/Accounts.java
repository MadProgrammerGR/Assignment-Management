package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import beans.User;

@WebListener
public final class Accounts implements ServletContextListener{
	@Resource(name="jdbc/postgres") //to name tou resource sto context.xml
	private static DataSource src;

	public static User getUser(String username, String password) {
		try(Connection con = src.getConnection();
			PreparedStatement stm = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
			Statement stm2 = con.createStatement(); ) {
			stm.setString(1, username);
			stm.setString(2, password);
			ResultSet rs = stm.executeQuery();
			if (!rs.next()) return null;
			String role = rs.getString("role");
			int id = rs.getInt("id");
			ResultSet rs2 = stm2.executeQuery("SELECT * FROM "+role+"s WHERE id = "+id); rs2.next();
			return new User(id, username, rs2.getString("first_name"), rs2.getString("last_name"), role);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int createNewGroup(List<Integer> userids) {
		try(Connection con = src.getConnection()) {
			PreparedStatement stm1 = con.prepareStatement("SELECT MAX(group_id)+1 FROM group_members");
			int gid = stm1.executeQuery().getInt(1);
			String sqlinsert = "INSERT INTO group_members VALUES (" + gid + ", ?)";
			for (int i = 1; i < userids.size(); i++)
				sqlinsert += ",(" + gid + ",?)";
			PreparedStatement stm2 = con.prepareStatement(sqlinsert);
			for (int i = 0; i < userids.size(); i++) {
				stm2.setInt(i + 1, userids.get(i).intValue());
			}
			stm2.executeUpdate();
			return gid;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int getStudentId(String username){
		try(Connection con = src.getConnection();
				PreparedStatement stm = con.prepareStatement("SELECT id FROM users WHERE username = ? AND role = 'student'"); ) {
			stm.setString(1, username);
			ResultSet rs = stm.executeQuery();
			if(rs.next()) return rs.getInt("id");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return -1;
	}
	
	public static List<User> getGroupMembers(int groupid){
		List<User> members = new ArrayList<User>();
		try(Connection con = src.getConnection();
				PreparedStatement stm = con.prepareStatement("SELECT u.username, s.first_name, s.last_name"
						+ " FROM group_members g INNER JOIN students s ON g.student_id = s.id"
						+ "  INNER JOIN users u ON s.id = u.id WHERE g.group_id = ?");){
			stm.setInt(1, groupid);
			ResultSet rs = stm.executeQuery();
			while(rs.next()){
				members.add(new User(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return members;
	}

	public static boolean belongsToGroup(int studentId, int groupId) {
		try(Connection con = src.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT 1 FROM group_members"
						+ " WHERE group_id = ? AND student_id = ?");){
			ps.setInt(1, groupId);
			ps.setInt(2, studentId);
			return ps.executeQuery().next();
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
