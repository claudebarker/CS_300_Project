import java.awt.EventQueue;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;

public class MainServerApp {
	private JFrame frmJavaChatServer;

    public JTextArea textArea;
    private JScrollPane scrollPane;
    private JTextField txtPort;
    private JButton btnStart;
    
    private boolean isRunning = false;
    private JPanel panel;
    private JLabel lblPort;
    
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainServerApp window = new MainServerApp();
					window.frmJavaChatServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the application.
	 */
	public MainServerApp() {
		initialize();
	    
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmJavaChatServer = new JFrame();
		frmJavaChatServer.setTitle("Java Chat Server");
		frmJavaChatServer.setBounds(100, 100, 602, 373);
		frmJavaChatServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJavaChatServer.getContentPane().setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		frmJavaChatServer.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		panel = new JPanel();
		frmJavaChatServer.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblPort = new JLabel("Port:");
		panel.add(lblPort);
		
		txtPort = new JTextField();
		panel.add(txtPort);
		txtPort.setText("60010");
		txtPort.setColumns(10);
		
		btnStart = new JButton("Start server");
		panel.add(btnStart);
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!isRunning){
					String port = txtPort.getText();
					int portInt = Integer.parseInt(port);
				
					createNewThread(portInt);
					isRunning = true;
				}
			}
		});
		
	}
	
	private void createNewThread(int portInt){
		System.out.println("Creating thread on port " + portInt);
		new Thread(new ServerConnectionHandler(this, portInt)).start();
	}
	
	public void updateConsoleLog(String consoleLogText){
		textArea.setText(consoleLogText);
		
		// Scroll to bottom of chat window
		JScrollBar verticalScrollbar = scrollPane.getVerticalScrollBar();
		verticalScrollbar.setValue(verticalScrollbar.getMaximum());
	}
}
