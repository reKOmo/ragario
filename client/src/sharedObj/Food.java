package sharedObj;

import java.io.Serializable;

public class Food extends GameObj implements Serializable{
	private static final long serialVersionUID = 4L;
	
	public int x;
	public int y;
	 public Food(int x, int y) {
		 this.x = x;
		 this.y = y;
	 }
	 
	 public double getX() {
		 return this.x;
	 }
	 
	 public double getY() {
		 return this.y;
	 }
	 
	 public double[] getCords() {
		 double[] a = {x, y};
		 return a;
	 }

}
