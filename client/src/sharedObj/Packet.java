package sharedObj;

import java.io.Serializable;
import java.util.ArrayList;

public class Packet implements Serializable {
	private static final long serialVersionUID = 32L;
	
	public ArrayList<GameObj> data = new ArrayList<GameObj>();;
	public Player main;
	public Packet(ArrayList<Food> food, ArrayList<Player> player, Player pl) {
		this.data.addAll(food);
		this.data.addAll(player);
		this.main = pl;
	}
}
