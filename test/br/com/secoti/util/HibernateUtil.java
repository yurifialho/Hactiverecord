package br.com.secoti.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {
	
	private static SessionFactory factory;
	private static ServiceRegistry serviceRegistry;
	
	static {
		//factory = new AnnotationConfiguration().configure().buildSessionFactory();
		Configuration cfg = new Configuration();
			cfg.configure();
		serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		factory = cfg.buildSessionFactory(serviceRegistry);
	}
	
	public static Session getSession() throws HibernateException {
		return factory.openSession();
	}
}