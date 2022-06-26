package ragario.client;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sender implements Runnable {
    private final ObjectOutputStream obOut;
    private final List<Object> toSend;
    public boolean running = true;

    public boolean socClosed = false;

    public Sender(OutputStream out) throws IOException {
        obOut = new ObjectOutputStream(new BufferedOutputStream(out));
        toSend = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public synchronized void run() {
        synchronized (this) {
            while (running) {
                while (toSend.size() == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (Object b : toSend) {
                    try {
                        obOut.writeObject(b);
                        obOut.flush();
                    } catch (IOException e) {
                        running = false;
                        socClosed = true;
                        break;
                    }
                }

                toSend.clear();
                this.notify();
            }
        }

    }

    public void send(Object ob) {
        synchronized (this) {
            toSend.add(ob);
            this.notify();
        }
    }
}
