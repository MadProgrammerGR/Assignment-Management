package filters;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import servlets.ServletUtils;

@WebFilter("/*")
public class Authorization implements Filter {
	private static final Set<String> freeUrls = new HashSet<String>();
	
	public void init(FilterConfig conf){ 
		freeUrls.add("/");
		freeUrls.add("/login");
		freeUrls.add("/login.jsp");
	}
	
	//ti vlepei enas syndedemenos user typoy type (professor h student)
	private boolean isAuthorized(String type, String url) {
		return url.startsWith("/"+type) || url.startsWith("/assignment/download") || url.equals("/logout");
	}
	
	private boolean isResource(String url) {
		return url.startsWith("/css") || url.startsWith("/images");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String reqUrl = req.getServletPath().toLowerCase();
		
		//resources tzampe (epitrepetai to cache)
		if(isResource(reqUrl)) {
			chain.doFilter(request, response);
			return;
		}//sta ypoloipa oxi (logout fix)
		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		res.setHeader("Pragma", "no-cache");
		res.setDateHeader("Expires", 0);
		
		HttpSession session = req.getSession(false);
		User user = session!=null ? (User) session.getAttribute("user_info") : null;
		
		if(user == null) { //mh syndedemenos
			if(freeUrls.contains(reqUrl)){ //orato url
				chain.doFilter(request, response); 
			}else{//aorato url :P
				ServletUtils.forwardMessage(req, res, "login.jsp", "error", "Please login first.");
			}
			return;
		}
		//redirect user ama grapsei la8os to home url tou
		if(reqUrl.equals("/login.jsp") || reqUrl.equals("/home.jsp")) {
			res.sendRedirect(user.getType()+"/home.jsp");
			return;
		}
		// You shall no pass!
		if(!isAuthorized(user.getType(), reqUrl)) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		// You shall pass!
		chain.doFilter(request, response);
	}

}
