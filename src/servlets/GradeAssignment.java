package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import database.Assignments;

@WebServlet("/professor/grade")
public class GradeAssignment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id;
		try{
			id = Integer.parseInt(request.getParameter("id"));
		}catch(NumberFormatException e) {
			ServletUtils.forwardMessage(request, response, "/professor/home.jsp", "error", "Invalid assignment");
			return;
		}
		
		int profId = ((User)request.getSession(false).getAttribute("user_info")).getId();
		int owner = Assignments.getProfessorId(id);
		if(owner == -1) {
			ServletUtils.forwardMessage(request, response, "/professor/home.jsp", "error", "Assignment doesn't exist");
		}else if(owner != profId) {
			ServletUtils.forwardMessage(request, response, "/professor/home.jsp", "error", "You aren't allowed to grade this assignment");			
		}else{
			request.setAttribute("assignment_info", Assignments.get(id));
			// TODO fetch student assignments on a list and pass it for rendering
			request.getRequestDispatcher("/WEB-INF/professor_assignment_grade.jsp").forward(request, response);				
		}
	}

}
