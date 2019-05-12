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
import ecv.dao.UsuarioDAO;
import ecv.dao.UsuarioDAOImplementation;
import ecv.model.Address;
import ecv.model.CV;
import ecv.model.Gender;
import ecv.model.Nationality;
import ecv.model.Usuario;

//CUANDO SE QUIERA AÑADIR LA PÁGINA PRINCIPAL EN LA DEFINICION DE ESTE WEBSERVLET SE TIENE QUE QUITAR LA PARTE DE "/"
@WebServlet("/DownloadCVServlet")
public class DownloadCVServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cvId = req.getParameter("cvId");
		CVDAO cvdao = CVDAOImplementation.getInstance();
		Long cId = new Long(Integer.parseInt(cvId));
		CV cv = cvdao.read(cId);
		System.out.println("-------------" + cv.getDocument().length + "-------------------");
		resp.setContentLength(cv.getDocument().length);
		resp.getOutputStream().write(cv.getDocument());
	}
}
