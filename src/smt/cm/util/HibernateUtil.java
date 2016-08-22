package smt.cm.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import smt.cm.entities.CategoryProduct;
import smt.cm.entities.Product;
import smt.cm.entities.Ticket;
import smt.cm.entities.TicketLine;

public class HibernateUtil {

	public static final SessionFactory sessionFactory;
	static {
		try {
			sessionFactory = new Configuration().configure()
					.addAnnotatedClass(Product.class)
					.addAnnotatedClass(CategoryProduct.class)
					.addAnnotatedClass(Ticket.class)
					.addAnnotatedClass(TicketLine.class)
					.buildSessionFactory();
		} catch (Throwable ex) {
			// Log exception!
			throw new ExceptionInInitializerError(ex);
		}
	}
}
