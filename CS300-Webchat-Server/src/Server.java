import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server implements Runnable {
	public Socket clientSocket;
	public ObjectOutputStream output;
	public ObjectInputStream input;
	
	public OnlineHandler onlineHandler;
	public OnlineNode onlineNode = null;
	
	public ServerConnectionHandler serverConnectionHandler;
	
	public RequestHandler requestHandler = new RequestHandler();
	
	public String username;
	
	public Thread serverRequestThread;
	
	public boolean sendingLogFile = false;
	
	private int updateInterval = 2000; // 5 sec
	
	public boolean loggedIn = false;
	
	public Server(OnlineHandler onlineHandler, Socket clientSocket, ServerConnectionHandler serverConnectionHandler) throws UnknownHostException, IOException{

		this.onlineHandler = onlineHandler;
		this.clientSocket = clientSocket;
		this.serverConnectionHandler = serverConnectionHandler;
		
		output = new ObjectOutputStream(clientSocket.getOutputStream());
		input = new ObjectInputStream(clientSocket.getInputStream());

		// Create a new thread to handle the requests from the client
		serverRequestThread = new Thread(new ServerRequestThread(this));
		serverRequestThread.start();
	}
	
	@Override
	public void run() {
		while(true){
			
			// Wait for a period of time before updating the client users lists
			
			try {
				Thread.sleep(updateInterval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			updateClientUsersList();
			
			serverConnectionHandler.updateConsoleLog();
			
			// If the user has disconnected, the serverRequestThread will not be alive
			if(!serverRequestThread.isAlive()){
				break;
			}
		}

		// Remove the online node for this client
		onlineHandler.onlineList.remove(onlineNode);
		
		// If we have exited the while loop, the kill this thread by returning.
		return;
	}
	
	private void updateClientUsersList(){
		String list = "ALL USERS;";
		for(OnlineNode node : onlineHandler.onlineList){
			if(node != null)
				list = list + node.getUsername() + ";";
		}
		
		String outputString = "USERS LIST:" + list;
		try {
			output.writeObject(outputString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("IO Error updating client list! Client might have disconnected.");
		}
	}

}
