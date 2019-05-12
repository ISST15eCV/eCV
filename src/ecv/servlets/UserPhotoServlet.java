package ecv.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ecv.dao.UsuarioDAO;
import ecv.dao.UsuarioDAOImplementation;
import ecv.model.Usuario;



@WebServlet("/UserPhotoServlet")
@MultipartConfig
public class UserPhotoServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter( "email" );
		
		Part filePart = req.getPart( "file" );
		InputStream fileContent = filePart.getInputStream();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[10240];
		for (int length = 0; (length = fileContent.read(buffer)) > 0;) output.write(buffer, 0, length);
		UsuarioDAO udao = UsuarioDAOImplementation.getInstance();
		Usuario usuario = udao.read(email);
		byte[] foto = output.toByteArray();
		usuario.setPhoto( foto );
		udao.update( usuario );
		
		req.getSession().setAttribute( "usuario" , usuario );
		resp.sendRedirect(req.getContextPath() + "/UserServlet?email=" + email + "&errorTag=000000");

	}
}