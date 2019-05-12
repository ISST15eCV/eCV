package ecv.dao;

import java.util.Collection;

import ecv.model.Studies;

public interface StudiesDAO {
	public void create(Studies studies);
	
	public Studies read(Long id);
	
	public void update(Studies studies);
	
	public void delete(Studies studies);
	
	public Collection<Studies> readAll();
}
