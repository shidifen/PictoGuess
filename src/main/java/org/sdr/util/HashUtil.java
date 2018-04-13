package org.sdr.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//used for renaming uploaded pictures and cookie values
//collision probability abysmal
//I don't want users download files with names based on the original ones
//or based on their age (small numbers = old files)
public class HashUtil {

	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	//MessageDigest will output non-ASCII characters, so a helper fct needed
	public static String hash (String s) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(s.getBytes());
		String toConvert = new String (messageDigest.digest());
		return bytesToHex(toConvert.getBytes());
	}

	//stolen shamelessly from StackOverflow, but I really needed unique filenames
	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
}