package ragario;

import sharedObj.Food;
import sharedObj.GameObj;
import sharedObj.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;

public class Renderer {
    private final Display window;
    private GameLogic gameLogic;

    public Renderer(Display w, GameLogic g) {
        window = w;
        window.getCanvas().createBufferStrategy(3);
        gameLogic = g;
    }

    public void render(List<GameObj> data, Player mainPlayer) {
        BufferStrategy buff = window.getCanvas().getBufferStrategy();
        if (buff == null) {
            window.getCanvas().createBufferStrategy(3);
            return;
        }
        Graphics graphics = buff.getDrawGraphics();
        graphics.setColor(Color.orange);
        graphics.fillRect(0,0,10,10);

        synchronized (data) {

            graphics.setColor(Color.red);
            graphics.fillRect(0, 0, window.getW(), window.getH());

            graphics.setColor(Color.black);
            double[] k = {0, 0};
            Point u = gameLogic.gameToScreen(k);
            int worldSize = 1000;
            graphics.fillRect(u.x, u.y, worldSize, worldSize);

            gameLogic.sort(data);
            for (GameObj ob : data) {
                Point p = gameLogic.gameToScreen(ob.getCords());
                if (ob instanceof Player) {
                    //draw player
                    if (ob == mainPlayer) {
                        graphics.setColor(Color.white);
                    } else {
                        graphics.setColor(Color.red);
                    }
                    Player pl = (Player) ob;
                    graphics.fillOval((int) (p.x - pl.sc2Diam() / 2), (int) (p.y - pl.sc2Diam() / 2), (int) pl.sc2Diam(), (int) pl.sc2Diam());
                } else if (ob instanceof Food) {
                    graphics.setColor(Color.green);
                    int foodSize = 10;
                    if (p.x - (foodSize / 2) > 0 && p.x + (foodSize / 2) < window.getW() && p.y + (foodSize / 2) < window.getH() && p.y - foodSize > 0) {
                        graphics.fillOval(p.x - (foodSize / 2), p.y - (foodSize / 2), foodSize, foodSize);
                    }
                }
            }

        }
        //show
        graphics.dispose();
        buff.show();
    }
}
