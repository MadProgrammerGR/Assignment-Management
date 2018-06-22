package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ProfessorAssignment;
import database.Assignments;

@WebServlet("/student/assignment")
public class StudentAssignmentDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = ServletUtils.integerOrNull(request.getParameter("id"));
		if(id==null) {
			ServletUtils.forwardMessage(request, response, "/student/home.jsp", "error", "Invalid assignment");
			return;
		}
		ProfessorAssignment pa = Assignments.get(id);
		if(pa == null) {
			ServletUtils.forwardMessage(request, response, "/student/home.jsp", "error", "Assignment doesn't exist");
		}else{
			request.setAttribute("assignment_info", pa);
			request.getRequestDispatcher("/WEB-INF/student_assignment_details.jsp").forward(request, response);				
		}
	}

}
