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
	
	ArrayList<OnlineNode> onlineList = new ArrayList<OnlineNode>();
	ArrayList<OnlineNode> onlineListNew = new ArrayList<OnlineNode>();
	
	private static int port = 60010;
	
	private static RequestHandler requestHandler = new RequestHandler();
	
	public static void main(String[] args) {
		//runTests();
	
		receiveNewRequests();
		processRequestQueue();
		
		requestHandler.printQueue();
		System.out.println("Done");
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
	
	private static void writeToFile(String data, String filename){
		try(
				Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "utf-8"))
				){
			
			// Write to file here.
			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Add new requests to the reqest handler object
	private static void receiveNewRequests(){
		// When a new request is received, create a new thread for it
		try(
				ServerSocket serverSocket = new ServerSocket(port);
				Socket clientSocket = serverSocket.accept();
				PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				){
			String inputLine, outputLine;
			
			outputLine = "Connection recieved!";
			output.println(outputLine);
			
			while(null != (inputLine = input.readLine())){
				
				// Echo the input line
				output.println(inputLine);
				System.out.println(inputLine);

				// Stop 
				if(inputLine.equals("stop")){
					break;
				}
				
				
				InetAddress url = clientSocket.getInetAddress(); // Get the URL from the client
				String requestCode = inputLine.substring(0, 1);  // Request code is the first character in the input			
				String data = inputLine.substring(1);            // Data is everything after the request code
				
				// Add the request to the request handler
				requestHandler.createNewRequest(url, requestCode, data);
			}
			
			System.out.println("Done reading input from client.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void processRequestQueue(){
		
	}
	
	private static void runTests(){
		Tests tests = new Tests();
		tests.testRequestNode();
		tests.testOnlineNode();
	}
	
}
