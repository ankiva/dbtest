package org.kiva.dbtest;

public class DbTest {
	
	private static Environment env;
	
	public static void main(String[] args){
		DbType dbType = DbType.parse(args[0]);
		env = new Environment();
		env.init(dbType);
	}
}
