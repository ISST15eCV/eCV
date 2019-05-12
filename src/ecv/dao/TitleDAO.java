package ecv.dao;

import java.util.Collection;

import ecv.model.Title;

public interface TitleDAO {
	public void create(Title title);
	
	public Title read(Long id);
	
	public void update(Title title);
	
	public void delete(Title title);
	
	public Collection<Title> readAll();
}
