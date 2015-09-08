package org.kiva.dbtest;

import org.kiva.dbtest.dao.UserDAO;
import org.kiva.dbtest.dao.impl.MongoUserDAO;
import org.kiva.dbtest.dao.impl.CassandraUserDaoImpl;

public class Environment {

	private UserDAO userDAO;

	public void init(DbType dbType) {
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
		}
	}

	private void initCassandra() {
		userDAO = new CassandraUserDaoImpl();
		userDAO.init();
	}

	private void initMongoDb() {
		
	}

	private void initRedis() {

	}

	public UserDAO getUserDAO() {
		return userDAO;
	}
}
