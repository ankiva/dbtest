package org.kiva.dbtest.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.kiva.dbtest.DbType;
import org.kiva.dbtest.dao.RoleDAO;
import org.kiva.dbtest.model.Company;
import org.kiva.dbtest.model.Role;

public class HibernateRoleDao extends AbstractHibernateDAO implements RoleDAO {

	public HibernateRoleDao(DbType dbType) {
		super(dbType);
	}

	@Override
	public Role create(Role role) {
		Session session = getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		if(!session.contains(role))
		{
			session.save(role);
		}
		Role loadedCompany = session.get(Role.class, role.getType());
		transaction.commit();
		return loadedCompany;
	}

}
