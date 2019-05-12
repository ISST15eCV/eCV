package ecv.dao;

import java.util.Collection;

import ecv.model.PersonalData;

public interface PersonalDataDAO {
	
	public void create(PersonalData pd);
	
	public PersonalData read(Long id);
	
	public void update(PersonalData pd);
	
	public void delete(PersonalData pd);
	
	public Collection<PersonalData> readAll();
}
