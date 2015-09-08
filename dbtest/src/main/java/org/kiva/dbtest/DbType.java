package org.kiva.dbtest;

public enum DbType {
	CASSANDRA,
	MONGODB,
	REDIS
	;
	
	public static DbType parse(String dbType){
		for(DbType type : values()){
			if(type.name().equalsIgnoreCase(dbType)){
				return type;
			}
		}
		throw new RuntimeException("Unknown type");
	}
}
