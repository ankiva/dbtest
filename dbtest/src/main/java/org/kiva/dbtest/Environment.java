package org.kiva.dbtest;

import org.kiva.dbtest.dao.UserDAO;

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

	}

	private void initMongoDb() {

	}

	private void initRedis() {

	}

	public UserDAO getUserDAO() {
		return userDAO;
	}
}
