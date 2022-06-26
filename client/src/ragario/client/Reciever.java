package ragario.client;

import ragario.DataHolder;
import sharedObj.Packet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class Reciever implements Runnable {
    private final ObjectInputStream obIn;
    private final DataHolder storage;
    public boolean running = true;

    public boolean socClosed = false;

    public Reciever(InputStream in, DataHolder dataStorage) throws IOException {
        obIn = new ObjectInputStream(new BufferedInputStream(in));
        storage = dataStorage;
    }

    @Override
    public void run() {
        try {
            Object readObjects;
            readObjects = obIn.readObject();
            while (readObjects != null && running) {
                Packet readPacket = (Packet) readObjects;
                storage.setGameObjects(readPacket.data);
                storage.getGameObjects().add(readPacket.main);
                storage.setPlayer(readPacket.main);
                readObjects = obIn.readObject();
            }
        } catch (IOException e) {
            socClosed = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
