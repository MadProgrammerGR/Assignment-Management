package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ServletUtils {
	public static void forwardMessage(HttpServletRequest request, HttpServletResponse response, String page, String status, String message) throws ServletException, IOException {
		request.setAttribute("status", status);
		request.setAttribute("message", message);
		request.getRequestDispatcher(page).forward(request, response);
	}
	
	public static boolean isEmpty(String input) {
		return input == null || input.trim().isEmpty();
	}
	
	public static Integer integerOrNull(String s) {
		try{
			return Integer.parseInt(s);
		}catch(NumberFormatException e) {
			return null;
		}
	}
}
