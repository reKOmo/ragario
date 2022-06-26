package sharedObj;

import java.io.Serializable;

public class Player extends GameObj implements Serializable{

	private static final long serialVersionUID = 5L;
	private float x;
	private float y;
	public int size;
	private String name;
	public static final int defaultfSize = 100;
	 
	public Player(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.size = defaultfSize;
		this.name = name;
	}
	
	public void updatePos(double[] a, int maxX, int maxY) {
		float newX = (float) (x + a[0]);
		float newY = (float) (y + a[1]);

		float diam = scoreToDiameter() / 2;

		if (newX + diam > maxX || newX - diam< 0 || newY + diam > maxY || newY - diam < 0) {
			return;
		}

		this.x = newX;
		this.y = newY;
	}
	
	public float scoreToDiameter() {
		//score to diameter
		return (float) Math.pow(size, 0.75);
	}
	
	public String name() {
		return this.name;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double[] getCords() {
		double[] a = {x, y};
		return a;
	}

}