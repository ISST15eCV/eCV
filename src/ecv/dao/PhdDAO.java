package ecv.dao;

import java.util.Collection;

import ecv.model.Phd;


public interface PhdDAO {
	public void create(Phd phd);
	
	public Phd read(Long id);
	
	public void update(Phd phd);
	
	public void delete(Phd phd);
	
	public Collection<Phd> readAll();
}
