package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import database.Accounts;
import database.Assignments;


@WebServlet("/student/createGroup")
public class CreateGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int aid = Integer.parseInt(request.getParameter("id"));
		String[] unames = request.getParameterValues("member");
		if(unames==null) 
			unames = new String[0];
		
		List<Integer> studentIDs = new ArrayList<Integer>(); 
		for (String u : unames) {
			if(u.isEmpty()) continue;
			int id = Accounts.getStudentId(u);
			if (id == -1) {
				ServletUtils.forwardMessage(request, response, "/student/assignment", "error", "Student \""+u+"\" doesn't exist");
				return;
			}else if(Assignments.getGroupAssignment(id, aid) != null) {
				ServletUtils.forwardMessage(request, response, "/student/assignment", "error", "Student \""+u+"\" is already in a group for this assignment");
				return;
			}
			studentIDs.add(id);
		}
		User user = (User)request.getSession(false).getAttribute("user_info");
		studentIDs.add(user.getId());
		
		int gid = Accounts.createNewGroup(studentIDs);
		Assignments.createAssignmentGroup(aid, gid);
		ServletUtils.forwardMessage(request, response, "/student/assignment", "success", "Successful registration of group");
	}

}
