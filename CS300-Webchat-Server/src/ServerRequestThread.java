import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public class ServerRequestThread implements Runnable {
	private Server server = null;
	
	public boolean loggedIn = false;
	
	public ServerRequestThread(Server server){
		this.server = server;
	}

	@Override
	public void run() {

		String inputLine, outputLine;
		
		outputLine = "Connection recieved!";
		try {
			server.output.writeObject(outputLine);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Weather of not the user client has logged in
		boolean loggedIn = false;
		
		try {
			while(null != (inputLine = (String) server.input.readObject())){
				
				// Skip empty input
				if(inputLine.equals("")){
					continue;
				}
				
				System.out.println("Message from client: " + inputLine);
				
				InetAddress url = server.clientSocket.getInetAddress(); // Get the URL from the client
				String requestCode = inputLine.substring(0, 1);  // Request code is the first character in the input			
				String data = inputLine.substring(1);            // Data is everything after the request code

				// Pass the onlineList to the request handler
				server.requestHandler.setOnlineList(server.onlineHandler.onlineList);

				// Add the request to the request handler
				System.out.println("Creating request...");
				server.requestHandler.createNewRequest(url, requestCode, data);

				System.out.println("Processing request...");
				String result = server.requestHandler.processNextRequest();
				
				System.out.println("result: " + result);
				
				// If we are returning a log file, we have to read the data of the file
				if(result.contains("LOG FILE:")){
					if(result.substring(0, result.indexOf(":")).contains("LOG FILE")){
						
						List<String> logData;
	
						// Let the server know that a log file is incoming
						server.output.writeObject("LOG FILE:");
						
						// Send the data for the log file to the server
						logData = readFile(result.substring(result.indexOf(':') + 1, result.length()));
						
						Iterator<String> iterator = logData.iterator();
						
						while(iterator.hasNext()){
							server.output.writeObject(iterator.next());
						}
						
						server.output.writeObject("[END OF ITERATOR]");
					}else{						
						// Otherwise just print out the result to the output stream
						server.output.writeObject(result);
					}
				}else{
					
					// Otherwise just print out the result to the output stream
					server.output.writeObject(result);
				}
				
				if(!loggedIn){
					loggedIn = server.requestHandler.checkLoggedIn();
					
					if(loggedIn){
						server.username = server.requestHandler.getClientUsername();
						server.onlineNode = new OnlineNode(url, server.username, server.output);
						server.onlineHandler.onlineList.add(server.onlineNode);
					}
				}
				
				System.out.println();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("User " + server.username + " at " + server.clientSocket.getInetAddress().toString() + " has disconnected.");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Done reading input from client " + server.username);
		
		return;
	}
	
	private List<String> readFile(String filename){
	    Path path = FileSystems.getDefault().getPath(filename);
		try {
			return Files.readAllLines(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
