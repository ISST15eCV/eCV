package ecv.dao;

import java.util.Collection;

import org.hibernate.Session;

import ecv.model.Career;
import ecv.model.PersonalData;

public class CareerDAOImplementation implements CareerDAO{
	
	private static CareerDAOImplementation instance = null;
	private CareerDAOImplementation() {}
	public static CareerDAOImplementation getInstance() {
		if (null == instance)
			instance = new CareerDAOImplementation();
		return instance;
	}
	
	public void create(Career career) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.save(career);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al crear la situación laboral");
		} finally {
			session.close();
		}	
	}
	
	public Career read(Long id) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			Career career = session.load(Career.class, id);
			session.getTransaction().commit();
			return career;
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer la situación laboral");
		} finally {
			session.close();
		}
		return null;
	}
	
	public void update(Career career) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(career);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al actualizar la situación laboral");
		} finally {
			session.close();
		}
	}
	
	public void delete(Career career) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(career);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al eliminar la situación laboral");
		} finally {
			session.close();
		}
	}
	
	public Collection<Career> readAll(){
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.createQuery("from Career").list();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer las situaciones laborales");
		} finally {
			session.close();
		}
		return null;
	}
}
