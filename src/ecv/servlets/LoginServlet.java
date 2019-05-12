package ecv.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

//CUANDO SE QUIERA AÑADIR LA PÁGINA PRINCIPAL EN LA DEFINICION DE ESTE WEBSERVLET SE TIENE QUE QUITAR LA PARTE DE "/"
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int errorTag = 0;
		if (req.getParameter("errorTag") != null) {
			errorTag = Integer.parseInt(req.getParameter("errorTag"));
		}
		req.getSession().setAttribute( "errorTag", errorTag);
		getServletContext().getRequestDispatcher( "/LoginView.jsp" ).forward( req, resp );
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String pass = req.getParameter("password");
		Subject currentUser = SecurityUtils.getSubject();
		if( !currentUser.isAuthenticated()) {
			try {
				if((email.isEmpty()) || (email == null) || (pass.isEmpty()) || (pass == null)) {
					throw new Exception("2");
				}
				UsernamePasswordToken token = new UsernamePasswordToken(email, pass);
				currentUser.login(token);
				if(currentUser.hasRole("admin"))
					resp.sendRedirect( req.getContextPath() + "/AdminServlet" );
				else
					resp.sendRedirect( req.getContextPath() + "/UserServlet?email=" + email + "&errorTag=000000" );
			}catch(Exception e) {
				String msg = e.getMessage();
				String code1 = "2";
				if(msg.equals(code1)){
					resp.sendRedirect( req.getContextPath() + "/LoginServlet?errorTag=2" );
				}else {
					resp.sendRedirect( req.getContextPath() + "/LoginServlet?errorTag=1" );
				}
			}
		}else{
			resp.sendRedirect( req.getContextPath() + "/LoginServlet?errorTag=0" );
		}
	}
}
