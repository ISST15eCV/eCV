package ecv.dao;

import java.util.Collection;

import org.hibernate.Session;
import ecv.model.*;
import ecv.dao.SessionFactoryService;

public class CVDAOImplementation implements CVDAO{

	private static CVDAOImplementation instance = null;
	private CVDAOImplementation() {}
	public static CVDAOImplementation getInstance() {
		if (null == instance)
			instance = new CVDAOImplementation();
		return instance;
	}
	
	
	@Override
	public void create(CV cv) throws Exception {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.save(cv);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al crear el Usuario");
			throw new Exception("ha habido un error al crear al usuario");
		} finally {
			session.close();
		}	
	}

	@Override
	public CV read(Long id) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			CV cv = session.load(CV.class, id);
			session.getTransaction().commit();
			return cv;
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer al Usuario");
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public void update(CV cv) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(cv);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al actualizar el Usuario");
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(CV cv) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(cv);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al eliminar al Usuario");
		} finally {
			session.close();
		}
	}

	@Override
	public Collection<CV> readAll() {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.createQuery("from CV").list();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer los Usuarios");
		} finally {
			session.close();
		}
		return null;
	}

}
