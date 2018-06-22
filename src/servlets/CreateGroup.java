package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ProfessorAssignment;
import database.Accounts;
import database.Assignments;

/**
 * Servlet implementation class CreateGroup
 */
@WebServlet("/student/createGroup")
public class CreateGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] usernames = request.getParameterValues("member");
		List<Integer> userids = new ArrayList<Integer>();
		userids.add(((beans.User)request.getAttribute("user-info")).getId());
		
		for(int i=0;i<usernames.length;i++){
			if(usernames[i]==""){
				continue;
			}
			userids.add(Accounts.getStudentId(usernames[i]));
		}
		if(userids.contains(-1)){
			ServletUtils.forwardMessage(request, response, "WEB-INF/student_assignment_details.jsp", "error", "the user dosn't exist");
		}
		int gid = Accounts.createNewGroup(userids);
		Assignments.createAssignmentGroup(((ProfessorAssignment)request.getAttribute("assignment_info")).getId(), gid);
		//redirect goes here
	}

}
