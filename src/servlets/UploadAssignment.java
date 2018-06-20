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
import database.Assignments;

@WebServlet("/professor/uploadAssignment")
@MultipartConfig
public class UploadAssignment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		Integer maxGrade = Integer.parseInt(request.getParameter("max_grade"));
		Integer maxGroupSize = Integer.parseInt(request.getParameter("max_group_size"));
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
			int sc = Assignments.save(title, filename, content, prof.getId(), maxGrade, maxGroupSize);
			if (sc == -2) {
				ServletUtils.forwardMessage(request, response, "/professor/home.jsp", "error", "File size limit is 5MB");
			} else if (sc == -1) {
				ServletUtils.forwardMessage(request, response, "/professor/home.jsp", "error", "Error occured during upload");
			} else {
				ServletUtils.forwardMessage(request, response, "/professor/home.jsp", "success", "Upload successful");
			}
		}
	}

}
