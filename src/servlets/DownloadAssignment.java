package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import database.Accounts;
import database.Assignments;

@WebServlet("/assignment/download")
public class DownloadAssignment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer assignmentId = integerOrNull(request.getParameter("id"));
		if(assignmentId == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		Integer groupId = integerOrNull(request.getParameter("gid"));
		if(groupId == null) {
			handleProfessorAssignment(request, response, assignmentId);
		}else{
			handleGroupAssignment(request, response, assignmentId, groupId);
		}
	}

	private void handleProfessorAssignment(HttpServletRequest request, HttpServletResponse response, Integer id) throws IOException {
		User user = (User)request.getSession(false).getAttribute("user_info");
		if(user.getType().equals("student")) {
			serveFileOr404(response, Assignments.getDescriptionFileData(id));
		}else if(user.getType().equals("professor")) {
			int owner = Assignments.getProfessorId(id);
			if(owner == -1) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}else if(owner != user.getId()) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}else{
				serveFileOr404(response, Assignments.getDescriptionFileData(id));
			}
		}
	}

	private void handleGroupAssignment(HttpServletRequest request, HttpServletResponse response, Integer assignmentId, Integer groupId) throws IOException {
		User user = (User)request.getSession(false).getAttribute("user_info");
		if(user.getType().equals("student")) {
			if(Accounts.belongsToGroup(user.getId(), groupId)) {
				serveFileOr404(response, Assignments.getGroupAssignmentFileData(assignmentId, groupId));
			}else{
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		}else if(user.getType().equals("professor")) {
			int owner = Assignments.getProfessorId(assignmentId);
			if(owner == -1) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}else if(owner != user.getId()) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}else{
				serveFileOr404(response, Assignments.getGroupAssignmentFileData(assignmentId, groupId));
			}
		}
	}
	
	private void serveFileOr404(HttpServletResponse response, InputStream fileStream) throws IOException {
		if(fileStream == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}else{
                 OutputStream out = response.getOutputStream();
                 byte[] buffer = new byte[2048]; //2kB
                 int bytesRead;
                 while((bytesRead = fileStream.read(buffer)) != -1) {
            	     out.write(buffer, 0, bytesRead);
                 }
		}
	}

	private Integer integerOrNull(String s) {
		try{
			return Integer.parseInt(s);
		}catch(NumberFormatException e) {
			return null;
		}
	}

}
