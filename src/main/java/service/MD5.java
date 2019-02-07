package service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public static String generateMd5HashCode(String pass) {
		 try {
			    MessageDigest m = MessageDigest.getInstance("MD5");
			    byte[] data = pass.getBytes();
			    m.update(data, 0, data.length);
			    BigInteger i = new BigInteger(1, m.digest());
			    return String.format("%1$032X", i);
			  } catch (NoSuchAlgorithmException e) {
			    System.err.println("NoSuchAlgorithmException in MD5 -" + e);
			    return pass;
			  }


	}
}
