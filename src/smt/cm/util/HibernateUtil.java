package smt.cm.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import smt.cm.entities.Caisse;
import smt.cm.entities.CaisseSession;
import smt.cm.entities.CategoryProduct;
import smt.cm.entities.Employee;
import smt.cm.entities.ModeRegulation;
import smt.cm.entities.OutgoCaisse;
import smt.cm.entities.Product;
import smt.cm.entities.Ticket;
import smt.cm.entities.TicketHasModes;
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
					.addAnnotatedClass(Caisse.class)
					.addAnnotatedClass(Employee.class)
					.addAnnotatedClass(CaisseSession.class)
					.addAnnotatedClass(OutgoCaisse.class)
					.addAnnotatedClass(ModeRegulation.class)
					.addAnnotatedClass(TicketHasModes.class)
					.buildSessionFactory();
		} catch (Throwable ex) {
			// Log exception!
			throw new ExceptionInInitializerError(ex);
		}
	}
}
