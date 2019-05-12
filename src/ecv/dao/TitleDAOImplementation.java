package ecv.dao;

import java.util.Collection;

import org.hibernate.Session;

import ecv.model.Language;
import ecv.model.Title;

public class TitleDAOImplementation implements TitleDAO{
	
	private static TitleDAOImplementation instance = null;
	private TitleDAOImplementation() {}
	public static TitleDAOImplementation getInstance() {
		if (null == instance)
			instance = new TitleDAOImplementation();
		return instance;
	}
	
	public void create(Title title) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.save(title);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al crear el titulo");
		} finally {
			session.close();
		}
	}
	
	public Title read(Long id) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			Title title = session.load(Title.class, id);
			session.getTransaction().commit();
			return title;
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer el titulo");
		} finally {
			session.close();
		}
		return null;
	}
	
	public void update(Title title) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(title);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al actualizar el titulo");
		} finally {
			session.close();
		}
	}
	
	public void delete(Title title) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(title);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al eliminar el titulo");
		} finally {
			session.close();
		}
	}
	
	public Collection<Title> readAll(){
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.createQuery("from Title").list();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer los titulos");
		} finally {
			session.close();
		}
		return null;
	}
}
