
public class PanelHandler {

	private MainClient mainClient;
	
	public PanelHandler(MainClient mainClient){
		this.mainClient = mainClient;
	}
	
	public void changeToMainPanel(){		
		mainClient.loginPanel.setVisible(false);
		mainClient.signupPanel.setVisible(false);
		mainClient.mainPanel.setVisible(true);
		mainClient.frmJavaChatApp.remove(mainClient.loginPanel);
		mainClient.frmJavaChatApp.remove(mainClient.signupPanel);
		mainClient.frmJavaChatApp.add(mainClient.mainPanel);
		mainClient.frmJavaChatApp.pack();
	}
	
	public void changeToSignupPanel(){
		mainClient.loginPanel.setVisible(false);
		mainClient.signupPanel.setVisible(true);
		mainClient.mainPanel.setVisible(false);
		mainClient.frmJavaChatApp.remove(mainClient.loginPanel);
		mainClient.frmJavaChatApp.add(mainClient.signupPanel);
		mainClient.frmJavaChatApp.remove(mainClient.mainPanel);
		mainClient.frmJavaChatApp.pack();
	}

	public void changeToLoginPanel(){
		mainClient.loginPanel.setVisible(true);
		mainClient.signupPanel.setVisible(false);
		mainClient.mainPanel.setVisible(false);
		mainClient.connectToServerPanel.setVisible(false);
		mainClient.frmJavaChatApp.remove(mainClient.connectToServerPanel);
		mainClient.frmJavaChatApp.remove(mainClient.mainPanel);
		mainClient.frmJavaChatApp.add(mainClient.loginPanel);
		mainClient.frmJavaChatApp.pack();
	}
}
