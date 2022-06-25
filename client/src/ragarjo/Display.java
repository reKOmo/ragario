package ragarjo;

import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JFrame;

public class Display extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private final int w;
	private final int h;
	private final String title;
	private Canvas can;
	
	public Display(int w, int h, String title) {
		this.w = w;
		this.h = h;
		this.title = title;
		init();
	}
	
	private void init() {
		JFrame win = new JFrame(title);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		can = new Canvas();
		can.setBackground(Color.black);
		can.setSize(w, h);
		win.add(can);
		win.pack();
		win.setVisible(true);
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
	
	public Canvas getCanvas() {
		return can;
	}
	
	
}
