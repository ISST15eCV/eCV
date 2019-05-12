package ecv.dao;

import java.util.Collection;

import org.hibernate.Session;

import ecv.model.Address;
import ecv.model.Studies;

public class AddressDAOImplementation implements AddressDAO{
	
	private static AddressDAOImplementation instance = null;
	private AddressDAOImplementation() {}
	public static AddressDAOImplementation getInstance() {
		if (null == instance)
			instance = new AddressDAOImplementation();
		return instance;
	}
	
	public void create(Address addr) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.save(addr);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al crear la direccion");
		} finally {
			session.close();
		}
	}
	
	public Address read(Long id) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			Address addr = session.load(Address.class, id);
			session.getTransaction().commit();
			return addr;
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer la direccion");
		} finally {
			session.close();
		}
		return null;
	}
	
	public void update(Address addr) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(addr);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al actualizar la direccion");
		} finally {
			session.close();
		}
	}
	
	public void delete(Address addr) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(addr);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al eliminar la direccion");
		} finally {
			session.close();
		}
	}
	
	public Collection<Address> readAll(){
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.createQuery("from Address").list();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer las direcciones");
		} finally {
			session.close();
		}
		return null;
	}
}
