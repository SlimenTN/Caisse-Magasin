package smt.cm.controllers;

import java.sql.Statement;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;

import smt.cm.interfaces.EntityRepository;
import smt.cm.util.HibernateUtil;

public class Repository<T> implements EntityRepository<T> {
	private final String ENTITIES_PACKAGE = "smt.cm.entities";
	private final String REPO_NAME = "Repository";

	@Override
	public int create(T obj) {
		Session session = HibernateUtil.sessionFactory.openSession();
		Transaction tx = null;
		int idEntity = 0;
		try {
			tx = session.beginTransaction();
			idEntity = (Integer) session.save(obj);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return idEntity;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<T> read() {
		String className = this.getClass().getSimpleName()
				.replace(REPO_NAME, "");
		List<T> objects = null;
		Session session = HibernateUtil.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			objects = session.createQuery("FROM " + className).list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return objects;
	}

	@Override
	public void update(T obj) {
		Session session = HibernateUtil.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(obj);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(T obj) {
		Session session = HibernateUtil.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(obj);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	
	/**
	 * Check if connection still valid
	 * @return
	 */
	public static boolean connectionIsValid(){
		boolean isValid = true;
		Statement state;
		try {
			state = ((SessionImpl)HibernateUtil.sessionFactory.openSession().getDelegate()).connection().createStatement();
			String sql = "SELECT 1";				   
			state.executeQuery(sql);

    	} catch (Exception e){
    		isValid = false;
//    		e.printStackTrace();
		}
		return isValid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(int id) {
		String className = this.getClass().getSimpleName()
				.replace(REPO_NAME, "");
		T obj = null;
		Session session = HibernateUtil.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			obj = (T) session.get(Class.forName(ENTITIES_PACKAGE+"."+className), id);
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return obj;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public T findBy(String field, String value) {
		String className = this.getClass().getSimpleName()
				.replace("Repository", "");
		T obj = null;
		Session session = HibernateUtil.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			obj = (T) session.createCriteria(Class.forName(ENTITIES_PACKAGE+"."+className)).add(Restrictions.eq(field, value)).uniqueResult();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return obj;
	}

}
