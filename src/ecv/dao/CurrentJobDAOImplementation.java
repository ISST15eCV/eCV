package ecv.dao;

import java.util.Collection;

import org.hibernate.Session;

import ecv.model.CareerItem;
import ecv.model.CurrentJob;

public class CurrentJobDAOImplementation implements CurrentJobDAO{
	

	private static CurrentJobDAOImplementation instance = null;
	private CurrentJobDAOImplementation() {}
	public static CurrentJobDAOImplementation getInstance() {
		if (null == instance)
			instance = new CurrentJobDAOImplementation();
		return instance;
	}
	
	public void create(CurrentJob cj) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.save(cj);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al crear el elemento");
		} finally {
			session.close();
		}
	}
	
	public CurrentJob read(Long id) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			CurrentJob cj = session.load(CurrentJob.class, id);
			session.getTransaction().commit();
			return cj;
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer el elemento");
		} finally {
			session.close();
		}
		return null;
	}
	
	public void update(CurrentJob cj) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(cj);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al actualizar el elemento");
		} finally {
			session.close();
		}
	}
	
	public void delete(CurrentJob cj) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(cj);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al eliminar el elemento");
		} finally {
			session.close();
		}
	}
	
	public Collection<CurrentJob> readAll(){
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.createQuery("from CurrentJob").list();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer los elementos");
		} finally {
			session.close();
		}
		return null;
	}
}
