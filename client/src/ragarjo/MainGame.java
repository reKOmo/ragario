package ragarjo;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import sharedObj.*;

public class MainGame {
	static int TFps = 30;
	private static Graphics gr;
	public static Display window;
	public static ArrayList<GameObj> data;
	static Player player;
	static public double[] dir;

	public static void main(String[] args) {
		window = new Display(1280, 720, "Ragar.io");
		System.out.println("wow");
		try {
			mainLoop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void mainLoop() throws Exception {
		System.out.println("Hit main");
		boolean run = true;
		final long frTime = 1000000000 / TFps;
		long beginLoopTime;
		
		//AnsiEditorMain client connection
		RagarClient cl = new RagarClient();
		cl.connect();

		System.out.println("About to loop");
		while (run) {
			beginLoopTime = System.nanoTime();
			
			//get mouse position
			int mouseX = MouseInfo.getPointerInfo().getLocation().x - window.getCanvas().getLocationOnScreen().x;
			int mouseY = MouseInfo.getPointerInfo().getLocation().y - window.getCanvas().getLocationOnScreen().y;
			Point pt = new Point(mouseX, mouseY);
			
			//calculate and send data
			if (player != null) {
				cl.send(GameLogic.playerDir(player, pt));
			}
			
			
			//get data
			cl.retrieve();
			
			
			//game logic
			GameLogic.main();
			
			
			//draw
			if (data != null) {
				render();
			}
			
			//wait
			try {
				//System.out.println((System.nanoTime() - beginLop)/1000000);
				long timeTookForFrame = beginLoopTime - System.nanoTime();
				if (Math.abs(timeTookForFrame) < frTime)
    				Thread.sleep((timeTookForFrame + frTime) / 1000000);
    		} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		cl.close();
	}
	
	public static void render() {
		BufferStrategy buff = window.getCanvas().getBufferStrategy();
		if (buff == null) {
			window.getCanvas().createBufferStrategy(3);
			return;
		}
		gr = buff.getDrawGraphics();
		gr.setColor(Color.red);
		gr.fillRect(0, 0, window.getW(), window.getH());
		
		gr.setColor(Color.black);
		double[] k = {0, 0};
		Point u = GameLogic.gameToScreen(k);
		gr.fillRect(u.x, u.y, 10000, 10000);
		
		//render
		GameLogic.cam.updatePos(player);
		GameLogic.sort(data, 0, data.size() - 1);
		for (GameObj ob : data) {
			//main player
			if (ob == player) {
				Point p = GameLogic.gameToScreen(player.getCords());
				gr.setColor(Color.white);
				gr.fillOval((int) (p.x - player.sc2Diam() / 2), (int) (p.y - player.sc2Diam() / 2), (int) player.sc2Diam(),(int) player.sc2Diam());
			} else if (ob instanceof Food) {
				gr.setColor(Color.green);
				Point b = GameLogic.gameToScreen(ob.getCords());
				if (b.x + 10 > 0 && b.x - 5 < 1280 && b.y - 10 < 720 && b.y + 10 > 0) {
					gr.fillOval(b.x - 5, b.y - 5, 10, 10);
				}
			} else if (ob instanceof Player) {
				gr.setColor(Color.red);
				Player bo = (Player) ob;
				Point b = GameLogic.gameToScreen(ob.getCords());
				gr.fillOval((int) (b.x - bo.sc2Diam() / 2), (int) (b.y - bo.sc2Diam() / 2), (int) bo.sc2Diam(), (int) bo.sc2Diam());
			}
		}
		
		//show
		buff.show();
		gr.dispose();
	}
}
