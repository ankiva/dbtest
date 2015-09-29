package org.kiva.dbtest.dao;

import org.kiva.dbtest.model.Company;

public interface CompanyDAO {

	void init();
	
	void destroy();
	
	Company create(Company company);
	
	Company read(String name);
	
	void update(Company company);
	
	void delete(Company company);
	
	void deleteAll();
}
