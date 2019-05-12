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

import ecv.dao.AddressDAO;
import ecv.dao.AddressDAOImplementation;
import ecv.dao.NationalityDAO;
import ecv.dao.NationalityDAOImplementation;
import ecv.dao.PersonalDataDAO;
import ecv.dao.PersonalDataDAOImplementation;
import ecv.dao.UsuarioDAO;
import ecv.dao.UsuarioDAOImplementation;
import ecv.model.Address;
import ecv.model.Gender;
import ecv.model.Nationality;
import ecv.model.PersonalData;
import ecv.model.Usuario;

//CUANDO SE QUIERA AÑADIR LA PÁGINA PRINCIPAL EN LA DEFINICION DE ESTE WEBSERVLET SE TIENE QUE QUITAR LA PARTE DE "/"
@WebServlet("/UpdatePDServlet")
public class UpdatePDServlet extends HttpServlet{
	
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
		//Recoleccion de datos
		String gender = req.getParameter("gender");
		String email = req.getParameter("email");
		String pdstr = req.getParameter("pd");
		
		//Recolección de datos de direcciones
		String addrId = req.getParameter("addrId");
		String aAddr = req.getParameter("aAddr");
		String aCity = req.getParameter("aCity");
		String aRegion = req.getParameter("aRegion");
		String aCountry = req.getParameter("aCountry");
		
		String nationalityId = req.getParameter("nationalityId");
		String nAddr = req.getParameter("nAddr");
		String nCity = req.getParameter("nCity");
		String nRegion = req.getParameter("nRegion");
		String nCountry = req.getParameter("nCountry");
		
		//Recolección de datos de contacto
		String tlf = req.getParameter("tlf");
		String mobile = req.getParameter("mobile");
		String fax = req.getParameter("fax");
		String web = req.getParameter("web");
		
		//Recolección de datos del texto libre
		String freeText = req.getParameter("freeText");
		
		//Descarga del pd
		Long pdid = new Long(Integer.parseInt(pdstr));
		PersonalDataDAO pddao = PersonalDataDAOImplementation.getInstance();
		PersonalData pd = pddao.read(pdid);
		AddressDAO addao = AddressDAOImplementation.getInstance();
		NationalityDAO ndao = NationalityDAOImplementation.getInstance();
		
		
		//Definicion de flags de error
		boolean errorTlfMbl = false;			//Salta si solo se rellena uno de los dos telefonos y el otro no (solo si no están llenos desde un principio)
		boolean errorTlf = false;				//Salta si los valores introducidos en el campo tlf son incorrectos
		boolean errorMbl = false;				//Salta si los valores introducidos en el campo movil son incorrectos
		boolean errorFax = false;				//Salta si los valores introducidos en el campo fax son incorrectos
		
		boolean errorAddr = false;      		//Salta si no todos los elementos del campo address se rellenan;
		boolean errorNationality = false; 		//Salta si no todos los elementos del campo address se rellenan;
		
		//Modificacion de valores
		if (gender == null) {
			gender = "o";
		}
		switch (gender) {
		case "m":
			pd.setGender(Gender.HOMBRE);
			break;
		case "f":
			pd.setGender(Gender.MUJER);
			break;
		case "o":
			pd.setGender(Gender.OTRO);
			break;
		}
		
		
		if(pd.getMobile() == null || pd.getTlf() == null) {
			if((!tlf.isEmpty() || !mobile.isEmpty())!=(tlf.isEmpty()&&mobile.isEmpty())) {     
				errorTlfMbl = true;
			}
		}


		if(tlf.isEmpty() || tlf == null) {				
			System.out.println("No se ha actualizado el telefono ni el movil");
		}else {
			if(tlf.length()==9) {
				try {
					Integer tlfi = Integer.parseInt(tlf);
					pd.setTlf(tlfi);
				}catch(Exception e){
					errorTlf = true;
					System.out.println("No es un telefono");
				}
			}else {
				errorTlf = true;
				System.out.println("No es un telefono");
			}
		}
		
		if(mobile.isEmpty() || mobile == null) {				//Como son condiciones obligatorias si no está relleno el campo movil no se realiza
			System.out.println("No se ha actualizado el telefono ni el movil");
		}else {
			if(mobile.length()==9) {
				try {
					Integer mobileI = Integer.parseInt(mobile);
					pd.setMobile(mobileI);
				}catch(Exception e){
					errorMbl = true;
					System.out.println("No es un movil");
				}
			}else {
				errorMbl = true;
				System.out.println("No es un movil");
			}
		}
		
		
		
