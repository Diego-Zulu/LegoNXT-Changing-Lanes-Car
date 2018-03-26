package btHandling;
import intelligentCar.LCDHandler;

import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;


public class BTConnectionHandler {

	private NXTConnection btc;
	
	private static BTConnectionHandler instance;
	
	public static BTConnectionHandler getInstance() {
		if (instance == null) {
			instance = new BTConnectionHandler();
		}
		return instance;
	}
	
	public void startConnection() {
		  btc = Bluetooth.waitForConnection(0, NXTConnection.RAW);
	}
	
	public String receiveMessage() throws IOException {
		
		  DataInputStream dis = btc.openDataInputStream();
		  String message = "";
		  
		  while (dis.available() > 0) {
			  
		    char c = (char) (dis.readByte() & 0xFF);
		    message += c;
		  }

		  dis.close();
		  
		  return message.trim().toUpperCase();
	}
	
	
	public void closeConnection() throws InterruptedException {
		  btc.close();
		  Thread.sleep(100); 
	}
}
