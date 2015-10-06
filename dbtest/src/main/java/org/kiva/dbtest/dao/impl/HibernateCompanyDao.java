package org.kiva.dbtest.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.kiva.dbtest.DbType;
import org.kiva.dbtest.dao.CompanyDAO;
import org.kiva.dbtest.model.Company;

public class HibernateCompanyDao extends AbstractHibernateDAO implements CompanyDAO {

	public HibernateCompanyDao(DbType dbType) {
		super(dbType);
	}

	public Company create(Company company)
	{
		Session session = getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		if(!session.contains(company))
		{
			session.save(company);
		}
		Company loadedCompany = session.get(Company.class, company.getName());
		transaction.commit();
		return loadedCompany;
	}
	
	public void delete(Company company)
	{
		Session session = getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		if(!session.contains(company))
		{
			session.delete(company);
		}
		transaction.commit();
	}
	
	public Company read(String name)
	{
		Session session = getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Company loadedCompany = session.get(Company.class, name);
		transaction.commit();
		return loadedCompany;
	}
	
	public void update(Company compay)
	{
		Session session = getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(compay);
		transaction.commit();
	}

	@Override
	public void deleteAll() {
		Iterator<?> it = getAll().iterator();
		Session session = getSessionFactory().getCurrentSession();
		
		Transaction transaction = session.beginTransaction();
		while(it.hasNext())
		{
			delete((Company)it.next());
		}
		transaction.commit();
	}
	
	public List<?> getAll()
	{
		Session session = getSessionFactory().getCurrentSession();
		Transaction trans = session.beginTransaction();
		List<?> users = session.createQuery("SELECT u FROM Company c").list();
		trans.commit();
		return users;
	}

}
