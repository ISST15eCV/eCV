package ecv.dao;

import java.util.Collection;

import org.hibernate.Session;


import ecv.model.Studies;

public class StudiesDAOImplementation implements StudiesDAO{
	
	private static StudiesDAOImplementation instance = null;
	private StudiesDAOImplementation() {}
	public static StudiesDAOImplementation getInstance() {
		if (null == instance)
			instance = new StudiesDAOImplementation();
		return instance;
	}
	
	public void create(Studies studies) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.save(studies);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al crear la info de estudios");
		} finally {
			session.close();
		}	
	}
	
	public Studies read(Long id) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			Studies studies = session.load(Studies.class, id);
			session.getTransaction().commit();
			return studies;
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer la info de estudios");
		} finally {
			session.close();
		}
		return null;
	}
	
	public void update(Studies studies) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(studies);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al actualizar la info de estudios");
		} finally {
			session.close();
		}
	}
	
	public void delete(Studies studies) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(studies);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al eliminar la info de estudios");
		} finally {
			session.close();
		}
	}
	
	public Collection<Studies> readAll(){
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.createQuery("from Studies").list();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer los estudios");
		} finally {
			session.close();
		}
		return null;
	}
}
