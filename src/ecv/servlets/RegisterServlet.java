package ecv.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ecv.dao.UsuarioDAO;
import ecv.dao.UsuarioDAOImplementation;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int errorTag = Integer.parseInt(req.getParameter("errorTag"));	
		req.getSession().setAttribute( "errorTag", errorTag);
		getServletContext().getRequestDispatcher( "/RegisterView.jsp" ).forward( req, resp );
	}
}
