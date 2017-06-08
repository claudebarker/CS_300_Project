import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server implements Runnable {
	private Socket clientSocket;
	private PrintWriter output;
	private BufferedReader input;
	private OnlineHandler onlineHandler;
	private RequestHandler requestHandler = new RequestHandler();
	private String username;
	
	public boolean loggedIn = false;
	
	public Server(OnlineHandler onlineHandler, Socket clientSocket) throws UnknownHostException, IOException{

		this.onlineHandler = onlineHandler;
		this.clientSocket = clientSocket;
		
		output = new PrintWriter(clientSocket.getOutputStream(), true);
		input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		String inputLine, outputLine;
		
		outputLine = "Connection recieved!";
		output.println(outputLine);
		
		// Weather of not the user client has logged in
		boolean loggedIn = false;
		
		try {
			while(null != (inputLine = input.readLine())){
				
				// Skip empty input
				if(inputLine.equals("")){
					continue;
				}
				
				System.out.println("Message from client: " + inputLine);
				
				InetAddress url = clientSocket.getInetAddress(); // Get the URL from the client
				String requestCode = inputLine.substring(0, 1);  // Request code is the first character in the input			
				String data = inputLine.substring(1);            // Data is everything after the request code

				// Pass the onlineList to the request handler
				requestHandler.setOnlineList(onlineHandler.onlineList);

				// Add the request to the request handler
				System.out.println("Creating request...");
				requestHandler.createNewRequest(url, requestCode, data);

				System.out.println("Processing request...");
				String result = requestHandler.processNextRequest();

				System.out.println("result: " + result);
				output.println(result);
				
				if(!loggedIn){
					loggedIn = requestHandler.checkLoggedIn();
					
					if(loggedIn){
						username = requestHandler.getClientUsername();
						onlineHandler.onlineList.add(new OnlineNode(url, username, output));
					}
				}
				
				System.out.println();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("User " + username + " has disconnected.");
		}
		
		System.out.println("Done reading input from client " + username);
		
		return;
	}

}
