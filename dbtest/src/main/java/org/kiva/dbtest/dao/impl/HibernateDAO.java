package org.kiva.dbtest.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.ogm.cfg.OgmConfiguration;
import org.hibernate.service.ServiceRegistry;
import org.kiva.dbtest.DbType;
import org.kiva.dbtest.dao.UserDAO;
import org.kiva.dbtest.model.User;

public class HibernateDAO implements UserDAO {

	private SessionFactory sessionFactory;
	private DbType dbType;
	private static final Logger LOG = Logger.getLogger(HibernateDAO.class);

	public HibernateDAO(DbType dbType) {
		this.dbType = dbType;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Create a session factory from the cfg.xml file
	 * 
	 * @param dbType - enum
	 */
	private void setSessionFactory(DbType dbType) {
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
			// TODO: create redis.cfg.xml
			configuration.configure("redis.cfg.xml");
			LOG.info("Hibernate Redis Configuration loaded");
			break;
		}

		configuration.addAnnotatedClass(org.kiva.dbtest.model.User.class);
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		LOG.info("Hibernate ServiceRegistry created");

		sessionFactory =  configuration.buildSessionFactory(serviceRegistry);
	}

	
	public User create(User user)
	{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		if(!session.contains(user))
		{
			session.save(user);
		}
		User loadedUser = session.get(User.class, user.getUserName());
		transaction.commit();
		return loadedUser;
	}
	
	public void delete(User user)
	{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		if(!session.contains(user))
		{
			session.delete(user);
		}
		transaction.commit();
	}
	
	public User read(String userName)
	{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		User loadedUser = session.get(User.class, userName);
		transaction.commit();
		return loadedUser;
	}
	
	public void update(User user)
	{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(user);
		transaction.commit();
	}

	@Override
	public void init() {
		setSessionFactory(dbType);
	}

	@Override
	public void destroy() {
		sessionFactory.close();
	}

	@Override
	public void deleteAll() {
		Iterator<?> it = getAll().iterator();
		Session session = sessionFactory.getCurrentSession();
		
		Transaction transaction = session.beginTransaction();
		while(it.hasNext())
		{
			delete((User)it.next());
		}
		transaction.commit();
	}
	
	public List<?> getAll()
	{
		Session session = sessionFactory.getCurrentSession();
		Transaction trans = session.beginTransaction();
		List<?> users = session.createQuery("SELECT u FROM User u").list();
		trans.commit();
		return users;
	}
}
