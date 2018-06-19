package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			session.invalidate();
			ServletUtils.forwardMessage(request, response, "login.jsp", "success", "You have been logged out successfully, bye!");
		} catch (java.lang.IllegalStateException e) {
			ServletUtils.forwardMessage(request, response, "login.jsp", "error", "Already invalidated session");
		}
		return;
	}

}
