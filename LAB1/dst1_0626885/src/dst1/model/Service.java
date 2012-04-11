package dst1.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

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
			Calendar cal = Calendar.getInstance();
			cal.set(2012, 02, 02);
			date = cal.getTime();
		}
		return date;
	}
}
