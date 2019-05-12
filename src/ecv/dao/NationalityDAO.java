package ecv.dao;

import java.util.Collection;

import ecv.model.Address;
import ecv.model.Nationality;

public interface NationalityDAO {
	public void create(Nationality nationality);
	
	public Nationality read(Long id);
	
	public void update(Nationality nationality);
	
	public void delete(Nationality nationality);
	
	public Collection<Nationality> readAll();
}
