package smt.cm.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import smt.cm.interfaces.EntityRepository;
import smt.cm.util.HibernateUtil;

public class Repository<T> implements EntityRepository<T> {

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
				.replace("Repository", "");
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

}
