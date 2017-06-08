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
	
	public static OnlineHandler onlineHandler;
	public static int port = 60010;
	
	public static void main(String[] args) {
		onlineHandler = new OnlineHandler();
		receiveNewClients();
	}
	
	private static void receiveNewClients(){
		ServerSocket serverSocket = null;
		try{
			serverSocket = new ServerSocket(port);
			System.out.println("Server socket created on port: " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		while(true){
			try{
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client connected. Creating thread for Server...");
				
				// Create a new thread to handle the new connection to the server.
				new Thread(new Server(onlineHandler, clientSocket)).start();
			} catch (IOException e){
				e.printStackTrace();
			}
		}	
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
	
	private static void runTests(){
		Tests tests = new Tests();
		tests.testRequestNode();
		tests.testOnlineNode();
	}
	
}
