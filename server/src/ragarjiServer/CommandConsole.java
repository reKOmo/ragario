package ragarjiServer;

import java.util.Scanner;

import sharedObj.Player;

public class CommandConsole implements Runnable{
	WorldServer ws;

	@Override
	public void run() {
		Scanner in = new Scanner(System.in);
		while (true) {
			String commd = in.nextLine();
			String[] command = commd.split(" ");
			commandExec(command);
 		}
		
	}
	
	public void setWorld(WorldServer ws) {
		this.ws = ws;
	}
	
	private void commandExec(String[] cmd) {
		switch(cmd[0]) {
			default:
				System.out.println("Not a command!!");
			case "size":
				for (Player p : ws.players) {
					if (p.name().equals(cmd[1])) {
						p.size = Integer.parseInt(cmd[2]);
						break;
					}
				}
			case "playerCount":
				System.out.println(ws.players.size());
		}
			
	}

}
