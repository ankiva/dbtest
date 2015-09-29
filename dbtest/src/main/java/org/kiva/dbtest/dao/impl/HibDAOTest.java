package org.kiva.dbtest.dao.impl;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.kiva.dbtest.DbTest;
import org.kiva.dbtest.DbType;
import org.kiva.dbtest.Utils;
import org.kiva.dbtest.model.User;

public class HibDAOTest {
	private static final Logger LOG = Logger.getLogger(DbTest.class);
	
	public static void main(String[] args) {
		Utils.confLogger();
		
		HibernateDAO hibDAO = new HibernateDAO(DbType.parse(args[0]));
		hibDAO.init();
		
		Iterator<?> it = hibDAO.getAll().iterator();
		LOG.info("===Existing users:===");
		while(it.hasNext())
		{
			LOG.info("");
			LOG.info(it.next());
			LOG.info("");
		}
		
		User u = DbTest.createUser("fuubar");
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
		
		hibDAO.destroy();
	}
}
