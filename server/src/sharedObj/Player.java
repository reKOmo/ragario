package sharedObj;

import java.io.Serializable;

public class Player extends GameObj implements Serializable{

	private static final long serialVersionUID = 5L;
	private float x;
	private float y;
	public int size;
	private String name;
	 
	public Player(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.size = 1000;
		this.name = name;
	}
	
	public void updatePos(double[] a) {
		this.x += a[0];
		this.y += a[1];
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