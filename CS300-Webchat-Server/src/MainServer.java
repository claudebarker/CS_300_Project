import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;
import java.net.InetAddress;

public class MainServer {
	
	private static ArrayList<OnlineNode> onlineList = new ArrayList<OnlineNode>();
	private static ArrayList<OnlineNode> onlineListNew = new ArrayList<OnlineNode>();
	
	public static int port = 60010;
	
	private static RequestHandler requestHandler = new RequestHandler();
	
	public static void main(String[] args) {
		
		receiveNewRequests();
		
	}
	
	private List<String> readFromFile(String filename){
	    Path path = FileSystems.getDefault().getPath("logs", "users.txt");
		try {
			return Files.readAllLines(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// Add new requests to the request handler object
	private static void receiveNewRequests(){
		// ***************
		// ***************
		// ***************
		// When a new request is received, create a new thread for it
		// ***************
		// ***************
		// ***************
		try(
			ServerSocket serverSocket = new ServerSocket(port);
			Socket clientSocket = serverSocket.accept();
			PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			){
			
			String inputLine, outputLine;
			
			outputLine = "Connection recieved!";
			output.println(outputLine);
			
			// Weather of not the user client has logged in
			boolean loggedIn = false;
			
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
				requestHandler.setOnlineList(onlineList);

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
						onlineList.add(new OnlineNode(url, requestHandler.getClientUsername(), output));
					}
				}
				
				System.out.println();
			}
			
			System.out.println("Done reading input from client.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void runTests(){
		Tests tests = new Tests();
		tests.testRequestNode();
		tests.testOnlineNode();
	}
	
}
