package ragarjiServer;

import sharedObj.Food;
import sharedObj.Packet;
import sharedObj.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private final ObjectInputStream got;
    private final ObjectOutputStream send;
    private final int name;
    double playerAngle;
    boolean run = true;

    private final WorldServer ws;

    public ClientHandler(ObjectInputStream in, ObjectOutputStream out, int num, WorldServer ws) {
        this.got = in;
        this.send = out;
        this.name = num;
        this.ws = ws;
    }

    @Override
    public void run(){
        //player creation
        Player play = ws.createPlayer(Integer.toString(name));

        if (play == null) {
            run = false;
        }

        String connectMessage = null;
        try {
            connectMessage = got.readObject().toString();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (connectMessage == null || !connectMessage.equals("READY")) {
            run = false;
        }

        while (run) {
            try {
                synchronized (ws.players) {
                    if (!ws.players.contains(play)) {
                        run = false;
                        break;
                    }
                }

                play.updatePos(GameFunctions.calcPos(play, playerAngle), 1000, 1000);

                //eat
                float playerDiameter = play.scoreToDiameter();
                int chunkDataArea = (int) (Math.floor((playerDiameter / ws.chunkSize)) + 1);
                ArrayList<Food> fd = ws.getFoodFromPl(play, chunkDataArea, chunkDataArea);
                for (Food a : fd) {
                    if (GameFunctions.distance(play, a) < Math.pow(playerDiameter / 2, 2)) {
                        play.size++;
                        synchronized (ws.chunks[(int) (a.getX() / 100)][(int) (a.getY() / 100)]) {
                            ws.chunks[(int) (a.getX() / 100)][(int) (a.getY() / 100)].remove(a);
                            ws.createFood();
                        }
                    }
                }

                synchronized (ws.players) {
                    ArrayList<Player> pla = ws.getPlFromPlayer(play, (int) (playerDiameter / ws.chunkSize / 2) + 1);
                    for (Player pl : pla) {
                        if (GameFunctions.distance(play, pl) < Math.pow(play.scoreToDiameter() / 2, 2) && play.size > pl.size) {
                            play.size += 1;
                            ws.players.remove(pl);
                        }
                    }
                }

                int dis = GameFunctions.chunksFromPlayer(play);

                //server stuff
                Packet pc = new Packet(ws.getFoodFromPl(play, dis * 2, dis), ws.getPlFromPlayer(play, dis), play);
                send.writeObject(pc);
                send.flush();
                send.reset();
                playerAngle = (double) got.readObject();
            } catch (Exception e) {
                break;
            }
        }

        RunServer.ws.players.remove(play);
        System.out.println("Client " + name +  " has disconnected");
    }

}
