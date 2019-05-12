package ecv.servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.pdf.fonts.otf.Language;

import ecv.dao.CareerDAO;
import ecv.dao.CareerDAOImplementation;
import ecv.dao.CareerItemDAO;
import ecv.dao.CareerItemDAOImplementation;
import ecv.dao.CurrentJobDAO;
import ecv.dao.CurrentJobDAOImplementation;
import ecv.dao.LanguageDAO;
import ecv.dao.LanguageDAOImplementation;
import ecv.dao.PhdDAO;
import ecv.dao.PhdDAOImplementation;
import ecv.dao.TitleDAO;
import ecv.dao.TitleDAOImplementation;
import ecv.dao.UsuarioDAO;
import ecv.dao.UsuarioDAOImplementation;
import ecv.model.Career;
import ecv.model.CareerItem;
import ecv.model.CurrentJob;
import ecv.model.Phd;
import ecv.model.Title;
import ecv.model.Usuario;

//CUANDO SE QUIERA AÑADIR LA PÁGINA PRINCIPAL EN LA DEFINICION DE ESTE WEBSERVLET SE TIENE QUE QUITAR LA PARTE DE "/"
@WebServlet("/RemoveLanguageServlet")
public class RemoveLanguageServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("langId");
		String email = req.getParameter("email");
		LanguageDAO lmao = LanguageDAOImplementation.getInstance();
		Long lId = new Long(Integer.parseInt(id));
		ecv.model.Language lang = lmao.read(lId);
		lmao.delete(lang);
		
		resp.sendRedirect( req.getContextPath() + "/StudiesServlet?email=" + email +"&errorTag=0");
	}
}
