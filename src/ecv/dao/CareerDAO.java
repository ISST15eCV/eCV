package ecv.dao;

import java.util.Collection;

import ecv.model.Career;


public interface CareerDAO {
	public void create(Career career);
	
	public Career read(Long id);
	
	public void update(Career career);
	
	public void delete(Career career);
	
	public Collection<Career> readAll();
}
