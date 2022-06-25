package ragarjiServer;

import sharedObj.*;

public class GameFunctions {
	public static double[] calcPos(Player p, double a) {
		double[] d = new double[2];
		double g = 40 * Math.pow(p.size, -0.4);
		d[0] = g * Math.cos(a);
		d[1] = g * Math.sin(a);
		return d;
	}
	
	public static double distance(GameObj a, GameObj b) {
		double g = Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2);		
		return g;
	}
	
	public static int chunksFromPlayer(Player a) {
		int i;
		if (a.size > 360) {
			i = (int) 1 + (a.size * 2) / 100;
		} else {
			i = 8;
		}
		return i;
	}
}