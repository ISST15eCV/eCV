package ecv.servlets;

import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ecv.dao.UsuarioDAO;
import ecv.dao.UsuarioDAOImplementation;
import ecv.model.Usuario;


//CUANDO SE QUIERA AÑADIR LA PÁGINA PRINCIPAL EN LA DEFINICION DE ESTE WEBSERVLET SE TIENE QUE QUITAR LA PARTE DE "/"
@WebServlet("/CareerServlet")
public class CareerServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String errorTag = req.getParameter("errorTag");
		UsuarioDAO udao = UsuarioDAOImplementation.getInstance();
		Usuario u = udao.read(email);
		
		try {
		java.sql.Date date = u.getCareer().getCurrentJob().getStart();
		Calendar cal = GregorianCalendar.getInstance();
		cal.clear();
		cal.setTime(date);
		String day =Integer.toString(cal.get(Calendar.DATE));
		String month =Integer.toString(cal.get(Calendar.MONTH) + 1);
		String year =Integer.toString(cal.get(Calendar.YEAR));
		req.getSession().setAttribute("day", day);
		req.getSession().setAttribute("month", month);
		req.getSession().setAttribute("year", year);
		}catch(Exception e) {
			
		}
		
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
		
		getServletContext().getRequestDispatcher( "/CareerView.jsp" ).forward( req, resp );
	}
}

