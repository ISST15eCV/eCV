package ecv.dao;

import java.util.Collection;

import org.hibernate.Session;

import ecv.model.PersonalData;



public class PersonalDataDAOImplementation implements PersonalDataDAO{
	
	private static PersonalDataDAOImplementation instance = null;
	private PersonalDataDAOImplementation() {}
	public static PersonalDataDAOImplementation getInstance() {
		if (null == instance)
			instance = new PersonalDataDAOImplementation();
		return instance;
	}
	
	public void create(PersonalData pd){
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.save(pd);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al crear el Dato Personal");
		} finally {
			session.close();
		}	
	}
	
	public PersonalData read(Long id) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			PersonalData pd = session.load(PersonalData.class, id);
			session.getTransaction().commit();
			return pd;
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer al Dato Personal");
		} finally {
			session.close();
		}
		return null;
	}
	
	public void update(PersonalData pd) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(pd);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al actualizar el Dato Personal");
		} finally {
			session.close();
		}
	}
	
	public void delete(PersonalData pd) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(pd);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al eliminar al Dato Personal");
		} finally {
			session.close();
		}
	}
	
	public Collection<PersonalData> readAll(){
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.createQuery("from PersonalData").list();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer los Datos Personales");
		} finally {
			session.close();
		}
		return null;
	}
}
