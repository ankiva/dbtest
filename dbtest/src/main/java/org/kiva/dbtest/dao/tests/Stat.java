package org.kiva.dbtest.dao.tests;

public class Stat {
	
	public static final Stat EMPTY = new Stat(0,0);

	private final long time;
	private final long count;
	
	public Stat(long time, long count){
		this.time = time;
		this.count = count;
	}
	
	public Stat(long time){
		this(time, 1);
	}
	
	public Stat add(Stat stat){
		return new Stat(this.time + stat.time, this.count + stat.count);
	}
	
	public Stat add(long start){
		return new Stat(this.time + (System.currentTimeMillis() - start), this.count + 1);
	}
	
	public long avg(){
		return time / count;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Stat [time=");
		builder.append(time);
		builder.append(", count=");
		builder.append(count);
		builder.append("]");
		return builder.toString();
	}
}
