package ecv.dao;

import java.util.Collection;

import ecv.model.Language;


public interface LanguageDAO {
	public void create(Language language);
	
	public Language read(Long id);
	
	public void update(Language language);
	
	public void delete(Language language);
	
	public Collection<Language> readAll();
}
