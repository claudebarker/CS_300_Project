import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class RequestHandler {

	private Queue<RequestNode> requestQueue = new LinkedList<RequestNode>();
	private ArrayList<OnlineNode> onlineList = new ArrayList<OnlineNode>();
	private boolean loggedIn = false;
	private String clientUsername = "";
	
	public RequestHandler(){
		
	}
	
	public String processNextRequest(){
		RequestNode currentRequest = getNext();
		String requestResult = "";
		if(currentRequest == null)
			return "REQUEST WAS NULL";

		String username = "";
		String target = "";
		String password = "";  
		String timestamp = "";
		String message = "";
		
		boolean validInput = false;
		
		switch(currentRequest.getRequestCode()){
			case 1:
				// New account

				username = (String) currentRequest.getRequestData().get(0);
				password = (String) currentRequest.getRequestData().get(1);
				
				// Check that input is valid
				// TODO
				validInput = true;
				
				if(validInput){
					loggedIn = true;
					clientUsername = username;
				}else{
					loggedIn = false;
				}
				
				requestResult = "CREATED NEW ACCOUNT";
				
				// Write to accounts file
				writeToFile(username + ";" + password, "accounts.txt");
				break;
			case 2:
				// Login

				username = (String) currentRequest.getRequestData().get(0);
				password = (String) currentRequest.getRequestData().get(1);
				
				// Check that input is valid
				// TODO
				validInput = true;
				
				if(validInput){
					clientUsername = username;
				}
				
				// Check that the password matches the username
				requestResult = verifyPassword(username, password);
				
				if(requestResult.equals("CORRECT PASSWORD")){
					loggedIn = true;
				}else{
					loggedIn = false;
				}
				
				break;
			case 3:
				// Message
				
				username = (String) currentRequest.getRequestData().get(0);
				target = (String) currentRequest.getRequestData().get(1);
				timestamp = (String) currentRequest.getRequestData().get(2);
				message = (String) currentRequest.getRequestData().get(3);
				
				requestResult = sendMessage(username, target, timestamp, message);
				
				// Store the message to a file
				if(target.equals("ALL USERS")){
					writeToFile(timestamp + message, "logs/" + "_ALL_USERS.txt");
				}else{
					writeToFile(timestamp + message, "logs/" + username + "-" + target + ".txt");
				}
				break;
			case 4:
				// Retrieve Logs
				username = (String) currentRequest.getRequestData().get(0);
				target = (String) currentRequest.getRequestData().get(1);
				
				// Return a link to the log file to the client program
				// TODO
				
				requestResult = "LOG FILE LINK:";
				
				break;
			case 5:
				// Ping to confirm online
				
				requestResult = "PING SUCCESSFUL";
				
				break;
		}
		
		return requestResult;
	}
	
	public void createNewRequest(InetAddress url, String requestCode, String data){
		System.out.println("URL:" + url.toString());
		System.out.println("Request code:" + requestCode);
		System.out.println("Data:" + data);
		
		int code = Integer.parseInt(requestCode);
		ArrayList<Object> dataArray = new ArrayList<Object>();
		
		// Use ; for the delimiter. Messages may contain simicolons though
		StringTokenizer tokenizer = new StringTokenizer(data, ";");
		
		switch(code){
			case 1:
				// New account
				dataArray.add(new String(tokenizer.nextToken())); // Username
				dataArray.add(new String(tokenizer.nextToken())); // Password
				
				break;
			case 2:
				// Login
				dataArray.add(new String(tokenizer.nextToken())); // Username
				dataArray.add(new String(tokenizer.nextToken())); // Password
				break;
			case 3:
				// Message
				dataArray.add(new String(tokenizer.nextToken())); // Sender Username
				dataArray.add(new String(tokenizer.nextToken())); // Target Username
				dataArray.add(new String(tokenizer.nextToken())); // Timestamp

				// Since messages may contain simi-colons, I have to add in the ; when one is encountered
				String message = "";
				message = message + tokenizer.nextToken();
				while(tokenizer.hasMoreTokens()){
					message = message + ";" + tokenizer.nextToken();
				}
				dataArray.add(message); // Message Text
				
				break;
			case 4:
				// Retrieve Logs
				dataArray.add(new String(tokenizer.nextToken())); // Sender Username
				dataArray.add(new String(tokenizer.nextToken())); // Target Username
				break;
			case 5:
				// Ping to confirm online
				// Data is ignored for ping
				break;
		}
		
		RequestNode node = new RequestNode(url, code, dataArray);
		
		requestQueue.add(node);
	}

	// Write data to a file
	private static void writeToFile(String data, String filename){
		try(
				FileWriter fileWriter = new FileWriter(filename, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				PrintWriter out = new PrintWriter(bufferedWriter);
				){
			
			System.out.println("Writing " + data + " to file " + filename);
			
			// Write to file here.
			out.println(data);
			
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
	
	// Check if the password matches the username
	private String verifyPassword(String username, String password){
		BufferedReader bufferedReader = null;
		boolean usernameFound = false;
		boolean validPassword = false;
			
		try {
			FileReader fileReader = new FileReader("accounts.txt");
			bufferedReader = new BufferedReader(fileReader);
			
			String line = "";
			while((line = bufferedReader.readLine()) != null){
				
				// If the username exists, return true
				
				String name = line.substring(0, line.indexOf(';'));
				String pass = line.substring(line.indexOf(';') + 1, line.length());
				
				//System.out.println("name=" + name + " pass=" + pass);
				
				if(name.equals(username)){
					usernameFound = true;
					if(pass.equals(password)){
						validPassword = true;
					}	
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		    try {
		    	bufferedReader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		
		// Process return
		if(usernameFound){
			if(validPassword){
				return "CORRECT PASSWORD";
			}else{
				return "WRONG PASSWORD!";
			}
		}else{
			return "USERNAME NOT FOUND!";
		}
	}
	
	public void setOnlineList(ArrayList<OnlineNode> onlineList){
		this.onlineList = onlineList;
	}
	
	private String sendMessage(String username, String target, String timestamp, String message){		
		String result = "";
		PrintWriter output = null;
		
		System.out.println(onlineList.toString());
		
		if(target.equals("ALL USERS")){
			
			// Send the message to all online users
			for(int i = 0; i < onlineList.size(); ++i){
				output = onlineList.get(i).getOutput();
				
				// Output is null for some reason...
				
				if(output != null){
					output.println("INCOMING MESSAGE:" + username + ";" + timestamp + ";" + message);
				}
			}
			
			result = "MESSAGE SENT";
		}else{
			// Send the message to a specific online user
			for(int i = 0; i < onlineList.size(); ++i){
				if(onlineList.get(i).getUsername().equals(target)){
					output = onlineList.get(i).getOutput();
					break;
				}
			}
			
			if(output != null){
				output.println("INCOMING MESSAGE:" + username + ";" + timestamp + ";" + message);
				result = "MESSAGE SENT";
			}else{
				result = "MESSAGE FAILED TO SEND";
			}
		}
		
		return result;
	}
	
	// Check if the username is within the accounts.txt file
	private boolean checkIfUsernameExists(String username){
		BufferedReader bufferedReader = null;
		
		try {
			FileReader fileReader = new FileReader("accounts.txt");
			bufferedReader = new BufferedReader(fileReader);
			
			String line = "";
			while((line = bufferedReader.readLine()) != null){
				
				// If the username exists, return true
				
				String name = line.substring(0, line.indexOf(';'));
				
				if(name.equals(username))
					return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		    try {
		    	bufferedReader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		
		return false;
	}
	
	public boolean checkLoggedIn(){
		return loggedIn;
	}
	
	public String getClientUsername(){
		return clientUsername;
	}
	
	// Get the next node from the queue
	public RequestNode getNext(){
		return requestQueue.poll();
	}
	
	public void printQueue(){
		Object array[] = requestQueue.toArray();
		
		for(Object item : array){
			System.out.println(item.toString());
		}
	}
	
	// Test functions
	public void testQueue(){
		fillQueueWithTestNodes();
		
		printQueue();
		
		System.out.println("Dequeueing nodes: ");
		System.out.println(getNext());
		System.out.println(getNext());
		System.out.println(getNext());
		System.out.println(getNext());
	}
	
	private void fillQueueWithTestNodes(){
		for(int i=0; i < 3; ++i){
			requestQueue.add(new RequestNode(true));
		}
	}
}
