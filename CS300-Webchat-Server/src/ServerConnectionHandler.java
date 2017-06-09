import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnectionHandler implements Runnable {
	
	public OnlineHandler onlineHandler;
	public int port = 60010;

    ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
	public MainServerApp mainServerApp;
	
	public ServerConnectionHandler(MainServerApp mainServerApp, int port){
		this.mainServerApp = mainServerApp;
		onlineHandler = new OnlineHandler();
		this.port = port;
	}

	@Override
	public void run() {
		System.setOut(new PrintStream(byteArrayStream));
		
		System.out.println("Server is starting up...");
		
		receiveNewClients();
	}
		
	private void receiveNewClients(){
		ServerSocket serverSocket = null;
		try{
			serverSocket = new ServerSocket(port);
			System.out.println("Server socket created on port: " + port);

			updateConsoleLog();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		while(true){
			try{
				Socket clientSocket = serverSocket.accept();
				
				System.out.println("Client connected. Creating thread for Server...");
				
				// Create a new thread to handle the new connection to the server.
				new Thread(new Server(onlineHandler, clientSocket, this)).start();

			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public void updateConsoleLog(){
		try {
			mainServerApp.updateConsoleLog(byteArrayStream.toString("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
