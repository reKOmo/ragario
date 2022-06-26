package ragario;

import sharedObj.GameObj;
import sharedObj.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataHolder {
    public List<GameObj> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(ArrayList<GameObj> gameObjects) {
        this.gameObjects = Collections.synchronizedList(gameObjects);
    }

    private List<GameObj> gameObjects = new ArrayList<>();

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private Player player;
}
