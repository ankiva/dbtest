package org.kiva.dbtest.dao;

import org.kiva.dbtest.model.Role;

public interface RoleDAO {

	void init();
	
	void destroy();
	
	Role create(Role role);
}
