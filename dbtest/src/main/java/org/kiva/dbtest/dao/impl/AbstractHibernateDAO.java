package org.kiva.dbtest.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.ogm.cfg.OgmConfiguration;
import org.hibernate.service.ServiceRegistry;
import org.kiva.dbtest.DbType;

public class AbstractHibernateDAO {

	private static final Logger LOG = Logger
			.getLogger(AbstractHibernateDAO.class);

	private static SessionFactory sessionFactory;
	private DbType dbType;

	public AbstractHibernateDAO(DbType dbType) {
		this.dbType = dbType;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Create a session factory from the cfg.xml file
	 * 
	 * @param dbType
	 *            - enum
	 */
	private void setSessionFactory(DbType dbType) {
		if (sessionFactory == null) {
			Configuration configuration = new OgmConfiguration();

			switch (dbType) {
			case CASSANDRA:
				// TODO: create cassandra.cfg.xml
				configuration.configure("cassandra.cfg.xml");
				LOG.info("Hibernate Cassandra Configuration loaded");
				break;
			case MONGODB:
				configuration.configure("mongodb.cfg.xml");
				LOG.info("Hibernate MongoDB Configuration loaded");
				break;
			case REDIS:
				configuration.configure("redis.cfg.xml");
				LOG.info("Hibernate Redis Configuration loaded");
				break;
			}

			configuration.addAnnotatedClass(org.kiva.dbtest.model.User.class);
			configuration.addAnnotatedClass(org.kiva.dbtest.model.Company.class);
			configuration.addAnnotatedClass(org.kiva.dbtest.model.Role.class);
			
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			LOG.info("Hibernate ServiceRegistry created");

			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
	}

	public void init() {
		setSessionFactory(dbType);
	}

	public void destroy() {
		sessionFactory.close();
	}
}
