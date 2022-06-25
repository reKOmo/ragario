package ragarjo;

import java.io.*;
import java.net.Socket;

import sharedObj.*;

public class RagarClient {
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket sok;
	
	public void connect() throws Exception {
		System.out.println("Connecting...");
		sok = new Socket("localhost", 25565);
		in =  new ObjectInputStream(new BufferedInputStream(sok.getInputStream()));
		out = new ObjectOutputStream(sok.getOutputStream());
		System.out.println("Connected!");
	}
	
	public void send(double playerDirection) throws Exception {
		out.writeObject(playerDirection);
		out.flush();
	}
	
	public void retrieve() throws Exception {
		//long time = System.nanoTime();
		Object b = in.readObject();
		//System.out.println("it took:" + (System.nanoTime() - time)/1000000);
		Packet a = (Packet) b;
		MainGame.data = a.data;
		MainGame.data.add(a.main);
		MainGame.player = a.main;
	}
	
	public void close() throws Exception {
		sok.close();
	}
		
}
