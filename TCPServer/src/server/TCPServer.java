package server;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServer {
	//tcp port on local host port
	public static final int PORT = 3100;
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		try {
			//server socket, can also specify Host Address
			serverSocket = new ServerSocket(PORT);
			//start listening on port
			System.out.println("Listening for clients on port: " + PORT);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + PORT);
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		//create new thread pool
		ThreadPool threadPool = new ThreadPool(2);
		//call runnable method on thread pool
		threadPool.runTask(startServer(serverSocket));
		//join thread pool
		threadPool.join();
		
		//close server socket and destroy threadpool
		serverSocket.close();
		threadPool.destroy();
	}

	private static Runnable startServer(final ServerSocket socket) {
		return new Runnable() {

			@Override
			public void run() {
				//keep looping and looking for data
				while (true)
					try {
						//create new thread 
						new TCPServerThread(socket.accept()).start();
					} catch (IOException e) {
						System.out.println("Client got disconnected!" + "\nListening for clients on port: " + PORT);
					}
			}
		};
	}
}