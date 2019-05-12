package ecv.dao;

import java.util.Collection;

import ecv.model.*;

public interface UsuarioDAO {

	public void create(Usuario user) throws Exception;
	
	public Usuario read(String email);
	
	public void update(Usuario user);
	
	public void delete(Usuario user);
	
	public Collection<Usuario> readAll();
	
}
