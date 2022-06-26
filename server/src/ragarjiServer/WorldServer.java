package ragarjiServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sharedObj.*;

public class WorldServer implements Runnable{
	public boolean on = true;
	private final int w;
	private final int h;
	private final int chunksHorizontally;
	private final int chunksVertically;
	public int chunkSize = 100;

	public final List<Player> players;
	public List<GameObj>[][] chunks;

	public Player[] leaderboard = new Player[10];

	public WorldServer(int width, int height, int playerCount) {
		this.w = width;
		this.h = height;
		//chunk size: 100x100
		if (w % chunkSize != 0 || h % chunkSize != 0) throw new IllegalArgumentException("World size must be divideable by " + chunkSize + "!!!");
		this.chunksVertically = height / chunkSize;
		this.chunksHorizontally = width / chunkSize;
		this.chunks = new List[chunksHorizontally][chunksVertically];
		this.players = Collections.synchronizedList(new ArrayList<>());
		init();
	}

	@Override
	public void run() {
		int ticks = 10;
		final long tickTime = 1000000000 / ticks;
		
		while(on) {
			long beginLop = System.nanoTime();
			
			//TODO
			
			
			
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
				chunks[x][y] = Collections.synchronizedList(new ArrayList<>());
			}
		}

		for (int a = 0; a < 100; a++) {
			createFood();
		}
	}
	
	public Player createPlayer(String id) {
		int x = (int) Math.floor(Math.random() * (w - Player.defaultfSize));
		int y = (int) Math.floor(Math.random() * (h - Player.defaultfSize));
		Player p = new Player(x + (Player.defaultfSize >> 1), y + (Player.defaultfSize >> 1), id);
		//TODO add id checking
		players.add(p);
		return p;
	}

	public void createFood() {
		Food f = new Food((int) (Math.random() * (w - 20)) + 10, (int) (Math.random() * (h - 20)) + 10);
		chunks[(int) (f.getX() / chunkSize)][(int) (f.getY() / chunkSize)].add(f);
	}
	
	
	public void addToLeaderBoard(Player p) {
		//TODO add to leaderboard
	}

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
					synchronized (chunks[xPos][yPos]) {
						for (Object a : chunks[xPos][yPos]) {
							food.add((Food) a);
						}
					}
				}
			}
		}

		return food;
	}
	
	public ArrayList<Player> getPlFromPlayer(Player player, int dis) {
		dis = dis * chunkSize;
		ArrayList<Player> pl = new ArrayList<Player>();
		synchronized (players) {
			for (Player p : players) {
				float halfPlayerSize = p.size >> 1;
				if (p.getX() + halfPlayerSize > player.getX() - dis && p.getX() - halfPlayerSize < player.getX()
						+ dis && p.getY() - halfPlayerSize < player.getY() + dis && p.getY() + halfPlayerSize > player.getY() - dis && p != player) {
					pl.add(p);
				}
			}
		}
		
		return pl;
	}
	
}
