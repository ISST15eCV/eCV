package ecv.servlets;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;




import org.apache.shiro.SecurityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import ecv.dao.CareerItemDAOImplementation;
import ecv.dao.CurrentJobDAO;
import ecv.dao.CurrentJobDAOImplementation;
import ecv.dao.PersonalDataDAOImplementation;
import ecv.dao.TitleDAOImplementation;
import ecv.dao.UsuarioDAO;
import ecv.dao.UsuarioDAOImplementation;
import ecv.model.CareerItem;
import ecv.model.CurrentJob;
import ecv.model.PersonalData;
import ecv.model.Studies;
import ecv.model.Title;
import ecv.model.Usuario;

@WebServlet("/RedirectServlet")
public class RedirectServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
		String email = currentUser.getPrincipal().toString();
		String code = req.getParameter("code");
		System.out.print(code);
		getOrcidData(code, email);	
		getServletContext().getRequestDispatcher("/RedirectView.jsp").forward(req, resp);
	}
	
	//Carga los datos obtenidos desde ORCID
	private void getOrcidData(String code, String email) {
	
		Client client = ClientBuilder.newClient();
		Form input = new Form();
		input.param("client_id", "APP-YLLEF2IRUXC0CCFJ");
		input.param("client_secret", "2c2e77f3-82f4-4811-a882-cb327bde687e");
		input.param("grant_type", "authorization_code");
		input.param("redirect_uri","http://localhost:8080/eCV/RedirectServlet");
		input.param("code", code);
		Entity<Form> ent = Entity.entity(input, MediaType.APPLICATION_FORM_URLENCODED);
		WebTarget wt = client.target("https://orcid.org/oauth/token");
		Invocation.Builder invocationBuilder =  wt.request(MediaType.APPLICATION_JSON);
		Response res = invocationBuilder.post(ent);
		UsuarioDAO udao = UsuarioDAOImplementation.getInstance();
		Usuario u = udao.read(email);
		String reString = res.readEntity(String.class);
		JSONObject obj = new JSONObject(reString);
		u.setOrcidId(obj.getString("orcid"));
		u.setAccessToken(obj.getString("access_token"));
		System.out.print("---" + u.getAccessToken() + "----" + u.getOrcidId() + "---");
		udao.update(u);
		
		//Descargamos los datos del usuario
		
		String record = client.target("https://pub.orcid.org/v2.1/" + u.getOrcidId() +"/record")
				.queryParam("Authorization type", "Bearer")
				.queryParam("Access token", u.getAccessToken())
				.request("application/vnd.orcid+json")
				.get(String.class);
		JSONObject conv = new JSONObject(record);
		System.out.println(conv);
		
		//String researcher_urls = ((JSONObject)((JSONObject)((JSONObject)conv.get("person")).getJSONArray("researcher-urls").get(0)).get("url")).getString("value");
		try {
			String researcher_url = ((JSONObject)((JSONObject)((JSONObject)((JSONObject)conv.get("person")).get("researcher-urls")).getJSONArray("researcher-url").get(0)).get("url")).getString("value");
			System.out.println(researcher_url);
			if(researcher_url != null && !researcher_url.isEmpty()) {
				PersonalData pd = u.getPd();
				pd.setWeb(researcher_url);
				PersonalDataDAOImplementation.getInstance().update(pd);
			}
		}catch(Exception e) {
			System.out.println("no se añaden webs");
		}
		
		
		JSONArray array = conv.getJSONObject("activities-summary").getJSONObject("educations").getJSONArray("education-summary");
		for(int i =0; i < array.length(); i++) {
				String name;
			try {
				name = array.getJSONObject(i).getString("role-title");
			}catch(Exception e){
				name="-";
			}
			try {
				
				String place = array.getJSONObject(i).getJSONObject("organization").getString("name");
				String day = array.getJSONObject(i).getJSONObject("end-date").getJSONObject("day").get("value").toString();
				String month = array.getJSONObject(i).getJSONObject("end-date").getJSONObject("month").get("value").toString();
				String year = array.getJSONObject(i).getJSONObject("end-date").getJSONObject("year").get("value").toString();
				if(name != null && place != null && day != null && month != null && year != null && !name.isEmpty() && !place.isEmpty() && !day.isEmpty() && !month.isEmpty() && !year.isEmpty()) {
					Title t = new Title();
					t.setName(name);
					t.setPlace(place);
					t.setDate(day + "-" + month + "-" + year);
					t.setStudies(u.getStudies());
					boolean update = true; 
					if(!u.getStudies().getDegrees().isEmpty()|| u.getStudies().getDegrees() != null) {
						for(Title old: u.getStudies().getDegrees()) {

							if (t.getName().equalsIgnoreCase(old.getName()) && t.getPlace().equalsIgnoreCase(old.getPlace())) {
								update = false;
								
							}
						}
						if(update) {
							TitleDAOImplementation.getInstance().create(t);
						}
					}
				}
			}catch(Exception e) {
				System.err.println(e.getMessage());
				System.out.println("titulación no añadida");
				continue;
			}
		}
		
		//category, employer, start
		JSONArray arrayEmpleos = conv.getJSONObject("activities-summary").getJSONObject("employments").getJSONArray("employment-summary");
		for(int i =0; i < arrayEmpleos.length(); i++) {
			String name;
			boolean current = false;
		try {
			name = arrayEmpleos.getJSONObject(i).getString("role-title") + " en " +  arrayEmpleos.getJSONObject(i).getString("department-name");
		}catch(Exception e){
			name="-";
		}
		
		String place = "";
		String day = "";
		String month = "";
		String year = ""; 
		try {
			place = arrayEmpleos.getJSONObject(i).getJSONObject("organization").getString("name");
			day = arrayEmpleos.getJSONObject(i).getJSONObject("start-date").getJSONObject("day").get("value").toString();
			month = arrayEmpleos.getJSONObject(i).getJSONObject("start-date").getJSONObject("month").get("value").toString();
			year = arrayEmpleos.getJSONObject(i).getJSONObject("start-date").getJSONObject("year").get("value").toString();
			
			try {
				String daye = arrayEmpleos.getJSONObject(i).getJSONObject("end-date").getJSONObject("day").get("value").toString();
				String monthe = arrayEmpleos.getJSONObject(i).getJSONObject("end-date").getJSONObject("month").get("value").toString();
				String yeare = arrayEmpleos.getJSONObject(i).getJSONObject("end-date").getJSONObject("year").get("value").toString();
				System.out.println("llega hasta aqui");
			}catch(Exception e){
				current = true;
				System.out.println("llega hasta aqui");
			}
	
				if(!name.isEmpty() && !place.isEmpty() && !day.isEmpty() && !month.isEmpty() && !year.isEmpty() && !current) {
					CareerItem c = new CareerItem();
					c.setCategory(name);
					c.setEmployer(place);
					c.setStart(day + "-" + month + "-" + year);
					c.setCareer(u.getCareer());
					boolean update = true; 
					if(!u.getStudies().getDegrees().isEmpty()|| u.getStudies().getDegrees() != null) {
						for(CareerItem old: u.getCareer().getPreviousJobs()) {
	
							if (c.getCategory().equalsIgnoreCase(old.getCategory()) && c.getEmployer().equalsIgnoreCase(old.getEmployer())) {
								update = false;
								
							}
						}
						if(update) {
							CareerItemDAOImplementation.getInstance().create(c);
						}
					}
				}else if(!name.isEmpty() && !place.isEmpty() && !day.isEmpty() && !month.isEmpty() && !year.isEmpty() && current){
					System.out.println("llega hasta aqui");
					CurrentJobDAO cjdao = CurrentJobDAOImplementation.getInstance();
					CurrentJob cj;
					if(u.getCareer().getCurrentJob()!=null) {
						cj= u.getCareer().getCurrentJob();
					}else {
						cj = new CurrentJob();
						cj.setCareer(u.getCareer());
					}
					cj.setCategory(name);
					cj.setEmployer(place);
					Calendar cal = Calendar.getInstance();
					System.out.println("llega hasta aqui");
					cal.clear();
					cal.set(Calendar.YEAR,Integer.parseInt(year));
					cal.set(Calendar.MONTH,Integer.parseInt(month));
					cal.set(Calendar.DATE,Integer.parseInt(day));
					System.out.println("llega hasta aqui");
					java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
					cj.setStart(date);
					if(u.getCareer().getCurrentJob()==null) {
						cjdao.create(cj);
					}else {
						cjdao.update(cj);
					}
					
				}		
			}catch(Exception e) {
				System.out.println("No se añaden trabajos");
			}
		
		}//Aqui acaba el for
		
	}
	
}
