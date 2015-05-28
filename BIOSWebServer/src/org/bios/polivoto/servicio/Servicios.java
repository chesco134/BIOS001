package org.bios.polivoto.servicio;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Servicios {

	private static DBConnection dbConnection;
	protected static final String usr = "root";
	protected static final String psswd = "sugar";
	protected SHAExample cypher;

	public Servicios() {
		dbConnection = new DBConnection();
		cypher = new SHAExample();
	}
	
	public String validateUser(String nombreUsuario, String usrPsswd){
		String veredicto = null;
		try{
			CallableStatement pstmnt = dbConnection.makeConnection(usr, Servicios.psswd).
					prepareCall("{Call validateUser(?,?,?)}");
			pstmnt.setNString(1, purifyString(nombreUsuario));
			//byte[] cypheredBytes = new Cifrado(new SHAExample().makeHash("LegendaryCollection")).cipher("sharPedo319");
			pstmnt.setBinaryStream(2, new ByteArrayInputStream(new Cifrado(new String(new SHAExample().makeHash("LegendaryCollection"))).cipher(usrPsswd)));
			pstmnt.registerOutParameter(3, java.sql.Types.BLOB);
			pstmnt.executeUpdate();
			byte[] bytes = new byte[(int) pstmnt.getBlob(3).length()];
			new DataInputStream(pstmnt.getBlob(3).getBinaryStream()).read(bytes);
			veredicto = bytes.toString();
			dbConnection.closeConnection();
		}catch(IOException | SQLException e){
			veredicto = "Log: " + e.toString();
			e.printStackTrace();
		}
		System.out.println("El veredicto es: " + veredicto);
		return veredicto;
	}

	public String unlockUser(String usrName, String usrPassword, String usrToUnlock){
		String veredicto = null;
		try{
			PreparedStatement pstmnt = dbConnection.makeConnection(usrName, usrPassword)
			.prepareCall("unlockUser(?,?)");
			pstmnt.setNString(1, purifyString(usrToUnlock));
			pstmnt.setNString(2, purifyString(veredicto));
			pstmnt.execute();
			dbConnection.closeConnection();			
		}catch(SQLException e){
			System.out.println("Log: " + e.toString());
		}
		return veredicto;
	}
	
	public static String purifyString(String str){
		String purifiedString = "";
		byte[] strBytes = str.getBytes();
		byte[] newStrBytes = new byte[str.length()];
		int newStrBytesIndex = 0;
		int minusCount = 0;
		for(int i = 0; i<strBytes.length; i++){
			if( strBytes[i] != '\'' && strBytes[i] != '\"' ){
				if( strBytes[i] != '-' ){
					newStrBytes[newStrBytesIndex] = strBytes[i];
					newStrBytesIndex++;
				}else{
					if( minusCount == 0 ){
						newStrBytes[newStrBytesIndex] = strBytes[i];
						newStrBytesIndex++;
					}else{
						minusCount = 0;
					}
				}
			}
		}
		purifiedString = new String(newStrBytes);
		return purifiedString;
	}

	public String startService(String usr, String psswd) {
		String finishMessage = "";
		if (validateUser(usr, psswd).equals("Welcome")) {
		} else {
		}
		return finishMessage;
	}
}