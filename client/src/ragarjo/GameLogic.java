package ragarjo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import sharedObj.*;

public class GameLogic {
	static Display dis = MainGame.window;
	public static Camera cam = new Camera(MainGame.window.getW(), MainGame.window.getH());
	
	public static void main() {
		
	}
	
	public static double playerDir(Player a, Point b) {
		double x = b.x - dis.getW() >> 1;
		double y = b.y - dis.getH() >> 1;
		return Math.atan2(y, x);
	}
	
	public static Point gameToScreen(double[] cord) {
		int x = (int) ((cord[0]) - cam.getX() + dis.getW()/2);
		int y = (int) ((cord[1]) - cam.getY() + dis.getH()/2);
		Point p = new Point(x, y);
		return p;
	}
	
		public static void sort(ArrayList<GameObj> a, int op, int ed) {
			if (op < ed) {
				int piv = Partition(a, op, ed);
				sort(a, op, piv -1);
				sort(a, piv + 1, ed);
			}
		}
		
		private static int Partition(ArrayList<GameObj> a, int op, int ed) {
			int pivot;
			if (a.get(ed) instanceof Player) {
				Player p = (Player) a.get(ed);
				pivot = p.size;
			} else {
				pivot = 30;
			}
			int low = op - 1;
			for (int i = op; i < ed; i++) {
				int com;
				if (a.get(i) instanceof Player) {
					Player p = (Player) a.get(ed);
					com = p.size;
				} else com = 30;
				
				if (com <= pivot) {
					low ++;
					Collections.swap(a, low, i);
				}
			}
			Collections.swap(a, ed, low + 1);
			return low + 1;
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
		//zom(p.size);
		this.x = (int) (p.getX());
		this.y = (int) (p.getY());
		//System.out.println(zoom);
	}
	
//	private void zoom(float size) {
//		if (size > 300) {
//			this.scale = 300/size;
//		} else {
//			this.scale = 1;
//		}
//	}
}



