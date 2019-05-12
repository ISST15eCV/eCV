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

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;

import ecv.dao.CareerDAO;
import ecv.dao.CareerDAOImplementation;
import ecv.dao.PersonalDataDAO;
import ecv.dao.PersonalDataDAOImplementation;
import ecv.dao.StudiesDAO;
import ecv.dao.StudiesDAOImplementation;
import ecv.dao.UsuarioDAO;
import ecv.dao.UsuarioDAOImplementation;
import ecv.model.Career;
import ecv.model.PersonalData;
import ecv.model.Studies;
import ecv.model.Usuario;

@WebServlet("/CreateUsuarioServlet")
public class CreateUsuarioServlet extends HttpServlet{

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
	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String name = req.getParameter("name");
		String surname = req.getParameter("sname");
		String nif =  req.getParameter("nif");
		String bday =  req.getParameter("bday");
		String bmonth =  req.getParameter("bmonth");
		String byear =  req.getParameter("byear");
		String email = req.getParameter("email");
		String pass = req.getParameter("password");
		String pass2 = req.getParameter("password2");
		

		Usuario user = new Usuario();					//Se crean los elementos elementos que formarán parte de los datos del usuario.
		PersonalData pd = new PersonalData();
		Career career = new Career();
		Studies studies = new Studies();
		
		Subject currentUser = SecurityUtils.getSubject();
		if( !currentUser.isAuthenticated()) {
			try {
				//Comprobamos que los elementos no esten vacios o el formato sea incorrecto
				if (nullOrEmpty(name,surname,nif,email,pass)) {
					throw new Exception("null");
				}
				
				java.sql.Date date = getDateFromData(byear, bmonth, bday); 
				
				//Almacenamos los datos del usuario en el objeto Usuario y en los Datos Personales. Estos se almacenan aqui porque se pretende que no sean modificables
				
				user.setEmail(email);
				user.setPassword(new Sha256Hash(pass).toString());
				pd.setName(sanitize(name));
				pd.setSurname(sanitize(surname));
				pd.setNif(nif);
				pd.setUsuario(user);
				pd.setBday(date);
				career.setUsuario(user);
				studies.setUsuario(user);
				if(!pass.equals(pass2)){
					throw new Exception("noCoin");
				}
				
				UsuarioDAO udao = UsuarioDAOImplementation.getInstance();
				PersonalDataDAO pddao = PersonalDataDAOImplementation.getInstance();
				CareerDAO cdao = CareerDAOImplementation.getInstance();
				StudiesDAO sdao = StudiesDAOImplementation.getInstance();
				
				udao.create(user);
				pddao.create(pd);
				cdao.create(career);
				sdao.create(studies);
				UsernamePasswordToken token = new UsernamePasswordToken( email, pass );
				currentUser.login(token);
				resp.sendRedirect( req.getContextPath() + "/UserServlet?email=" + email + "&errorTag=000000" );
			}catch(Exception e) {
				String msg = e.getMessage() + "2";
				String code1 = "null2";
				if(msg.equals(code1)){
					resp.sendRedirect( req.getContextPath() + "/RegisterServlet?errorTag=2" );
				}else if(msg.contentEquals("noCoin2")){
					resp.sendRedirect( req.getContextPath() + "/RegisterServlet?errorTag=3" );
				}else if(msg.contentEquals("fecha erronea2")){
					resp.sendRedirect( req.getContextPath() + "/RegisterServlet?errorTag=4" );
				}else {
					resp.sendRedirect( req.getContextPath() + "/RegisterServlet?errorTag=1" );
				}
			}
		}else {
			resp.sendRedirect( req.getContextPath() + "/RegisterServlet?errorTag=0" );
		}
	}
	
	private boolean nullOrEmpty(String name, String surname, String nif, String email, String pass) {
		return ((name.isEmpty() || surname.isEmpty() || nif.isEmpty() || email.isEmpty() || pass.isEmpty() || (name == null) || (surname == null) || (nif == null) || (email == null) || (pass == null)));
	}
	
	
	public java.sql.Date getDateFromData(String byear, String bmonth,String bday) throws Exception{
		
		int byearInt;
		int bmonthInt;
		int bdayInt;
		
		try {
			byearInt = Integer.parseInt(byear);
			bmonthInt = Integer.parseInt(bmonth);
			bdayInt = Integer.parseInt(bday);
		}catch(Exception e) {
			throw new Exception("fecha erronea");
		}

		if ((byearInt<1900)|| (bmonthInt > 12) || (bmonthInt < 1)){
			throw new Exception("fecha erronea");
		}
		
		switch (bmonthInt){
		case 	1:
		case	3:
		case	5:
		case	7:
		case	8:
		case	10:
		case	12:
			if (bdayInt < 1 || bdayInt > 31) {
				throw new Exception("fecha erronea");
			}
			break;
		case 	4:
		case	6:
		case	9:
		case	11:
			if (bdayInt < 1 || bdayInt > 30) {
				throw new Exception("fecha erronea");
			}
			break;
		case 2:
			if((byearInt % 4 == 0) && ((byearInt % 100 != 0) || (byearInt % 400 == 0))) {
				if (bdayInt < 1 || bdayInt > 29 ) {
					throw new Exception("fecha erronea");
				}
			}else {
				if (bdayInt < 1 || bdayInt > 28) {
					throw new Exception("fecha erronea");
				}
			}
			break;
		}
		
		
		try {
			Calendar cal = Calendar.getInstance();
			cal.clear();
			cal.set(Calendar.YEAR, byearInt);
			cal.set(Calendar.MONTH, bmonthInt - 1);
			cal.set(Calendar.DATE, bdayInt);
			Calendar today = Calendar.getInstance();
			today.getTime();
			if (cal.after(today)){
				throw new Exception("fecha erronea");
			}
			java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
			return date;
		}catch(Exception e) {
			throw new Exception("fecha erronea");
		}
	}
}
