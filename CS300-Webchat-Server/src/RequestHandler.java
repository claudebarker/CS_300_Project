import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class RequestHandler {

	private Queue<RequestNode> requestQueue = new LinkedList<RequestNode>();
	
	public RequestHandler(){
		
	}
	
	public void processNextRequest(){
		RequestNode currentRequest = getNext();
		if(currentRequest == null)
			return;
		
		switch(currentRequest.getRequestCode()){
			case 1:
				// New account
				break;
			case 2:
				// Login
				break;
			case 3:
				// Message
				break;
			case 4:
				// Retrieve Logs
				break;
			case 5:
				// Ping to confirm online
				break;
		}
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
