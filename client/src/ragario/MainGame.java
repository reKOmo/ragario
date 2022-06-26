package ragario;

import java.awt.*;

import ragario.client.RagarClient;

public class MainGame {
	public static Display window;
	public RagarClient client;
	public Renderer renderer;
	public GameLogic gameLogic;

	static int TFps = 30;
	public DataHolder data;


	public void init() {
		window = new Display(1280, 720, "Ragar.io");
		data = new DataHolder();
		client = new RagarClient(data);
		gameLogic = new GameLogic(data);
		renderer = new Renderer(window, gameLogic);
	}

	public void start() throws Exception {
		client.start();
		mainLoop();
	}
	
	
	private void mainLoop() throws Exception {
		boolean run = true;
		final long frTime = 1000000000 / TFps;
		long beginLoopTime;

		while (run) {
			beginLoopTime = System.nanoTime();
			
			//get mouse position
			int mouseX = MouseInfo.getPointerInfo().getLocation().x - window.getCanvas().getLocationOnScreen().x;
			int mouseY = MouseInfo.getPointerInfo().getLocation().y - window.getCanvas().getLocationOnScreen().y;
			Point pt = new Point(mouseX, mouseY);

			//calculate and send data
			if (data.getPlayer() != null) {
				try {
					client.send(gameLogic.playerDir(pt));
				} catch (Exception e) {
					run = false;
					System.out.println("Connection closed due to death :( or server error");
				}
				//game logic
				gameLogic.main();
			}

			//draw
			if (data.getGameObjects() != null && data.getPlayer() != null) {
				renderer.render(data.getGameObjects(), data.getPlayer());
			}

			try {
				long timeTookForFrame = beginLoopTime - System.nanoTime();
				if (Math.abs(timeTookForFrame) < frTime)
    				Thread.sleep((timeTookForFrame + frTime) / 1000000);
    		} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		client.close();
	}
}
