package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Assignments;

@WebServlet("/assignment/download")
public class DownloadAssignment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int assignmentId = Integer.parseInt(request.getParameter("id"));
		InputStream fileStream = Assignments.getAssignmentFileData(assignmentId);
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

}
