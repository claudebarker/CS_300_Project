import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server implements Runnable {
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	
	MainServer mainServer;
	
	public boolean loggedIn = false;
	
	public Server(MainServer mainServer) throws UnknownHostException, IOException{
		ServerSocket serverSocket = new ServerSocket(mainServer.port);
		Socket clientSocket = serverSocket.accept();
		PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		this.mainServer = mainServer;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