		if(fax.isEmpty() || fax == null) {								//N se manejan excepciones de completitud
			System.out.println("No se ha actualizado el fax");
		}else {
			if(fax.length()==9) {
				try {
					Integer faxi = Integer.parseInt(fax);
					pd.setFax(faxi);
				}catch(Exception e){
					System.out.println("No es un fax");
					errorFax = true; 
				}
			}else {
				errorFax = true;
			}
		}
		
		if(web.isEmpty() || web == null) {								//No se manejan excepciones
			System.out.println("No se ha actualizado la web");
		}else {
			pd.setWeb(web);
		}
		
		if(freeText.isEmpty() || freeText == null) {								//No se manejan excepciones
			System.out.println("No se ha actualizado el texto libre");
		}else {
			pd.setTextFree(sanitize(freeText));	
		}
				
		//Actualización del PD
		pddao.update(pd);
		
		//Actualización o creación de las Direcciones
		
		if(testAddr(aAddr, aCity, aRegion, aCountry)){ //Si no se ha escrito nada no se actualiza
			if(buscaError(aAddr, aCity, aRegion, aCountry)) {
				errorAddr = true;
			}
		}else { //Si se ha escrito se comprueba si ya existia un dato
			Address addr;
			if((addrId == null) || addrId.isEmpty()) { 	//Si no existe se crea uno nuevo
				addr = new Address();
			}else{										//Si existe se actualiza el antiguo
				Long adid = new Long(Integer.parseInt(addrId));
				addr = addao.read(adid);
			}
			
			addr.setAddr(sanitize(aAddr));
			addr.setCity(sanitize(aCity));
			addr.setRegion(sanitize(aRegion));
			addr.setCountry(sanitize(aCountry));
			addr.setPd(pd);
			
			if((addrId == null) || addrId.isEmpty()) { 	//Si no existia se crea uno nuevo
				addao.create(addr);
			}else {										//Si existe se actualiza
				addao.update(addr);
			}
		}
		
		if(testAddr(nAddr, nCity, nRegion, nCountry)) {
			if(buscaError(nAddr, nCity, nRegion, nCountry)) {
				errorNationality = true;
			}
		}else {
			Nationality nationality;
			if((nationalityId == null) || nationalityId.isEmpty()) {
				nationality = new Nationality();
			}else {
				Long ndid = new Long(Integer.parseInt(nationalityId));
				nationality = ndao.read(ndid);
			}
			nationality.setAddr(sanitize(nAddr));
			nationality.setCity(sanitize(nCity));
			nationality.setRegion(sanitize(nRegion));
			nationality.setCountry(sanitize(nCountry));
			nationality.setPd(pd);
			
			if((nationalityId == null) || nationalityId.isEmpty()) {
				ndao.create(nationality);
			}else {
				ndao.update(nationality);
			}
		}
		

		

		
		//Manejo de excepciones y relanzar la vista
		resp.sendRedirect( req.getContextPath() + "/UserServlet?email=" + email +"&errorTag=" + redir(errorTlfMbl, errorTlf, errorMbl, errorFax, errorAddr, errorNationality));
	}
	
	private boolean testAddr(String a, String c, String r, String p) {
		if (a.isEmpty() || c.isEmpty() || r.isEmpty() || p.isEmpty() || (a == null) || (c == null) || (r == null) || (p == null)){
			return true;
		}else {
			return false;
		}
		
	}
	
	private boolean buscaError(String a, String b, String c, String d) {
		boolean isSomeoneFull =((!a.isEmpty() && a != null) || (!b.isEmpty() && b != null) || (!c.isEmpty() && c != null) || (!d.isEmpty() && d != null ));
		boolean isAnyoneFull =	(!a.isEmpty() && !b.isEmpty() && !c.isEmpty() && !d.isEmpty());
		return (isSomeoneFull != isAnyoneFull); //Or exclusivo: si todas estan vacias devuelve un 0, si todas estan llenas devuelve un 0, pero si solo hay alguna llena devuelve un 1;
	}
	
	private String redir(boolean tlfMbl, boolean tlf, boolean mbl, boolean fax, boolean addr, boolean nationality) {
		int[] errorCode = {0,0,0,0,0,0};
		if (tlfMbl == true) errorCode[0] = 1;
		if (tlf == true) errorCode[1] = 1;
		if (mbl == true) errorCode[2] = 1;
		if (fax == true) errorCode[3] = 1;
		if (addr == true) errorCode[4] = 1;
		if (nationality == true) errorCode[5] = 1;
		String errorTag = "";
		for (int i: errorCode){
			errorTag +=  Integer.toString(i);
		}
		return errorTag;
	}
}


