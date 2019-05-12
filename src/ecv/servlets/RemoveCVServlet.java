package ecv.servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ecv.dao.CVDAO;
import ecv.dao.CVDAOImplementation;
import ecv.dao.CareerDAO;
import ecv.dao.CareerDAOImplementation;
import ecv.dao.CareerItemDAO;
import ecv.dao.CareerItemDAOImplementation;
import ecv.dao.CurrentJobDAO;
import ecv.dao.CurrentJobDAOImplementation;
import ecv.dao.PhdDAO;
import ecv.dao.PhdDAOImplementation;
import ecv.dao.TitleDAO;
import ecv.dao.TitleDAOImplementation;
import ecv.dao.UsuarioDAO;
import ecv.dao.UsuarioDAOImplementation;
import ecv.model.CV;
import ecv.model.Career;
import ecv.model.CareerItem;
import ecv.model.CurrentJob;
import ecv.model.Phd;
import ecv.model.Title;
import ecv.model.Usuario;

//CUANDO SE QUIERA AÑADIR LA PÁGINA PRINCIPAL EN LA DEFINICION DE ESTE WEBSERVLET SE TIENE QUE QUITAR LA PARTE DE "/"
@WebServlet("/RemoveCVServlet")
public class RemoveCVServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cvId = req.getParameter("cvId");
		String email = req.getParameter("email");
		CVDAO cvdao = CVDAOImplementation.getInstance();
		Long cId = new Long(Integer.parseInt(cvId));
		CV cv = cvdao.read(cId);
		cvdao.delete(cv);
		
		resp.sendRedirect( req.getContextPath() + "/CVServlet?email=" + email);
	}
}
