package ecv.servlets;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ecv.dao.UsuarioDAO;
import ecv.dao.UsuarioDAOImplementation;
import ecv.model.Usuario;


//CUANDO SE QUIERA AÑADIR LA PÁGINA PRINCIPAL EN LA DEFINICION DE ESTE WEBSERVLET SE TIENE QUE QUITAR LA PARTE DE "/"
@WebServlet("/StudiesServlet")
public class StudiesServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String errorTag = req.getParameter("errorTag");
		UsuarioDAO udao = UsuarioDAOImplementation.getInstance();
		Usuario u = udao.read(email);
		
		//Foto
				try {
					byte[] photo = u.getPhoto();
					String sphoto = Base64.getEncoder().encodeToString( photo );
					req.getSession().setAttribute( "photo" , sphoto );
				} catch(NullPointerException e) {
					System.out.print(e);
				}
				
		req.getSession().setAttribute("errorTag", errorTag);
		req.getSession().setAttribute("usuario", u);
		getServletContext().getRequestDispatcher( "/StudiesView.jsp" ).forward( req, resp );
	}
}

