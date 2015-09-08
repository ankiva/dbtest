package org.kiva.dbtest.dao;

import org.kiva.dbtest.model.User;

public interface UserDAO {

	void init();
	
	void destroy();
	
	User create(User user);
	
	User read(String userName);
	
	void update(User user);
	
	void delete(User user);
}
