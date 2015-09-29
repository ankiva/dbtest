package org.kiva.dbtest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

public class Utils {
	
	private static final SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static void confLogger(){
//		PropertyConfigurator.
//		BasicConfigurator.configure();
//		System.setProperty("log4j.rootLogger.level", "INFO");
//		System.setProperty("log4j.org.kiva.level", "DEBUG");
	}
	
	public static Date parse(String date){
		synchronized (format) {
			try{
				return format.parse(date);
			} catch(ParseException e){
				throw new RuntimeException("Failed to parse " + date, e);
			}
		}
	}
	
	public static String format(Date date){
		synchronized (format) {
			return format.format(date);
		}
	}
}
