package ragarjiServer;

import java.util.ArrayList;

import sharedObj.*;

public class WorldServer implements Runnable{
	public boolean on = true;
	private final int w;
	private final int h;
	private final int chunksHorizontally;
	private final int chunksVertically;
	public int chunkSize = 100;
	public ArrayList<Player> players;
	public ArrayList<GameObj>[][] chunks;
	//public ArrayList<Food> fod = new ArrayList<Food>();
	public Player[] leaderboard = new Player[10];

	public WorldServer(int width, int height, int playerCount) {
		this.w = width;
		this.h = height;
		//chunk size: 100x100
		if (w % chunkSize != 0 || h % chunkSize != 0) throw new IllegalArgumentException("World size must be divideable by " + chunkSize + "!!!");
		this.chunksVertically = height / chunkSize;
		this.chunksHorizontally = width / chunkSize;
		this.chunks = new ArrayList[chunksHorizontally][chunksVertically];
		this.players = new ArrayList<>();
		init();
	}

	@Override
	public void run() {
		int ticks = 10;
		final long tickTime = 1000000000 / ticks;
		
		while(on) {
			long beginLop = System.nanoTime();
			
			
			
			
			
			try {
    			Thread.sleep((beginLop-System.nanoTime() + tickTime) / 1000000);
    		} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	private void init() {
		//initialize chunks
		for (int x = 0; x < chunksHorizontally; x++) {
			for (int y = 0; y < chunksVertically; y++) {
				chunks[x][y] = new ArrayList<>();
			}
		}

		for (int a = 0; a < 100; a++) {
			Food f = new Food((int) (Math.random() * w), (int) (Math.random() * h));
			chunks[(int) (f.getX() / chunkSize)][(int) (f.getY() / chunkSize)].add(f);
			//fod.add(f);
		}

//		for (int a = 0; a < 100; a++) {
//			createPlayer((int) (Math.random() * w), (int) (Math.random() * h), Integer.toString(a));
//		}
	}
	
	public Player createPlayer(int x, int y, String id) {
		Player p = new Player(x, y, id);
//		if (players.size() > 0) {
//			for (Player pl : players) {
//				if (p.name().equals(pl.name())) {
//					return null;
//				}
//			}
//		}
		players.add(p);
		return p;
	}
	
	
	public void addToLeaderBoard(Player p) {
		if (leaderboard[leaderboard.length - 1].size < p.size) {
			for (int i = 0; i < leaderboard.length; i++) {
			
			}
		}
	}
	//change to include players
	public ArrayList<Food> getFoodFromPl(Player p, int w, int h) {
		ArrayList<Food> food = new ArrayList<>();

		//convert position to chunk space
		int startX = (int) (p.getX() / chunkSize) - w / 2;
		int startY = (int) ((p.getY() / chunkSize) - h / 2);

		for (int x = 0; x <= w; x++) {
			for (int y = 0; y <= h; y++) {
				int xPos = startX + x;
				int yPos = startY + y;
				if (xPos >= 0 && yPos >= 0 && xPos < chunks.length && yPos < chunks[xPos].length) {
					for (Object a : chunks[xPos][yPos]) {
						food.add((Food) a);
					}
				}
			}
		}

		return food;
	}
	
	public ArrayList<Player> getPlFromPlayer(Player player, int dis) {
		dis = dis * chunkSize;
		ArrayList<Player> pl = new ArrayList<Player>();
		for (Player p : players) {
			float halfPlayerSize = p.size >> 1;
			if (p.getX() + halfPlayerSize > player.getX() - dis && p.getX() - halfPlayerSize < player.getX()
					+ dis && p.getY() - halfPlayerSize < player.getY() + dis && p.getY() + halfPlayerSize > player.getY() - dis && p != player) {
				pl.add(p);
			}
		}
		
		return pl;
	}
	
}
