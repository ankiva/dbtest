package org.kiva.dbtest.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.kiva.dbtest.DbType;
import org.kiva.dbtest.dao.UserDAO;
import org.kiva.dbtest.model.User;

public class HibernateDAO extends AbstractHibernateDAO implements UserDAO {

	public HibernateDAO(DbType dbType) {
		super(dbType);
	}
	
	public User create(User user)
	{
		Session session = getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		if(!session.contains(user))
		{
			session.save(user);
		}
		User loadedUser = (User) session.get(User.class, user.getUserName());
		transaction.commit();
		return loadedUser;
	}
	
	public void delete(User user)
	{
		Session session = getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		if(!session.contains(user))
		{
			session.delete(user);
		}
		transaction.commit();
	}
	
	public User read(String userName)
	{
		Session session = getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		User loadedUser = (User) session.get(User.class, userName);
		transaction.commit();
		return loadedUser;
	}
	
	public void update(User user)
	{
		Session session = getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(user);
		transaction.commit();
	}

	@Override
	public void deleteAll() {
		Iterator<?> it = getAll().iterator();
		Session session = getSessionFactory().getCurrentSession();
		
		Transaction transaction = session.beginTransaction();
		while(it.hasNext())
		{
			delete((User)it.next());
		}
		transaction.commit();
	}
	
	public List<?> getAll()
	{
		Session session = getSessionFactory().getCurrentSession();
		Transaction trans = session.beginTransaction();
		List<?> users = session.createQuery("SELECT u FROM User u").list();
		trans.commit();
		return users;
	}
}
