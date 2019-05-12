package ecv.dao;

import java.util.Collection;

import ecv.model.CareerItem;

public interface CareerItemDAO {
	public void create(CareerItem ci);
	
	public CareerItem read(Long id);
	
	public void update(CareerItem ci);
	
	public void delete(CareerItem ci);
	
	public Collection<CareerItem> readAll();
}
