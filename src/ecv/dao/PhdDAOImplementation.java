package ecv.dao;

import java.util.Collection;

import org.hibernate.Session;

import ecv.model.Phd;


public class PhdDAOImplementation implements PhdDAO{
	
	private static PhdDAOImplementation instance = null;
	private PhdDAOImplementation() {}
	public static PhdDAOImplementation getInstance() {
		if (null == instance)
			instance = new PhdDAOImplementation();
		return instance;
	}
	
	public void create(Phd phd) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.save(phd);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al crear el titulo");
		} finally {
			session.close();
		}
	}
	
	public Phd read(Long id) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			Phd phd = session.load(Phd.class, id);
			session.getTransaction().commit();
			return phd;
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer el titulo");
		} finally {
			session.close();
		}
		return null;
	}
	
	public void update(Phd phd) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(phd);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al actualizar el titulo");
		} finally {
			session.close();
		}
	}
	
	public void delete(Phd phd) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(phd);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al eliminar el titulo");
		} finally {
			session.close();
		}
	}
	
	public Collection<Phd> readAll(){
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.createQuery("from Phd").list();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer los titulos");
		} finally {
			session.close();
		}
		return null;
	}
}
