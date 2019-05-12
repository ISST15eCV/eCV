package ecv.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import ecv.model.Address;
import ecv.model.Gender;
import ecv.model.Nationality;
import ecv.model.Usuario;

//CUANDO SE QUIERA AÑADIR LA P�GINA PRINCIPAL EN LA DEFINICION DE ESTE WEBSERVLET SE TIENE QUE QUITAR LA PARTE DE "/"
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet{
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		UsuarioDAO udao = UsuarioDAOImplementation.getInstance();
		req.getSession().setAttribute("usuario", udao.read(email));
		Usuario u = udao.read(email);
		
		
		//Tomar los datos de la fecha de nacimiento para su presentacion
		java.sql.Date date = u.getPd().getBday();
		Calendar cal = GregorianCalendar.getInstance();
		cal.clear();
		cal.setTime(date);
		String bday =Integer.toString(cal.get(Calendar.DATE));
		String bmonth =Integer.toString(cal.get(Calendar.MONTH) + 1);
		String byear =Integer.toString(cal.get(Calendar.YEAR));
		req.getSession().setAttribute("bday", bday);
		req.getSession().setAttribute("bmonth", bmonth);
		req.getSession().setAttribute("byear", byear);
		
		//Foto
		try {
			byte[] photo = u.getPhoto();
			String sphoto = Base64.getEncoder().encodeToString( photo );
			req.getSession().setAttribute( "photo" , sphoto );
		} catch(NullPointerException e) {
			System.out.print(e);
		}
		
		//Datos del genero
		Gender gen = u.getPd().getGender();
		if (gen == null) {
			gen = Gender.OTRO;
		}
		
		switch (gen) {
		case HOMBRE:
			req.getSession().setAttribute("gender", "Hombre");
			break;
		case MUJER:
			req.getSession().setAttribute("gender", "Mujer");
			break;
		case OTRO:
			req.getSession().setAttribute("gender", "Otro");
			break;
		}
		
		//Datos de la dirección y la nacionalidad
		Address addr = u.getPd().getAddress();
		Nationality nationality = u.getPd().getNationality();
		if ((addr == null) || (addr.getAddr().isEmpty() && addr.getCity().isEmpty() && addr.getRegion().isEmpty() && addr.getCountry().isEmpty())) {
			req.getSession().setAttribute("addr", "No hay dirección guardada");
		}else {
			String s =  addr.getAddr() + ", " + addr.getCity()  + ", " + addr.getRegion()  + ", " + addr.getCountry();
			req.getSession().setAttribute("addr", s);
		}
		
		if ((nationality == null) || (nationality.getAddr().isEmpty() && nationality.getCity().isEmpty() && nationality.getRegion().isEmpty() && nationality.getCountry().isEmpty())) {
			req.getSession().setAttribute("nationality", "No hay nacionalidad guardada");
		}else {
			String s =  nationality.getAddr() + ", " + nationality.getCity()  + ", " + nationality.getRegion()  + ", " + nationality.getCountry();
			req.getSession().setAttribute("nationality", s);
		}
		
		try {
			String tlf = Integer.toString(u.getPd().getTlf());
			if(tlf.isEmpty() || tlf == null) {
				req.getSession().setAttribute("tlf", "-");
			}else {
				req.getSession().setAttribute("tlf", tlf);
			}
		}catch(Exception e) {
				req.getSession().setAttribute("tlf", "-");	
		}
		
		try {
			String mobile = Integer.toString(u.getPd().getMobile());
			if(mobile.isEmpty() || mobile == null) {
				req.getSession().setAttribute("mobile", "-");
			}else {
				req.getSession().setAttribute("mobile", mobile);
			}
		}catch(Exception e){
			req.getSession().setAttribute("mobile", "-");
		}
		
		try {
			String fax = Integer.toString(u.getPd().getFax());
			if(fax.isEmpty() || fax == null) {
				req.getSession().setAttribute("fax", "-");
			}else {
				req.getSession().setAttribute("fax", fax);
			}
		}catch(Exception e) {
			req.getSession().setAttribute("fax", "-");
		}
		
		try {
			String web = u.getPd().getWeb();
			if(web.isEmpty() || web == null) {
				req.getSession().setAttribute("web", "-");
			}else {
				req.getSession().setAttribute("web", web);
			}
		}catch(Exception e) {
			req.getSession().setAttribute("web", "-");
		}

		try {
			String freeText = u.getPd().getTextFree();
			if(freeText.isEmpty() || freeText == null) {
				req.getSession().setAttribute("freeText", " 500 caracteres maximos.");
			}else {
				req.getSession().setAttribute("freeText", freeText);
			}
		}catch(Exception e) {
			req.getSession().setAttribute("freeText", "Anadir mas datos maximos.");
		}
		
		//Trabajar con los errores en la llamada
		int[] errorTag = new int[6]; 
		String errors = req.getParameter("errorTag");
		for (int i = 0; i<6; i++) {
			errorTag[i] = Integer.parseInt(errors.substring(i, i+1));
		}
		req.getSession().setAttribute("errorTag", errorTag);
		
		
		getServletContext().getRequestDispatcher( "/UserView.jsp" ).forward( req, resp );
	}
}
