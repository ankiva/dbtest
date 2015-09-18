package org.kiva.dbtest.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.ogm.cfg.OgmConfiguration;
import org.hibernate.service.ServiceRegistry;
import org.kiva.dbtest.DbTest;
import org.kiva.dbtest.Utils;
import org.kiva.dbtest.model.User;

public class HibernateDAO {

	private static final Logger LOG = Logger.getLogger(HibernateDAO.class);

	public static void main(String[] args) {
Utils.confLogger();
		
		Session session = getSessionFactory("mongodb.cfg.xml").openSession();
		
		session.beginTransaction();

		LOG.info("Populating the database...");
		User usr = DbTest.createUser("fubar");

		session.save(usr);
		LOG.info("==========================Contains: "+session.contains(usr)+"==========================");
		
		session.delete(usr);
		LOG.info("==========================Contains: "+session.contains(usr)+"==========================");
		
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * 
	 * @param confName - name of the XML config file in the resources folder
	 * @return SessionFactory
	 */
	private static SessionFactory getSessionFactory(String confName) {
		Configuration configuration = new OgmConfiguration();
		configuration.configure(confName);
		configuration.addAnnotatedClass(org.kiva.dbtest.model.User.class);
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		
		return configuration.buildSessionFactory(serviceRegistry);
	}

}
