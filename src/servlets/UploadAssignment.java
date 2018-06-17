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
		    int maxGrade = Integer.parseInt(request.getParameter("max_grade"));
		    int maxGroupSize = Integer.parseInt(request.getParameter("max_group_size"));
		    Part filePart = request.getPart("description_file");
		    String filename = filePart.getSubmittedFileName();
		    InputStream content = filePart.getInputStream();
		    
		    User prof = (User)request.getSession().getAttribute("user_info");
		    if (Assignments.save(title, filename, content, prof.getId(), maxGrade, maxGroupSize))
			    ServletUtils.forwardMessage(request, response, "Upload successful", "/professor/home.jsp");
		    else
			    ServletUtils.forwardMessage(request, response, "Error occured during upload", "/professor/home.jsp");
	}

}
