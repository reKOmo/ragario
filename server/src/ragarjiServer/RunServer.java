package ragarjiServer;

import java.io.*;
import java.net.*;

public class RunServer {
	boolean run = true;
	public static WorldServer ws;
	public static CommandConsole console;

	public static void main(String[] args) throws Exception {
		console = new CommandConsole();
		Thread cmd = new Thread(console);
		cmd.start();
		RunServer ser = new RunServer();
		ser.run();
	}
	
	public void run() throws Exception {
		System.out.println("Starting server...");
		ServerSocket serSok = new ServerSocket(25565);
		System.out.println("Server started on socket: " + serSok.getLocalPort());
		System.out.println("Creating world...");
		ws = new WorldServer(1000, 1000, 20);
		Thread world = new Thread(ws);
		world.start();
		console.setWorld(ws);
		System.out.println("Created new world");
		System.out.println("Listening for new connections");
		
		while(run) {
			Socket newClient = serSok.accept();
			System.out.println("New client connected");
			ObjectOutputStream out = new ObjectOutputStream(newClient.getOutputStream());
			out.flush();
			ObjectInputStream in = new ObjectInputStream(newClient.getInputStream());
			
			
			int id = (int) (Math.random() * 200);
			
			ClientHandler hC = new ClientHandler(in, out, id, ws);
			System.out.println("Created new handler");
			
			Thread th = new Thread(hC);
			th.start();
			System.out.println("Added new client: " + id);
		}
		
		serSok.close();
	}

}

