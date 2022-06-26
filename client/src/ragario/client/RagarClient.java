package ragario.client;

import java.net.Socket;

import ragario.DataHolder;

public class RagarClient {
	private Socket sok;
	private Reciever dataReviever;
	private Sender dataSender;

	public DataHolder gameObjectData;
	public static final String host = "localhost";
	public static final int port = 25565;

	public RagarClient(DataHolder gameData) {
		this.gameObjectData = gameData;
	}

	public void start() throws Exception {
		connect(host, port);
	}

	private void connect(String ip, int port) throws Exception {
		System.out.println("Connecting...");
		sok = new Socket(ip, port);
		dataReviever = new Reciever(sok.getInputStream(), gameObjectData);
		dataSender = new Sender(sok.getOutputStream());

		Thread receiver = new Thread(dataReviever);
		Thread sender = new Thread(dataSender);

		receiver.start();
		sender.start();

		dataSender.send("READY");

		System.out.println("Connected!");
	}
	
	public synchronized void send(double playerDirection) throws Exception {
		if (dataSender.socClosed || dataReviever.socClosed) {
			throw new Exception("Socket closed");
		} else {
			dataSender.send(playerDirection);
		}
	}
	
	public void close() throws Exception {
		dataSender.running = false;
		dataReviever.running = false;
		sok.close();
	}
}
