package dst1.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class Service {

	private static Date date;
	
	/**
	 * Get an MD5 hash from a given String
	 * @param pass the String to calculate the hash from
	 * @return the MD5 hash corresponding to the given String
	 */
	public static byte[] getMD5Hash(String pass) {
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			byte[] data = pass.getBytes(); 
			m.update(data,0,data.length);
			return m.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get an MD5 hash as a hexadecimal String from a given String
	 * @param pass the String to calculate the hash from
	 * @return the MD5 hash in String format corresponding to the given String
	 */
	public static String getMD5HashString(String pass) {
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			byte[] data = pass.getBytes(); 
			m.update(data,0,data.length);
			BigInteger i = new BigInteger(1,m.digest());
			return String.format("%1$032X", i);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get a reference date for testing purposes (Job and Execution dates)
	 * @return the reference date
	 */
	public static Date getReferenceDate() {
		if(date == null) {
			
			date = getCurrentDate();
		}
		return date;
	}
	
	
	/**
	 * Gets the current date
	 * @return the date representing "now"
	 */
	public static Date getCurrentDate() {
		
		Date currentDate = null;
		
		// get the supported ids for GMT+01:00 (Vienna Standard Time)
		 String[] ids = TimeZone.getAvailableIDs(+1 * 60 * 60 * 1000);
		 // if no ids were returned, something is wrong. get out.
		 if (ids.length == 0)
		     throw new RuntimeException("Timezone invalid");

		  // begin output
	//	 System.out.println("Current Time");

		 // create a Vienna Standard Time time zone
		 SimpleTimeZone vdt = new SimpleTimeZone(+1 * 60 * 60 * 1000, ids[0]);

		 // set up rules for daylight savings time
		 vdt.setStartRule(Calendar.MARCH, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		 vdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 3 * 60 * 60 * 1000);

		 // create a GregorianCalendar with the Vienna Daylight time zone
		 // and the current date and time
		 Calendar calendar = new GregorianCalendar(vdt);
		 Date trialTime = new Date();
		 calendar.setTime(trialTime);
		
//		Date currentDate = new Date(System.currentTimeMillis());
//		Calendar cal1 = new GregorianCalendar();
//		Calendar cal = Calendar.getInstance();
//		cal.set(2012, 02, 02);
		 currentDate = calendar.getTime();
		 
		 return currentDate;
	}
}
