package ecv.dao;

import java.util.Collection;

import org.hibernate.Session;

import ecv.model.CareerItem;

public class CareerItemDAOImplementation implements CareerItemDAO{
	

	private static CareerItemDAOImplementation instance = null;
	private CareerItemDAOImplementation() {}
	public static CareerItemDAOImplementation getInstance() {
		if (null == instance)
			instance = new CareerItemDAOImplementation();
		return instance;
	}
	
	public void create(CareerItem ci) {
		Session session = SessionFactoryService.get().openSession();
		try {
			System.out.println("Llega bien al try");
			session.beginTransaction();
			session.save(ci);
			System.out.println("Llega bien al guardado");
			session.getTransaction().commit();
			System.out.println("Llega bien al commit");
		} catch (Exception e) {
			System.out.println("Ha habido un error al crear el elemento");
		} finally {
			session.close();
		}
	}
	
	public CareerItem read(Long id) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			CareerItem ci = session.load(CareerItem.class, id);
			session.getTransaction().commit();
			return ci;
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer el elemento");
		} finally {
			session.close();
		}
		return null;
	}
	
	public void update(CareerItem ci) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(ci);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al actualizar el elemento");
		} finally {
			session.close();
		}
	}
	
	public void delete(CareerItem ci) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(ci);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al eliminar el elemento");
		} finally {
			session.close();
		}
	}
	
	public Collection<CareerItem> readAll(){
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.createQuery("from CareerItem").list();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer los elementos");
		} finally {
			session.close();
		}
		return null;
	}
}
