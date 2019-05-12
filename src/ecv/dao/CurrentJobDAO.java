package ecv.dao;

import java.util.Collection;

import ecv.model.CareerItem;
import ecv.model.CurrentJob;

public interface CurrentJobDAO {
	public void create(CurrentJob cj);
	
	public CurrentJob read(Long id);
	
	public void update(CurrentJob cj);
	
	public void delete(CurrentJob cj);
	
	public Collection<CurrentJob> readAll();
}
