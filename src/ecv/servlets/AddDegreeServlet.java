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


import ecv.dao.StudiesDAO;
import ecv.dao.StudiesDAOImplementation;
import ecv.dao.TitleDAO;
import ecv.dao.TitleDAOImplementation;
import ecv.model.Studies;
import ecv.model.Title;

//CUANDO SE QUIERA AÑADIR LA PÁGINA PRINCIPAL EN LA DEFINICION DE ESTE WEBSERVLET SE TIENE QUE QUITAR LA PARTE DE "/"
@WebServlet("/AddDegreeServlet")
public class AddDegreeServlet extends HttpServlet{
	
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
		String ent = req.getParameter("ent");
		String day = req.getParameter("day");
		String month = req.getParameter("month");
		String year = req.getParameter("year");
		
		int errorTag=0;
		
		StudiesDAO sdao = StudiesDAOImplementation.getInstance();
		TitleDAO tidao = TitleDAOImplementation.getInstance();
		Long sId = new Long(Integer.parseInt(studiesId));
		Studies studies = sdao.read(sId);
	
		if(test(name,ent,day,month,year)) {
			if(isSomethingEmpty(name,ent,day,month,year)) {
				errorTag=2;
			}
		}else {
			Title ti = new Title();
			try {
			
				ti.setName(sanitize(name));
				ti.setPlace(sanitize(ent));
				java.sql.Date date =getDateFromData(year,month,day); //Se usa este metodo para comprobar que esta bien la fecha
				Calendar cal = GregorianCalendar.getInstance();
				cal.clear();
				cal.setTime(date);
				String dayF =Integer.toString(cal.get(Calendar.DATE));
				String monthF =Integer.toString(cal.get(Calendar.MONTH) + 1);
				String yearF =Integer.toString(cal.get(Calendar.YEAR));
				ti.setDate(dayF + "-" + monthF + "-" + yearF);
				ti.setStudies(studies);
				tidao.create(ti);
			}catch(Exception e) {
				errorTag = 1;
			}
		}
	
	
		resp.sendRedirect( req.getContextPath() + "/StudiesServlet?email=" + email +"&errorTag=" + errorTag);
	
	
	
	}
	private boolean test(String a, String b, String c, String d, String e) {
		return (a == null || b == null || c == null || d == null || e == null || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty() || e.isEmpty());
	}
	
	private boolean isSomethingEmpty(String a, String b, String c, String d, String e) {
		return ((!a.isEmpty() || !b.isEmpty() || !c.isEmpty() || !d.isEmpty() || !e.isEmpty())!=(!a.isEmpty() && !b.isEmpty() && !c.isEmpty() && !d.isEmpty() && !e.isEmpty()));
	}
	
	public java.sql.Date getDateFromData(String year, String month,String day) throws Exception{
		int yearInt;
		int monthInt;
		int dayInt;
		
		try {
			yearInt = Integer.parseInt(year);
			monthInt = Integer.parseInt(month);
			dayInt = Integer.parseInt(day);
		}catch(Exception e) {
			throw new Exception("fecha erronea");
		}

		if ((yearInt<1900)|| (monthInt > 12) || (monthInt < 1)){
			throw new Exception("fecha erronea");
		}
		
		switch (monthInt){
		case 	1:
		case	3:
		case	5:
		case	7:
		case	8:
		case	10:
		case	12:
			if (dayInt < 1 || dayInt > 31) {
				throw new Exception("fecha erronea");
			}
			break;
		case 	4:
		case	6:
		case	9:
		case	11:
			if (dayInt < 1 || dayInt > 30) {
				throw new Exception("fecha erronea");
			}
			break;
		case 2:
			if((yearInt % 4 == 0) && ((yearInt % 100 != 0) || (yearInt % 400 == 0))) {
				if (dayInt < 1 || dayInt > 29 ) {
					throw new Exception("fecha erronea");
				}
			}else {
				if (dayInt < 1 || dayInt > 28) {
					throw new Exception("fecha erronea");
				}
			}
			break;
		}
		
		
		try {
			Calendar cal = Calendar.getInstance();
			cal.clear();
			cal.set(Calendar.YEAR, yearInt);
			cal.set(Calendar.MONTH, monthInt - 1);
			cal.set(Calendar.DATE, dayInt);
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
