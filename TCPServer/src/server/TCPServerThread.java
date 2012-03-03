package server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class TCPServerThread extends Thread {
	private Socket socket = null;
	//constructor
	public TCPServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {

		try {
			//read data into buffered stream
			BufferedInputStream stream = new BufferedInputStream(
					socket.getInputStream());
			//create music player object with buffered stream
			Player p = new Player(stream);
			//start playing
			p.play();
			//close socket after done playing
			socket.close();

		} catch (IOException e) {
			System.out.println("Client got disconnected!" + "\nListening for clients on port: " + TCPServer.PORT);
		} catch (JavaLayerException e) {
			System.out.println("Client got disconnected!" + "\nListening for clients on port: " + TCPServer.PORT);
		}
	}
}