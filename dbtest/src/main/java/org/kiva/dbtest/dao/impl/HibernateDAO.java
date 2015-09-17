package org.kiva.dbtest.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.kiva.dbtest.DbTest;
import org.kiva.dbtest.Utils;
import org.kiva.dbtest.model.User;

public class HibernateDAO {

	private static final Logger LOG = Logger.getLogger(DbTest.class);
	
	public static void main(String[] args) {
		Utils.confLogger();
		//Get Session
        Session session = getSessionFactory().getCurrentSession();
        //start transaction
        session.beginTransaction();
        User emp = DbTest.createUser("test");
        //Save the Model object
        session.save(emp);
        
        LOG.info(session.contains(emp));
        
        session.delete(emp);
        //Commit transaction
        session.getTransaction().commit();

	}
	
	private static SessionFactory getSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("persistence.cfg.xml");
            LOG.info("Hibernate Mongo Configuration loaded");
             
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            LOG.info("Hibernate Mongo serviceRegistry created");
             
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
             
            return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            LOG.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

}
