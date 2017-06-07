import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{
	
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	
	MainClient mainClient;
	
	public boolean loggedIn = false;
	
	public Client(MainClient mainClient) throws UnknownHostException, IOException{
		socket = new Socket(mainClient.hostName, mainClient.port);
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		this.mainClient = mainClient;
	}
	
	
	@Override
	public void run() {
		String fromServer;
		
		while(true){
			try {
				fromServer = in.readLine();
				
				if(fromServer != null){
					if(!fromServer.equals("null"))
						System.out.println("Server: " + fromServer);
					
					if(loggedIn){
						
					}else{
						if(fromServer.equals("CORRECT PASSWORD") || fromServer.equals("CREATED NEW ACCOUNT")){
							loggedIn = true;

							mainClient.username = mainClient.loginPanel.usernameField.getText();
							
							mainClient.loginPanel.setVisible(false);
							mainClient.signupPanel.setVisible(false);
							mainClient.mainPanel.setVisible(true);
							mainClient.frmJavaChatApp.remove(mainClient.loginPanel);
							mainClient.frmJavaChatApp.remove(mainClient.signupPanel);
							mainClient.frmJavaChatApp.add(mainClient.mainPanel);
							mainClient.frmJavaChatApp.pack();
						}else{
							mainClient.loginPanel.errorLabel.setText(fromServer);
							mainClient.signupPanel.errorLabel.setText(fromServer);
						}
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void sendRequest(String request){
		if(request != null && !request.equals("")){
			System.out.println("Sending request: " + request);
			out.println(request);
		}
	}
	
}
