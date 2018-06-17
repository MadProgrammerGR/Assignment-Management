package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import database.Accounts;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if(ServletUtils.isEmpty(username) || ServletUtils.isEmpty(password)) {
			ServletUtils.forwardMessage(request, response, "Empty Username or Password", "login.jsp");
			return;
		}
		
		User user = Accounts.getUser(username, password);
		if(user == null) {
			ServletUtils.forwardMessage(request, response, "Incorrect Credentials", "login.jsp");
			return;
		}
		
		//apo8hkeush tou session gia na menei logged in
		HttpSession session = request.getSession();
		session.setAttribute("user_info", user);
		session.setMaxInactiveInterval(60*60);//one hour
		//phgaine ton sto homepage tou analoga me to typo user
		response.sendRedirect(user.getType()+"/home.jsp");
	}

}
