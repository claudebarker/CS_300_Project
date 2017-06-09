import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JScrollBar;

public class Client implements Runnable{
	
	Socket socket;
	ObjectOutputStream out;
	ObjectInputStream in;
	
	MainClient mainClient;
	
	public boolean loggedIn = false;
	
	public Client(MainClient mainClient) throws UnknownHostException, IOException{
		socket = new Socket(mainClient.hostName, mainClient.port);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		
		this.mainClient = mainClient;
	}
	
	
	@Override
	public void run() {
		String fromServer;
		
		while(true){
			try {
				fromServer = (String) in.readObject();
				
				if(fromServer != null){
					if(!fromServer.equals("null"))
						System.out.println("Server: " + fromServer);
					
					if(loggedIn){
						
						// Check if we are dealing with an incoming message
						if(fromServer.contains(":")){
							String code = fromServer.substring(0, fromServer.indexOf(':'));
							if(code.equals("INCOMING MESSAGE")){
								String message = fromServer.substring(fromServer.indexOf(':') + 1, fromServer.length());
								appendMessageToChatWindow(message);
								
							}else if(code.equals("USERS LIST")){
								String message = fromServer.substring(fromServer.indexOf(':') + 1, fromServer.length());
								updateUsersListGUI(message);
								
							}else if(code.equals("LOG FILE")){
								System.out.println("Beginning log file transfer...");
								
								LogDialog logDialog = new LogDialog();
								logDialog.setVisible(true);
								
								String logData = (String) in.readObject();
								
								while(!logData.contains("[END OF ITERATOR]")){
									logDialog.textArea.append(logData + System.lineSeparator());
									
									logData = (String) in.readObject();
								}
							}
						}
						
					}else{
						if(fromServer.equals("CORRECT PASSWORD") || fromServer.equals("CREATED NEW ACCOUNT")){
							loggedIn = true;
							mainClient.panelHandler.changeToMainPanel();
						}else if(fromServer.contains("ERROR")){
							mainClient.loginPanel.errorLabel.setText(fromServer);
							mainClient.signupPanel.errorLabel.setText(fromServer);
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void updateUsersListGUI(String message){
		System.out.println("User's list recieved: " + message);

		int tempIndex = mainClient.mainPanel.usersList.getSelectedIndex();
		mainClient.mainPanel.usersList.setListData(message.split(";"));
		mainClient.mainPanel.usersList.setSelectedIndex(tempIndex);
		
	}
	
	public void appendMessageToChatWindow(String message){;
		mainClient.mainPanel.chatBox.append(message + '\n');
		
		// Scroll to bottom of chat window
		JScrollBar verticalScrollbar = mainClient.mainPanel.chatScrollPane.getVerticalScrollBar();
		verticalScrollbar.setValue(verticalScrollbar.getMaximum());
	}

	public void sendRequest(String request){
		if(request != null && !request.equals("")){
			System.out.println("Sending request: " + request);
			try {
				out.writeObject(request);
			} catch (IOException e) {
				System.err.println("Error sending request!");
			}
		}
	}
	
}
