package ecv.dao;

import java.util.Collection;

import ecv.model.*;

public interface CVDAO {

	public void create(CV cv) throws Exception;
	
	public CV read(Long id);
	
	public void update(CV cv);
	
	public void delete(CV cv);
	
	public Collection<CV> readAll();
	
}
