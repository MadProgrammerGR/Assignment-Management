package servlets;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import beans.User;
import database.Accounts;
import database.Assignments;

@WebServlet({"/professor/uploadAssignment","/student/uploadAssignment"})
@MultipartConfig
public class UploadAssignment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getServletPath();
		if(reqUrl.startsWith("/professor")) {
			handleProfessorUpload(request, response);
		}else{
			handleStudentGroupUpload(request, response);
		}
	}

	private void handleProfessorUpload(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String title = request.getParameter("title");
		Integer maxGrade = ServletUtils.integerOrNull(request.getParameter("max_grade"));
		Integer maxGroupSize = ServletUtils.integerOrNull(request.getParameter("max_group_size"));
		Part filePart = request.getPart("description_file");
		String filename = filePart.getSubmittedFileName();
		if(ServletUtils.isEmpty(title) || ServletUtils.isEmpty(filename) || maxGrade==null || maxGroupSize==null){
			ServletUtils.forwardMessage(request, response, "/professor/home.jsp", "error", "Some field(s) are missing and are required");
		}else if(maxGrade < 1 || maxGrade > 10) {
			ServletUtils.forwardMessage(request, response, "/professor/home.jsp", "error", "Max grade should be in range [1,10]");
		}else if(maxGroupSize < 1 || maxGroupSize > 10) {
			ServletUtils.forwardMessage(request, response, "/professor/home.jsp", "error", "Max group size should be in range [1,10]");
		}else{
			InputStream content = filePart.getInputStream();
			User prof = (User) request.getSession().getAttribute("user_info");
			int sc = Assignments.save(title, filename, content, prof.getId(), maxGrade, maxGroupSize, request.getParameter("deadline"));
			if (sc == -2) {
				ServletUtils.forwardMessage(request, response, "/professor/home.jsp", "error", "File size limit is 5MB");
			} else if (sc == -1) {
				ServletUtils.forwardMessage(request, response, "/professor/home.jsp", "error", "Error occured during upload");
			} else {
				ServletUtils.forwardMessage(request, response, "/professor/home.jsp", "success", "Upload successful");
			}
		}
	}
	
	private void handleStudentGroupUpload(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Integer assignmentId = ServletUtils.integerOrNull(request.getParameter("id"));
		Integer groupId = ServletUtils.integerOrNull(request.getParameter("gid"));
		Part filePart = request.getPart("file");
		String filename = filePart.getSubmittedFileName();
		if(ServletUtils.isEmpty(filename) || assignmentId==null || groupId==null){
			ServletUtils.forwardMessage(request, response, "/student/assignment", "error", "Some field(s) are missing and are required");
			return;
		}
		User student = (User)request.getSession(false).getAttribute("user_info");
		if(!Accounts.belongsToGroup(student.getId(), groupId)) {
			ServletUtils.forwardMessage(request, response, "/student/assignment", "error", "You dont belong in this group! stop trying to hack your classmates!");
			return;
		}
		int sc = Assignments.saveGroupAssignment(assignmentId, groupId, filePart.getInputStream(), filename);
		if (sc == -2) {
			ServletUtils.forwardMessage(request, response, "/student/assignment", "error", "File size limit is 5MB");
		} else if (sc == -1) {
			ServletUtils.forwardMessage(request, response, "/student/assignment", "error", "Error occured during upload");
		} else {
			ServletUtils.forwardMessage(request, response, "/student/assignment", "success", "Upload successful");
		}
	}

}
