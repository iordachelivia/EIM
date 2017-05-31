package ro.pub.cs.systems.eim.lab08.chatservicejmdns.networkserviceoperations;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import android.util.Log;
import ro.pub.cs.systems.eim.lab08.chatservicejmdns.general.Constants;

public class ChatServer extends Thread {

	private NetworkServiceDiscoveryOperations networkServiceDiscoveryOperations = null;

	private ServerSocket serverSocket = null;

	public ChatServer(NetworkServiceDiscoveryOperations networkServiceDiscoveryOperations, int port) {
		this.networkServiceDiscoveryOperations = networkServiceDiscoveryOperations;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException ioException) {
			Log.e(Constants.TAG, "An error has occurred during server run: " + ioException.getMessage());
			if (Constants.DEBUG) {
				ioException.printStackTrace();
			}
		}
	}

	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				Log.i(Constants.TAG, "Waiting for a connection...");
				Socket socket = serverSocket.accept();
				Log.i(Constants.TAG,
						"Received a connection request from: " + socket.getInetAddress() + ":" + socket.getLocalPort());
				List<ChatClient> communicationFromClients = networkServiceDiscoveryOperations
						.getCommunicationFromClients();
				communicationFromClients.add(new ChatClient(null, socket));
				networkServiceDiscoveryOperations.setCommunicationFromClients(communicationFromClients);
			}
		} catch (IOException ioException) {
			Log.e(Constants.TAG, "An error has occurred during server run: " + ioException.getMessage());
			if (Constants.DEBUG) {
				ioException.printStackTrace();
			}
		}
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void stopThread() {
		interrupt();
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException ioException) {
			Log.e(Constants.TAG, "An error has occurred while closing server socket: " + ioException.getMessage());
			if (Constants.DEBUG) {
				ioException.printStackTrace();
			}
		}
	}

}