package org.kiva.dbtest;

import org.kiva.dbtest.dao.UserDAO;
import org.kiva.dbtest.dao.impl.HibernateUserDao;
import org.kiva.dbtest.dao.impl.MongoUserDAO;
import org.kiva.dbtest.dao.impl.CassandraUserDaoImpl;
import org.kiva.dbtest.dao.impl.RedisUserDAO;

public class Environment {

	private UserDAO userDAO;

	public void init(DbType dbType, DbType dbSubType) {
		switch (dbType) {
		case CASSANDRA:
			initCassandra();
			break;
		case MONGODB:
			initMongoDb();
			break;
		case REDIS:
			initRedis();
			break;
		case HIBERNATE:
			initHibernate(dbSubType);
			break;
		}
	}

	private void initCassandra() {
		userDAO = new CassandraUserDaoImpl();
		userDAO.init();
	}

	private void initMongoDb() {
		userDAO = new MongoUserDAO();
		userDAO.init();
	}

	private void initRedis() {
		userDAO = new RedisUserDAO();
		userDAO.init();
	}
	
	private void initHibernate(DbType dbSubType){
		if(dbSubType != null){
			userDAO = new HibernateUserDao(dbSubType);
			userDAO.init();
		} else {
			throw new IllegalArgumentException("arg 2 must be given for hibernate");
		}
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}
}
