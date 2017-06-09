import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class MainClient {
	
	public JFrame frmJavaChatApp;
	
	Client client;
	
	public String hostName = "localhost";
	public int port = 60010;
	
	public String username = null;
	public String currentChat = "ALL USERS";
	public String requestToSend = null;
	
	PanelHandler panelHandler;
	
	MainPanel mainPanel;
	LoginPanel loginPanel;
	SignupPanel signupPanel;
	ConnectToServerPanel connectToServerPanel;

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

	public MainClient() {
		initialize();
		
		panelHandler = new PanelHandler(this);
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
		connectToServerPanel = new ConnectToServerPanel();
		
		frmJavaChatApp.add(connectToServerPanel);

		// *****************
		// ConnectToServerPanel
		// *****************
		
		connectToServerPanel.connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tryConnectingToServer(connectToServerPanel.serverIPField.getText(), connectToServerPanel.portField.getText());
			}
		});

		// *****************
		// Login panel
		// *****************
		
		loginPanel.loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendLoginRequest(loginPanel.usernameField.getText(), loginPanel.passwordField.getText());
			}
		});
		
		// New user button
		loginPanel.newUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelHandler.changeToSignupPanel();
			}
		});
		

		// *****************
		// Singup panel		
		// *****************
		
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
				panelHandler.changeToLoginPanel();
			}
		});
		
		// *****************
		// Main panel
		// *****************
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
		
		// Get log file
		mainPanel.getLogFilebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getLogFile();
			}
		});
		
		// Change chat recipient
		mainPanel.usersList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				currentChat = (String)mainPanel.usersList.getSelectedValue();
				System.out.println("Changing currentChat to " + currentChat);
			}
		});
		
	}
	
	private void tryConnectingToServer(String ipField, String portField){
		hostName = ipField;
		port = Integer.parseInt(portField);
		
		try {
			client = new Client(this);
			new Thread(client).start();
			
			panelHandler.changeToLoginPanel();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			
			// If we are not in the ALL USERS chat, we need to append the message we send to the chat window
			if(!currentChat.equals("ALL USERS")){
				String messageForWindow = username + " -> " + currentChat + " : " + getTimestamp() + ": " + messageText;
				client.appendMessageToChatWindow(messageForWindow);
			}
		}
	}
	
	private void getLogFile(){
		requestToSend = "4" + username + ";" + currentChat;
		client.sendRequest(requestToSend);
	}
	
	private String getTimestamp(){
		return "" + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
	}
}
