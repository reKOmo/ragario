package sharedObj;

import java.io.Serializable;

public class Player extends GameObj implements Serializable{
	private static final long serialVersionUID = 5L;
	
	private float x;
	private float y;
	public int size;
	public String name;
	 
	public Player(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.size = 100;
		this.name = name;
	}
	
	public float sc2Diam() {
		//score to diameter
		return (float) Math.pow(size, 0.75);
	}
	
	public double[] getCords() {
		double[] a = {x, y};
		return a;
	}
	

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

}