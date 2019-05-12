package ecv.servlets;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;

import ecv.dao.CVDAO;
import ecv.dao.CVDAOImplementation;
import ecv.dao.UsuarioDAO;
import ecv.dao.UsuarioDAOImplementation;
import ecv.model.CV;
import ecv.model.CareerItem;
import ecv.model.Gender;
import ecv.model.Language;
import ecv.model.Phd;
import ecv.model.Title;
import ecv.model.Usuario;
 
@WebServlet({"/PDFServlet"})
public class PDFServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private  String nombre;
	private  String apellidos;
	private  Gender genero;
	private  String DNI;
	private  Date fechaNacimiento;
	private  String paisNacimiento;
	private  String ciudadNacimiento;
	private  String direccionContacto;
	private  Integer telFijo;
	private  Integer telMovil;
	private  Integer fax;
	private  String email;
	private  String web;
	
	private static String DatosPersonales_titulos = "";
	
	 /**
	  * 
	  * @param document
	  * @param req 
	  * @param resp 
	  * @param DatosSituacion 
	  * @throws DocumentException
	  */
	private void createTableTest(Document document, Usuario usuario, HttpServletRequest req, HttpServletResponse resp) throws DocumentException {
		
		String datos_grado = "";
		String datos_phd = "";
		String trabajoActual = "";
		String DatosPersonales = "";
		String DatosEstudios ="";
		String DatosIdiomas = "";
		String DatosSituacion = "";
		String Situacion_titulos = "";
		String Estudios_titulos = "";
		String Grados_titulos = "";
		String Phd_titulos = "";
		 		
		
		Boolean fax_boolean = true;
		Boolean web_boolean = true;
		Boolean estudios_grado_boolean = true;
		Boolean estudios_phd_boolean = true;
		Boolean trabajo_actual_boolean = true;
		Boolean trabajo_anteriores_boolean = true;
		Boolean idiomas_boolean = true;
		
		
		nombre = usuario.getPd().getName();			
		apellidos = usuario.getPd().getSurname();	
		genero = usuario.getPd().getGender();		
		DNI = usuario.getPd().getNif();			
		fechaNacimiento = usuario.getPd().getBday();				
		email = usuario.getEmail();	
		
		// PAIS DE NACIMIENTO
		if (!usuario.getPd().getNationality().getCountry().isEmpty() || usuario.getPd().getNationality().getCountry() != null) { paisNacimiento = usuario.getPd().getNationality().getCountry(); } else { paisNacimiento = "";}
		
		// CIUDAD NACIMIENTO
		if (!usuario.getPd().getNationality().getCity().isEmpty() || usuario.getPd().getNationality().getCity() != null) { ciudadNacimiento = usuario.getPd().getNationality().getCity(); } else { ciudadNacimiento = "";}
		
		// DIRECCION CONTACTO
		if (!usuario.getPd().getAddress().getAddr().isEmpty() || usuario.getPd().getAddress().getAddr() != null) { direccionContacto = usuario.getPd().getAddress().getAddr() + ", " + usuario.getPd().getAddress().getCity() + ", " + usuario.getPd().getAddress().getRegion() + ", " + usuario.getPd().getAddress().getCountry(); } else { direccionContacto = "";}
		
		// TELEFONO
		if (usuario.getPd().getTlf() != null) { telFijo = usuario.getPd().getTlf(); } else { telFijo = null; }
		
		// MOVIL
		if (usuario.getPd().getMobile() != null) { telMovil = usuario.getPd().getMobile();	} else { telMovil = null; }
		
		// FAX
		if (usuario.getPd().getFax() != null) { fax = usuario.getPd().getFax(); } else { fax_boolean = false; fax = null; }
		
		// WEB
		if (usuario.getPd().getWeb() !=null) { web = usuario.getPd().getWeb(); } else {web_boolean = false;}
		
		/**
		 * IDIOMAS
		 */
		String Idiomas_titulos = "";
		if (usuario.getStudies().getLanguages().isEmpty()) {
			DatosIdiomas = "";
			Idiomas_titulos = "";
		} else {
			Collection<Language> idiomas = usuario.getStudies().getLanguages();
			
			for (Language lenguaje: idiomas) {
				String nombre = lenguaje.getName();
				String nivelS = lenguaje.getSpeakingLevel();
				String nivelR = lenguaje.getReadingLevel();
				String nivelL = lenguaje.getListeningLevel();
				DatosIdiomas += nombre + "\n" + nivelL + "\n" + nivelR + "\n"+ nivelS + "\n\n";
				Idiomas_titulos += "Idioma:\n" + "Nivel de escucha:\n" + "Nivel de lectura:\n" + "Nivel de conversación:\n\n";
	 		}
			Idiomas_titulos += "\n\n";
			DatosIdiomas += "\n\n";
		}
		
		
		  
		/**
		 * ESTUDIOS
		 */
		
		// Estudios de grado
		if (usuario.getStudies().getDegrees().isEmpty()) {
			estudios_grado_boolean = false;
			Grados_titulos += "";
		} else {
			Collection<Title> degrees = usuario.getStudies().getDegrees();
			
			for (Title grado: degrees) {
				String nombre = grado.getName();
				String fecha = grado.getDate();
				String lugar = grado.getPlace();
				datos_grado += nombre + "\n" + fecha + "\n" + lugar + "\n\n";
				Grados_titulos += "Nombre titulacion:\n" + "Fecha finalización:\n" + "Lugar:\n\n";
	 		}
		}
		 
		// Estudios de doctorado
		if (usuario.getStudies().getPhds().isEmpty()) {
			estudios_phd_boolean = false;
			Phd_titulos += "";
		} else {
			Collection<Phd> phds = usuario.getStudies().getPhds();
			
			for (Phd phd: phds) {
				String nombre = phd.getName();
				String fecha = phd.getDate();
				String lugar = phd.getPlace();
				datos_phd += nombre + "\n" + fecha + "\n" + lugar + "\n\n";
				Phd_titulos += "Nombre titulacion:\n" + "Fecha finalización:\n" + "Lugar:\n\n";
	 		}
		}
		
		
		if (estudios_phd_boolean == false && estudios_grado_boolean == false) {
			Estudios_titulos = "";	
		} else if (estudios_phd_boolean == false && estudios_grado_boolean == true) {
			Estudios_titulos = "Estudios de grado\n" + Grados_titulos + "\n";	
		} else if (estudios_phd_boolean == true && estudios_grado_boolean == false) {
			Estudios_titulos = "\nEstudios de doctorado:\n" + Phd_titulos;	
		} else {
			Estudios_titulos = "Estudios de grado\n" + Grados_titulos + "\nEstudios de doctorado:\n" + Phd_titulos;		
		}
	
		
		
		
		/**
		 *  DATOS SITUACION
		 */
		
		// TRABAJO ACTUAL
		String actual_titulo = "";
		if (usuario.getCareer().getCurrentJob() == null) {
			trabajoActual = "Sin trabajo actual\n";
			actual_titulo = "";
		} else {
			String categoria = usuario.getCareer().getCurrentJob().getCategory();
			String employr = usuario.getCareer().getCurrentJob().getEmployer();
			Date inicio = usuario.getCareer().getCurrentJob().getStart();
			trabajoActual = "\n" + categoria + "\n" + employr + "\n" + inicio.toString() + "\n";
			actual_titulo = "Cargo:" + "\n" + "Empleador:" + "\n" + "Fecha de inicio" + "\n";
		}
		
								
		// ANTERIORES TRABAJOS
		String anteriores_datos = "";
		String anteriores_titulos = "";
		
		if (usuario.getCareer().getPreviousJobs().isEmpty()) {
			anteriores_datos = "Sin anteriores trabajos\n";
			anteriores_titulos = "";
		} else {
			Collection<CareerItem> trabajosAnteriores = usuario.getCareer().getPreviousJobs();
			for (CareerItem trabajoAnterior: trabajosAnteriores) {
				String category = trabajoAnterior.getCategory();
				String employer = trabajoAnterior.getEmployer();
				String start = trabajoAnterior.getStart();
				anteriores_datos += "\n" + category + "\n" + employer + "\n" + start + "\n";
				anteriores_titulos += "Cargo:" + "\n" + "Empleador:" + "\n" + "Fecha de inicio" + "\n\n";
	 		}
		}
		

		Situacion_titulos = "Trabajo actual:" + "\n" + actual_titulo + "\n\n" + "Trabajos anteriores:" + "\n" + anteriores_titulos;
		
		/**
		 *  DATOS DE TEXTO LIBRE
		 */
		String freeTextDato;
		String freeTextTitulo;
		
		if (usuario.getPd().getTextFree() == null) {
			freeTextDato = "";
			freeTextTitulo = "";
		} else {
			freeTextDato = usuario.getPd().getTextFree();
			freeTextTitulo = "Otros datos";
		}
		 
		
		if (fax_boolean == false && web_boolean == true) {
			DatosPersonales = apellidos + "\n" + nombre + "\n" + genero + "\n" + DNI + "\n" + fechaNacimiento + "\n" + paisNacimiento + "\n" + ciudadNacimiento + "\n" + direccionContacto + "\n" + telFijo + "\n" + telMovil + "\n" + email + "\n" + web;
			DatosPersonales_titulos = "Apellidos:\n"
					+ "Nombre:\n"
					+ "Género:\n"
		    		+ "DNI:\n"
		    		+ "Fecha de nacimiento:\n"
		    		+ "Pais de nacimiento:\n"
		    		+ "Ciudad de nacimiento:\n"
		    		+ "Dirección de contacto:\n"
		    		+ "Telefono fijo:\n"
		    		+ "Telefono móvil:\n"
		    		+ "Correo electrónico:\n"
		    		+ "Página web personal:\n";
		} else if (fax_boolean == true && web_boolean == false ) {
			DatosPersonales = apellidos + "\n" + nombre + "\n" + genero + "\n" + DNI + "\n" + fechaNacimiento + "\n" + paisNacimiento + "\n" + ciudadNacimiento + "\n" + direccionContacto + "\n" + telFijo + "\n" + telMovil + "\n" + fax + "\n" + email;
			DatosPersonales_titulos = "Apellidos:\n" + "Nombre:\n"
					+ "Género:\n"
		    		+ "DNI:\n"
		    		+ "Fecha de nacimiento:\n"
		    		+ "Pais de nacimiento:\n"
		    		+ "Ciudad de nacimiento:\n"
		    		+ "Dirección de contacto:\n"
		    		+ "Telefono fijo:\n"
		    		+ "Telefono móvil:\n"
		    		+ "Fax:\n"
		    		+ "Correo electrónico:\n";
		} else if (fax_boolean == false && web_boolean == false) {
			DatosPersonales = apellidos + "\n" + nombre + "\n" + genero + "\n" + DNI + "\n" + fechaNacimiento + "\n" + paisNacimiento + "\n" + ciudadNacimiento + "\n" + direccionContacto + "\n" + telFijo + "\n" + telMovil + "\n" + email;
			DatosPersonales_titulos = "Apellidos:\n"
					+ "Nombre:\n"
					+ "Género:\n"
		    		+ "DNI:\n"
		    		+ "Fecha de nacimiento:\n"
		    		+ "Pais de nacimiento:\n"
		    		+ "Ciudad de nacimiento:\n"
		    		+ "Dirección de contacto:\n"
		    		+ "Telefono fijo:\n"
		    		+ "Telefono móvil:\n"
		    		+ "Correo electrónico:\n";
		} else {
			DatosPersonales = apellidos + "\n" + nombre + "\n" + genero + "\n" + DNI + "\n" + fechaNacimiento + "\n" + paisNacimiento + "\n" + ciudadNacimiento + "\n" + direccionContacto + "\n" + telFijo + "\n" + telMovil + "\n" + fax + "\n" + email + "\n" + web;
			DatosPersonales_titulos = "Apellidos:\n"
					+ "Nombre:\n"
					+ "Género:\n"
		    		+ "DNI:\n"
		    		+ "Fecha de nacimiento:\n"
		    		+ "Pais de nacimiento:\n"
		    		+ "Ciudad de nacimiento:\n"
		    		+ "Dirección de contacto:\n"
		    		+ "Telefono fijo:\n"
		    		+ "Telefono móvil:\n"
		    		+ "Fax:\n"
		    		+ "Correo electrónico:\n"
		    		+ "Página web personal:\n";
		}
		
		DatosSituacion = trabajoActual + "\n\n" + anteriores_datos;
		DatosEstudios = "\n" + datos_grado + "\n\n" + datos_phd;
		
		

		
		// FUENTES PARA EL PDF
		com.itextpdf.text.Font NombreFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14f);
		NombreFont.setColor(BaseColor.BLACK);
		
		com.itextpdf.text.Font titulosFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f);
		titulosFont.setColor(BaseColor.BLACK);
		
		com.itextpdf.text.Font datosFont = FontFactory.getFont(FontFactory.HELVETICA, 10f);
		datosFont.setColor(BaseColor.BLACK);
		
		float[] columnWidths1 = {1, 3};
		float[] columnWidths2 = {1, (float) 1.4, (float) 2};
		
		PdfPTable tableNombre = new PdfPTable(2);
		PdfPTable tableDatosPersonales = new PdfPTable(3);
		PdfPTable tableSituacion = new PdfPTable(3);
		PdfPTable tableEstudios = new PdfPTable(3);
		PdfPTable tableIdiomas = new PdfPTable(3);
		PdfPTable tableFreeText = new PdfPTable(3);
		
		tableNombre.setWidths(columnWidths1);
		tableDatosPersonales.setWidths(columnWidths2);
		tableSituacion.setWidths(columnWidths2);
		tableEstudios.setWidths(columnWidths2);
		tableIdiomas.setWidths(columnWidths2);
		tableFreeText.setWidths(columnWidths2);
		
		// FOTO DE USUARIO
				PdfPCell cellFoto = new PdfPCell(new Phrase(""));
				cellFoto.setBorderColor(BaseColor.WHITE);
				
		byte[] photo = usuario.getPhoto();
		try {
			Image image = Image.getInstance(photo);
			image.scaleAbsoluteWidth(80);
			image.scaleAbsoluteHeight(80);
			cellFoto.addElement(image);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		

		// ENCABEZADO CON NOMBRE Y APELLIDOS
		PdfPCell cellNombre = new PdfPCell(new Phrase(nombre + " " + apellidos,NombreFont));
		cellNombre.setBorderColor(BaseColor.WHITE);
		cellNombre.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		tableNombre.addCell(cellFoto);
		tableNombre.addCell(cellNombre);
		
		// DATOS PERSONALES
		PdfPCell cell = new PdfPCell(new Phrase(""));
		cell.setRowspan(2);
		cell.setBorderColor(BaseColor.WHITE);

		tableDatosPersonales.addCell(cell);
		
		// we add the four remaining cells with addCell()
		PdfPCell cellDatosPersonalesTitulos = new PdfPCell();
	    cellDatosPersonalesTitulos.addElement(new Phrase(DatosPersonales_titulos + "\n\n",titulosFont));  
	    cellDatosPersonalesTitulos.setBorderColor(BaseColor.WHITE);
	    cellDatosPersonalesTitulos.setPaddingLeft(10);
	    cellDatosPersonalesTitulos.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cellDatosPersonalesTitulos.setVerticalAlignment(Element.ALIGN_TOP);
		
	    PdfPCell cellDatosPersonales = new PdfPCell();
	    cellDatosPersonales.addElement(new Phrase(DatosPersonales + "\n\n",datosFont));  
	    cellDatosPersonales.setBorderColor(BaseColor.WHITE);
	    cellDatosPersonales.setPaddingLeft(10);
	    cellDatosPersonales.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cellDatosPersonales.setVerticalAlignment(Element.ALIGN_TOP);
	    
	    tableDatosPersonales.addCell(cellDatosPersonalesTitulos);
	    tableDatosPersonales.addCell(cellDatosPersonales);		
		
	    
	    // SITUACIÓN 
	    PdfPCell cell2 = new PdfPCell(new Phrase(""));
	    cell2.setBorderColor(BaseColor.WHITE);

		PdfPCell cellSituacionTitulos = new PdfPCell();
		cellSituacionTitulos.addElement(new Phrase(Situacion_titulos + "\n\n",titulosFont));  
		cellSituacionTitulos.setBorderColor(BaseColor.WHITE);
		cellSituacionTitulos.setPaddingLeft(10);
		cellSituacionTitulos.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellSituacionTitulos.setVerticalAlignment(Element.ALIGN_TOP);
		
		PdfPCell cellSituacion = new PdfPCell();
		cellSituacion.addElement(new Phrase(DatosSituacion + "\n\n",datosFont));  
		cellSituacion.setBorderColor(BaseColor.WHITE);
		cellSituacion.setPaddingLeft(10);
		cellSituacion.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellSituacion.setVerticalAlignment(Element.ALIGN_TOP);
		
		tableSituacion.addCell(cell2);
		tableSituacion.addCell(cellSituacionTitulos);
		tableSituacion.addCell(cellSituacion);
		
		
		// ESTUDIOS
		PdfPCell cell3 = new PdfPCell(new Phrase(""));
		cell3.setBorderColor(BaseColor.WHITE);
		
		PdfPCell cellEstudiosTitulos = new PdfPCell();
		cellEstudiosTitulos.addElement(new Phrase(Estudios_titulos + "\n\n", titulosFont));
		cellEstudiosTitulos.setBorderColor(BaseColor.WHITE);
		cellEstudiosTitulos.setPaddingLeft(10);
		cellEstudiosTitulos.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellEstudiosTitulos.setVerticalAlignment(Element.ALIGN_TOP);
		
		PdfPCell cellEstudios = new PdfPCell();
		cellEstudios.addElement(new Phrase(DatosEstudios + "\n\n", datosFont));
		cellEstudios.setBorderColor(BaseColor.WHITE);
		cellEstudios.setPaddingLeft(10);
		cellEstudios.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellEstudios.setVerticalAlignment(Element.ALIGN_TOP);
		
		tableEstudios.addCell(cell3);
		tableEstudios.addCell(cellEstudiosTitulos);
		tableEstudios.addCell(cellEstudios);
		
		
		// IDIOMAS
		PdfPCell cell4 = new PdfPCell(new Phrase(""));
		cell4.setBorderColor(BaseColor.WHITE);
		
		PdfPCell cellIdiomasTitulos = new PdfPCell();
		cellIdiomasTitulos.addElement(new Phrase(Idiomas_titulos , titulosFont));
		cellIdiomasTitulos.setBorderColor(BaseColor.WHITE);
		cellIdiomasTitulos.setPaddingLeft(10);
		cellIdiomasTitulos.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellIdiomasTitulos.setVerticalAlignment(Element.ALIGN_TOP);
		
		PdfPCell cellIdiomas = new PdfPCell();
		cellIdiomas.addElement(new Phrase(DatosIdiomas , datosFont));
		cellIdiomas.setBorderColor(BaseColor.WHITE);
		cellIdiomas.setPaddingLeft(10);
		cellIdiomas.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellIdiomas.setVerticalAlignment(Element.ALIGN_TOP);
		
		tableIdiomas.addCell(cell4);
		tableIdiomas.addCell(cellIdiomasTitulos);
		tableIdiomas.addCell(cellIdiomas);
		
		// TEXTO LIBRE
		PdfPCell cell5 = new PdfPCell(new Phrase(""));
		cell5.setBorderColor(BaseColor.WHITE);
		
		PdfPCell cellFreeTextTitulo = new PdfPCell();
		cellFreeTextTitulo.addElement(new Phrase(freeTextTitulo + "\n\n", titulosFont));
		cellFreeTextTitulo.setBorderColor(BaseColor.WHITE);
		cellFreeTextTitulo.setPaddingLeft(10);
		cellFreeTextTitulo.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellFreeTextTitulo.setVerticalAlignment(Element.ALIGN_TOP);
		
		PdfPCell cellFreeText = new PdfPCell();
		cellFreeText.addElement(new Phrase(freeTextDato + "\n\n", datosFont));
		cellFreeText.setBorderColor(BaseColor.WHITE);
		cellFreeText.setPaddingLeft(10);
		cellFreeText.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellFreeText.setVerticalAlignment(Element.ALIGN_TOP);
		
		tableFreeText.addCell(cell5);
		tableFreeText.addCell(cellFreeTextTitulo);
		tableFreeText.addCell(cellFreeText);
		
		document.add(tableNombre);
		document.add(tableDatosPersonales);
		document.add(tableSituacion);
		document.add(tableEstudios);
		document.add(tableIdiomas);
		document.add(tableFreeText);
		
		
	}
	
	/**
	 * Configuración del encabezado del documento
	 * 
	 * @param writer
	 * @param document
	 * @throws DocumentException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	private void onStartPage(PdfWriter writer, Document document) throws DocumentException, MalformedURLException, IOException { 
	    
		String imgPath = "/img/logo.png";
	    String absoluteImgPath = getServletContext().getRealPath(imgPath);
		Image img = Image.getInstance(absoluteImgPath);
	    img.scaleAbsolute(40, 40);
	    img.setAbsolutePosition(40, 780);
	    
		document.add(img);
	      
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("CV Normalizado"), 100, 800, 0);
    }
	
//	private static String generateString() {
//        String uuid = UUID.randomUUID().toString();
//        return "uuid = " + uuid;
//    }
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException,  IOException {
    	
    	
    	String email = req.getParameter("email");
		UsuarioDAO udao = UsuarioDAOImplementation.getInstance();
		Usuario u = udao.read(email);
		
    	
		res.setContentType("application/pdf"); 
		
    	Document document = new Document();	
    	Document save = new Document();
	    try  {
	         PdfWriter writer = PdfWriter.getInstance(document, res.getOutputStream());
	         
	    			 
	         document.open();
	         document.add(new Paragraph("\n\n"));
	         onStartPage(writer,document);
	         createTableTest(document, u, req, res);
	         onStartPage(writer,document);
	         document.close();

	         //A partir de aqui ni idea de si tira
	         System.out.println("Ha llegado a la zona rara");
	         ByteArrayOutputStream output = new ByteArrayOutputStream();
	         PdfWriter saver = PdfWriter.getInstance(save, output);
	         save.open();
	         save.add(new Paragraph("\n\n"));
	         onStartPage(saver,save);
	         createTableTest(save, u, req, res);
	         onStartPage(saver,save);
	         save.close();
	         
	         CVDAO cvdao = CVDAOImplementation.getInstance();
	         Calendar cal = Calendar.getInstance();
	         String fecha = cal.getTime().toString();
	         CV cv = new CV();
	         cv.setDate(fecha);
	         cv.setUsuario(u);
	         cv.setDocument(output.toByteArray());
	         cvdao.create(cv);
	         
	         
	         
	      } catch (DocumentException e) {
	         e.printStackTrace();
	      } catch (FileNotFoundException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
