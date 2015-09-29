package org.kiva.dbtest.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.kiva.dbtest.DbTest;
import org.kiva.dbtest.DbType;
import org.kiva.dbtest.Utils;
import org.kiva.dbtest.model.Company;
import org.kiva.dbtest.model.Role;
import org.kiva.dbtest.model.User;

public class HibDAOTest {
	private static final Logger LOG = Logger.getLogger(DbTest.class);
	
	private static HibernateDAO hibDAO;
	private static HibernateCompanyDao hibCompanyDAO;
	private static HibernateRoleDao hibRoleDAO;
	
	public static void main(String[] args) {
		Utils.confLogger();
		
		hibDAO = new HibernateDAO(DbType.parse(args[0]));
		hibDAO.init();
		
		hibCompanyDAO = new HibernateCompanyDao(DbType.parse(args[0]));
		hibCompanyDAO.init();
		
		hibRoleDAO = new HibernateRoleDao(DbType.parse(args[0]));
		hibRoleDAO.init();
		
		Iterator<?> it = hibDAO.getAll().iterator();
		LOG.info("===Existing users:===");
		while(it.hasNext())
		{
			LOG.info("");
			LOG.info(it.next());
			LOG.info("");
		}
		
//		test1();
		test2();
		
		hibDAO.destroy();
		hibCompanyDAO.destroy();
	}
	
	private static void test1(){
		User u = DbTest.createUser("kaltsakas1");
		hibDAO.create(u);
		User fuubar = hibDAO.read(u.getUserName());
		assert fuubar != null;
		LOG.info("===Created user: "+u+"===");
		
		fuubar.setLastName("Vsevolod-Hui");
		hibDAO.update(fuubar);
		User updatedUser = hibDAO.read(u.getUserName());
		assert updatedUser.getLastName().equals("Vsevolod-Hui");
		LOG.info("===Updated user: "+updatedUser+"===");
		
		hibDAO.delete(updatedUser);
		assert hibDAO.read("fuubar") == null;
		LOG.info("===Deleted user: "+updatedUser+"===");
		
	}
	
	private static void test2(){
		Company company = DbTest.createCompany("goggle");
		
		Company comp = hibCompanyDAO.create(company);
		
		LOG.info("comp: " + comp);
		
		Role role1 = DbTest.createRole("view");
		LOG.info("role1: " + hibRoleDAO.create(role1));
		Role role2 = DbTest.createRole("edit");
		LOG.info("role2:" + hibRoleDAO.create(role2));
		
		User u = DbTest.createUser("mullo");
		u.setCompany(company);
		u.setRoles(new ArrayList<Role>());
		u.getRoles().add(role1);
		u.getRoles().add(role2);
		
		hibDAO.create(u);
		User fuubar = hibDAO.read(u.getUserName());
		assert fuubar != null;
		LOG.info("===Created user: "+u+"===");
		
		fuubar.setLastName("Vsevolod-Hui");
		hibDAO.update(fuubar);
		User updatedUser = hibDAO.read(u.getUserName());
		assert updatedUser.getLastName().equals("Vsevolod-Hui");
		LOG.info("===Updated user: "+updatedUser+"===");
		
//		hibDAO.delete(updatedUser);
//		assert hibDAO.read("fuubar") == null;
//		LOG.info("===Deleted user: "+updatedUser+"===");
	}
	
}
