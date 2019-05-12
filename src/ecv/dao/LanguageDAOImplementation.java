package ecv.dao;

import java.util.Collection;

import org.hibernate.Session;


import ecv.model.Language;

public class LanguageDAOImplementation implements LanguageDAO{
	
	private static LanguageDAOImplementation instance = null;
	private LanguageDAOImplementation() {}
	public static LanguageDAOImplementation getInstance() {
		if (null == instance)
			instance = new LanguageDAOImplementation();
		return instance;
	}
	
	public void create(Language language) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.save(language);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al crear el idioma");
		} finally {
			session.close();
		}
	}
	
	public Language read(Long id) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			Language language = session.load(Language.class, id);
			session.getTransaction().commit();
			return language;
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer el idioma");
		} finally {
			session.close();
		}
		return null;
	}
	
	public void update(Language language) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(language);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al actualizar el idioma");
		} finally {
			session.close();
		}
	}
	
	public void delete(Language language) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(language);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al eliminar el idioma");
		} finally {
			session.close();
		}
	}
	
	public Collection<Language> readAll(){
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.createQuery("from Language").list();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer los idiomas");
		} finally {
			session.close();
		}
		return null;
	}
}
