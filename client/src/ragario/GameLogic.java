package ragario;

import java.awt.Point;
import java.util.List;

import sharedObj.*;

public class GameLogic {
	public Display dis = MainGame.window;
	public  Camera cam;
	private final DataHolder gameData;

	public GameLogic(DataHolder data) {
		cam = new Camera(MainGame.window.getW(), MainGame.window.getH());
		gameData = data;
	}
	
	public void main() {
		cam.updatePos(gameData.getPlayer());
	}
	
	public double playerDir(Point b) {
		double x = b.x - (dis.getW() >> 1);
		double y = b.y - (dis.getH() >> 1);
		return Math.atan2(y, x);
	}
	
	public Point gameToScreen(double[] cord) {
		int x = (int) ((cord[0]) - cam.getX() + dis.getW()/2);
		int y = (int) ((cord[1]) - cam.getY() + dis.getH()/2);
		Point p = new Point(x, y);
		return p;
	}
	
	public void sort(List<GameObj> a) {
		a.sort((o1, o2) -> {
			int size1 = 0;
			int size2 = 0;
			if (o1 instanceof Player) size1 = ((Player) o1).size;
			if (o2 instanceof Player) size2 = ((Player) o2).size;

			return size1 - size2;
		});
	}
}



class Camera {
	private int x;
	private int y;
	private double scale;
	
	public Camera(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public double getScale() {
		return scale;
	}
	
	public void updatePos(Player p) {
		this.x = (int) (p.getX());
		this.y = (int) (p.getY());
	}
}



