package ecv.dao;

import java.util.Collection;

import org.hibernate.Session;
import ecv.model.*;
import ecv.dao.SessionFactoryService;

public class UsuarioDAOImplementation implements UsuarioDAO{

	private static UsuarioDAOImplementation instance = null;
	private UsuarioDAOImplementation() {}
	public static UsuarioDAOImplementation getInstance() {
		if (null == instance)
			instance = new UsuarioDAOImplementation();
		return instance;
	}
	
	
	@Override
	public void create(Usuario user) throws Exception {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al crear el Usuario");
			throw new Exception("ha habido un error al crear al usuario");
		} finally {
			session.close();
		}	
	}

	@Override
	public Usuario read(String email) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			Usuario user = session.load(Usuario.class, email);
			session.getTransaction().commit();
			return user;
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer al Usuario");
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public void update(Usuario user) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al actualizar el Usuario");
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(Usuario user) {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.delete(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al eliminar al Usuario");
		} finally {
			session.close();
		}
	}

	@Override
	public Collection<Usuario> readAll() {
		Session session = SessionFactoryService.get().openSession();
		try {
			session.beginTransaction();
			session.createQuery("from Usuario").list();
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Ha habido un error al leer los Usuarios");
		} finally {
			session.close();
		}
		return null;
	}

}
