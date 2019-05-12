package ecv.dao;

import java.util.Collection;

import ecv.model.Address;

public interface AddressDAO {
	public void create(Address addr);
	
	public Address read(Long id);
	
	public void update(Address addr);
	
	public void delete(Address addr);
	
	public Collection<Address> readAll();
}
