package org.kiva.dbtest.dao.tests;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class StatHolder {

	public static enum Type {
		CREATE, READ, UPDATE, DELETE, ITER;
	}

	private static final Logger LOG = Logger.getLogger(StatHolder.class);

	private final Map<Type, Stat> stats = new HashMap<Type, Stat>();

	public StatHolder() {
		reset();
	}

	public void reset() {
		for (Type t : Type.values()) {
			stats.put(t, Stat.EMPTY);
		}
	}

	public void add(Type type, Stat stat) {
		stats.put(type, stats.get(type).add(stat));
	}

	public void addCreate(Stat stat) {
		add(Type.CREATE, stat);
	}

	public void addRead(Stat stat) {
		add(Type.READ, stat);
	}

	public void addUpdate(Stat stat) {
		add(Type.UPDATE, stat);
	}

	public void addDelete(Stat stat) {
		add(Type.DELETE, stat);
	}

	public void addIter(Stat stat) {
		add(Type.ITER, stat);
	}
	
	public void logStats(){
		for(Type t : Type.values()){
			Stat stat = stats.get(t);
			LOG.info(t + " stat: totals=" + stat + "; avg=" + (stat.avg()));
		}
	}
}
