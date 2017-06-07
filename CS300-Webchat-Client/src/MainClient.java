import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class MainClient {
	
	public JFrame frmJavaChatApp;
	
	Client client;
	Thread clientThread;
	
	public String hostName = "localhost";
	public int port = 60010;
	
	public String username = null;
	public String currentChat = "ALL USERS";
	public String requestToSend = null;
	
	MainPanel mainPanel;
	LoginPanel loginPanel;
	SignupPanel signupPanel;
	

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainClient window = new MainClient();
					window.frmJavaChatApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}


	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public MainClient() throws UnknownHostException, IOException {
		initialize();
		
		client = new Client(this);
		clientThread = new Thread(client);
		clientThread.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmJavaChatApp = new JFrame();
		frmJavaChatApp.setTitle("Java Chat App");
		frmJavaChatApp.setBounds(100, 100, 670, 543);
		frmJavaChatApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new MainPanel();
		loginPanel = new LoginPanel();
		signupPanel = new SignupPanel();
		
		frmJavaChatApp.add(loginPanel);

		// Login panel
		loginPanel.loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendLoginRequest(loginPanel.usernameField.getText(), loginPanel.passwordField.getText());
			}
		});
		
		// New user button
		loginPanel.newUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loginPanel.setVisible(false);
				signupPanel.setVisible(true);
				mainPanel.setVisible(false);
				frmJavaChatApp.remove(loginPanel);
				frmJavaChatApp.add(signupPanel);
				frmJavaChatApp.remove(mainPanel);
				frmJavaChatApp.pack();
			}
		});
		
		
		// Singup panel		
		// Sign Up button
		signupPanel.signupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Check that the two password fields are equal
				if(signupPanel.passwordField.getText().equals(signupPanel.passwordFieldAgain.getText())){
					signupPanel.errorLabel.setText("");
					sendSignupRequest(signupPanel.usernameField.getText(), signupPanel.passwordField.getText());
				}else{
					signupPanel.errorLabel.setText("ERROR: Password fields do not match!");
				}
			}
		});
		
		// Return button
		signupPanel.returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loginPanel.setVisible(true);
				signupPanel.setVisible(false);
				mainPanel.setVisible(false);
				frmJavaChatApp.add(loginPanel);
				frmJavaChatApp.remove(signupPanel);
				frmJavaChatApp.remove(mainPanel);
				frmJavaChatApp.pack();
			}
		});
		
		
		// Main panel
		mainPanel.sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendCurrentMessage(mainPanel.messageBox.getText());
			}
		});
		
		// Send the message if the user presses "Enter"
		mainPanel.messageBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				// Check if the user pressed "Enter"
				if(arg0.getKeyCode() == 10){
					mainPanel.sendButton.doClick();
				}
			}
		});
		
		
	}
	
	private void sendSignupRequest(String usernameField, String passwordField){
		if(!usernameField.equals("") & !passwordField.equals("")){
			requestToSend = "1" + usernameField + ";" + passwordField;
			client.sendRequest(requestToSend);
		}
	}
	
	private void sendLoginRequest(String usernameField, String passwordField){
		if(!usernameField.equals("") & !passwordField.equals("")){
			requestToSend = "2" + usernameField + ";" + passwordField;
			client.sendRequest(requestToSend);
		}
	}

	private void sendCurrentMessage(String messageText){		
		if(!messageText.equals("")){
			requestToSend = "3" + username + ";" + currentChat + ";" + getTimestamp() + ";" + messageText;
			client.sendRequest(requestToSend);
			
			mainPanel.messageBox.setText("");
		}
	}
	
	private String getTimestamp(){
		return "" + System.currentTimeMillis();
	}
}
