package ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend;

import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AddUtilizatorSecurityService {

	private ConcurrentHashMap<String, Long> codes = new ConcurrentHashMap<>();
	private final byte[] keyBytes = ("c"+"s"+"kcsmkq322vev56").getBytes();
	
	public String getCode(String message){
		long time = System.currentTimeMillis();
		String code = new String(Base64.getEncoder().encode(("lkmcenwejcnwebh"+"_"+time+"_"+Math.random()).getBytes()));
		try {
			code = new String(Base64.getEncoder().encode(encryptMessage((message+"_"+time).getBytes("UTF8"))), "UTF8");
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchPaddingException | NoSuchAlgorithmException
				| BadPaddingException | IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		codes.put(code, time);
		return code;
	}
	
	public boolean checkCode(String message){
		maintenance();
		if (!codes.containsKey(message)) {
			return false;
		}
		if ((System.currentTimeMillis() - codes.get(message)) > 600000){
			return false;
		}
		try {
			String msg = new String(decryptMessage(Base64.getDecoder().decode(message.getBytes("UTF8"))), "UTF8");
			String parts[] = msg.split("_");
			if (System.currentTimeMillis() - new Long(parts[1]) < 600000) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private long lastMaintenance;
	
	private void maintenance() {
		if ((System.currentTimeMillis() - lastMaintenance) < 6000) {
			return;
		}
		lastMaintenance = System.currentTimeMillis();
		Enumeration<String> keys = codes.keys();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			if ((System.currentTimeMillis() - codes.get(key)) >= 600000){
				codes.remove(key);
			}
		}
	}
	
	private byte[] encryptMessage(byte[] message)
			  throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, 
	    BadPaddingException, IllegalBlockSizeException {
	  
	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	    SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
	    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	    return cipher.doFinal(message);
	}
	
	private byte[] decryptMessage(byte[] encryptedMessage) 
			  throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, 
	    BadPaddingException, IllegalBlockSizeException {
	  
	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	    SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
	    cipher.init(Cipher.DECRYPT_MODE, secretKey);
		    return cipher.doFinal(encryptedMessage);
	}
}
