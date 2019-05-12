package ecv.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ecv.dao.LanguageDAO;
import ecv.dao.LanguageDAOImplementation;
import ecv.dao.StudiesDAO;
import ecv.dao.StudiesDAOImplementation;
import ecv.dao.TitleDAO;
import ecv.dao.TitleDAOImplementation;
import ecv.model.Language;
import ecv.model.Studies;
import ecv.model.Title;

//CUANDO SE QUIERA AÑADIR LA PÁGINA PRINCIPAL EN LA DEFINICION DE ESTE WEBSERVLET SE TIENE QUE QUITAR LA PARTE DE "/"
@WebServlet("/AddLanguageServlet")
public class AddLanguageServlet extends HttpServlet{
	
	/**
	 * Escapa caracteres indeseados
	 * 
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected String sanitize(String s) throws UnsupportedEncodingException {		
		String response= new String(s.getBytes("ISO-8859-1"),"UTF-8");
		return response;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String studiesId = req.getParameter("studiesId");
		String name = req.getParameter("name");
		String lst = req.getParameter("lst");
		String rdg = req.getParameter("rdg");
		String spk = req.getParameter("spk");
		
		int errorTag=0;
		
		StudiesDAO sdao = StudiesDAOImplementation.getInstance();
		LanguageDAO lmao = LanguageDAOImplementation.getInstance();
		Long sId = new Long(Integer.parseInt(studiesId));
		Studies studies = sdao.read(sId);
	
		if(test(name,lst,rdg,spk)) {
			if(isSomethingEmpty(name,lst,rdg,spk)) {
				errorTag=5;
			}
		}else {
			Language lang = new Language();
			lang.setName(sanitize(name));
			lang.setListeningLevel(sanitize(lst));
			lang.setReadingLevel(sanitize(rdg));
			lang.setSpeakingLevel(sanitize(spk));
			lang.setStudies(studies);
			lmao.create(lang);
		}
	
	
		resp.sendRedirect( req.getContextPath() + "/StudiesServlet?email=" + email +"&errorTag=" + errorTag);
	
	
	
	}
	private boolean test(String a, String b, String c, String d) {
		return (a == null || b == null || c == null || d == null || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty());
	}
	
	private boolean isSomethingEmpty(String a, String b, String c, String d) {
		return ((!a.isEmpty() || !b.isEmpty() || !c.isEmpty() || !d.isEmpty())!=(!a.isEmpty() && !b.isEmpty() && !c.isEmpty() && !d.isEmpty()));
	}
}
