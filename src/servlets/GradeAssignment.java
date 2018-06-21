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
       
	protected boolean validate(HttpServletRequest request, HttpServletResponse response, Integer id, Integer profId) throws ServletException, IOException {
		if(id == null) {
			ServletUtils.forwardMessage(request, response, "/professor/home.jsp", "error", "Invalid assignment");
			return false;
		}
		int owner = Assignments.getProfessorId(id);
		if(owner == -1)
			ServletUtils.forwardMessage(request, response, "/professor/home.jsp", "error", "Assignment doesn't exist");
		else if(owner != profId)
			ServletUtils.forwardMessage(request, response, "/professor/home.jsp", "error", "You aren't allowed to grade this assignment");			
		return owner==profId;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = ServletUtils.integerOrNull(request.getParameter("id"));
		Integer profId = ((User)request.getSession(false).getAttribute("user_info")).getId();
		if(!validate(request, response, id, profId))
			return;
		forwardGradesPage(request, response, id);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = ServletUtils.integerOrNull(request.getParameter("id"));
		Integer profId = ((User)request.getSession(false).getAttribute("user_info")).getId();
		if(!validate(request, response, id, profId))
			return;
		
		Integer gid = ServletUtils.integerOrNull(request.getParameter("gid"));
		if(gid==null) {
			ServletUtils.forwardMessage(request, response, "/professor/home.jsp", "error", "Invalid group");
			return;
		}
		
		Float grade = ServletUtils.floatOrNull(request.getParameter("grade"));
		if(grade==null) {
			forwardGradesPageMessage(request, response, id, "error", "Invalid grade");
			return;
		}

		int maxGrade = Assignments.getMaxGradeOnly(id);
		if(grade < 1 || grade > maxGrade) {
			forwardGradesPageMessage(request, response, id, "error", "Grade should be in range [1,"+maxGrade+"]");
			return;
		}
		
		int sc = Assignments.setGroupGrade(id, gid, grade);
		if(sc==-1) {
			forwardGradesPageMessage(request, response, id, "error", "Error occured during saving grade");
		}else{
			forwardGradesPageMessage(request, response, id, "success", "Grade set to "+grade);
		}
	}
	
	private void forwardGradesPageMessage(HttpServletRequest request, HttpServletResponse response, int id, String status, String message) throws ServletException, IOException {
		request.setAttribute("status", status);
		request.setAttribute("message", message);
		forwardGradesPage(request, response, id);
	}

	private void forwardGradesPage(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
		request.setAttribute("assignment_info", Assignments.get(id));
		request.setAttribute("students_assignments_info", Assignments.getGroupAssignments(id));
		request.getRequestDispatcher("/WEB-INF/professor_assignment_grade.jsp").forward(request, response);
	}

}
