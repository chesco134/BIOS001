package org.bios.polivoto.servicio;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SHAExample {
	public String makeCheckSum(String fileName){
		String hexString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			FileInputStream fis = new FileInputStream(fileName);
			byte[] dataByte = new byte[1024];
			int nread = 0;
			while((nread = fis.read(dataByte)) != -1){
				md.update(dataByte, 0, nread);
			}
			byte[] mdBytes = md.digest();
			StringBuffer theHexString = new StringBuffer();
			for (int i = 0; i<mdBytes.length; i++){
				theHexString.append(Integer.toHexString(0xFF & mdBytes[i]));
			}
			hexString = theHexString.toString();
			fis.close();
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
		return hexString;
	}
	
	public byte[] makeHash(String psswd){
		byte[] byteData = null;
		StringBuffer sb = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(psswd.getBytes());
			byteData = md.digest();
			System.out.println("\n\n\t******* " + byteData.length + " *******\n\n" + "\t******* " + byteData.toString() + " *******\n\n");
			sb = new StringBuffer();
			for (int i = 0; i<byteData.length; i++ ){
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100,16).substring(1));
			}
			System.out.println(sb.toString());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return byteData;
	}
}
