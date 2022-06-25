package ragarjiServer;

import java.io.*;
import java.net.*;
import java.util.*;
import sharedObj.*;

public class RunServer {
	boolean run = true;
	ArrayList<HandleClient> clientList = new ArrayList<HandleClient>();
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
		ws = new WorldServer(100, 100, 2000);
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
			
			HandleClient hC = new HandleClient(in, out, id, newClient);
			System.out.println("Created new handler");
			
			clientList.add(hC);
			
			Thread th = new Thread(hC);
			th.start();
			System.out.println("Added new client: " + id);
		}
		
		serSok.close();
	}

}

class HandleClient implements Runnable {
	ObjectInputStream got;
	ObjectOutputStream send;
	int name;
	Socket sok;
	double g1;
	boolean run = true;
	
	public HandleClient(ObjectInputStream in, ObjectOutputStream out, int num, Socket sok) {
		this.got = in;
		this.send = out;
		this.name = num;
		this.sok = sok;
	}

	@Override
	public void run(){
		//player creation
		Player play = RunServer.ws.createPlayer(200, 300, Integer.toString(name));

		if (play == null) {
			run = false;
		}

		while (run) {
			try {
				//player stuff
				if (g1 != -69) {
					play.updatePos(GameFunctions.calcPos(play, g1));


					//eat
					float playerDiameter = play.scoreToDiameter();
					int chunkDataArea = (int) (Math.floor((playerDiameter / RunServer.ws.chunkSize)) + 1);
					ArrayList<Food> fd = RunServer.ws.getFoodFromPl(play, chunkDataArea, chunkDataArea);
					for (Food a : fd) {
						if (GameFunctions.distance(play, a) < Math.pow(playerDiameter / 2, 2)) {
							play.size++;
							RunServer.ws.chunks[(int) (a.getX() / 100)][(int) (a.getY() / 100)].remove(a);
						}
					}
					ArrayList<Player> pla = RunServer.ws.getPlFromPlayer(play, (int) (playerDiameter / RunServer.ws.chunkSize / 2) + 1);
					for (Player pl : pla) {
						if (GameFunctions.distance(play, pl) < Math.pow(play.scoreToDiameter() / 2, 2)) {
							play.size += 1;
							RunServer.ws.players.remove(pl);
						}
					}
				}

				int dis = GameFunctions.chunksFromPlayer(play);

				//server stuff
				Packet pc = new Packet(RunServer.ws.getFoodFromPl(play, dis * 2, dis), RunServer.ws.getPlFromPlayer(play, dis), play);
				send.writeObject(pc);
				send.flush();
				send.reset();
				g1 = (double) got.readObject();
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
//		if (id == -1) {
//			System.out.println("!!Server full!!");
//		}
		RunServer.ws.players.remove(play);
		System.out.println("Client " + name +  " has disconnected");
	}
	
}
