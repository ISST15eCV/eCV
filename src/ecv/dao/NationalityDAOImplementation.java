package ecv.dao;

import java.util.Collection;

import org.hibernate.Session;

import ecv.model.Nationality;


public class NationalityDAOImplementation implements NationalityDAO{
	
	private static NationalityDAOImplementation instance = null;
	private NationalityDAOImplementation() {}
	public static NationalityDAOImplementation getInstance() {
		if (null == instance)
			instance = new NationalityDAOImplementation();
		return instance;
	}
	
	public void create(Nationality nationality) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.save(nationality);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al crear la direccion");
		} finally {
			session.close();
		}
	}
	
	public Nationality read(Long id) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			Nationality nationality = session.load(Nationality.class, id);
			session.getTransaction().commit();
			return nationality;
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer la direccion");
		} finally {
			session.close();
		}
		return null;
	}
	
	public void update(Nationality nationality) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(nationality);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al actualizar la direccion");
		} finally {
			session.close();
		}
	}
	
	public void delete(Nationality nationality) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(nationality);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al eliminar la direccion");
		} finally {
			session.close();
		}
	}
	
	public Collection<Nationality> readAll(){
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.createQuery("from Nationality").list();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer las direcciones");
		} finally {
			session.close();
		}
		return null;
	}
}
