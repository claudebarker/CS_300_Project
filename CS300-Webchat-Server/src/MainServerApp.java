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

public class MainServerApp {
	private JFrame frame;

    public JTextArea textArea;
    private JScrollPane scrollPane;
    private JSplitPane splitPane;
    private JTextField txtPort;
    private JButton btnStart;
    
    private boolean isRunning = false;
    
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainServerApp window = new MainServerApp();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 602, 373);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		splitPane = new JSplitPane();
		scrollPane.setColumnHeaderView(splitPane);
		
		txtPort = new JTextField();
		txtPort.setText("60010");
		splitPane.setLeftComponent(txtPort);
		txtPort.setColumns(10);
		
		btnStart = new JButton("START");
		
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
		
		splitPane.setRightComponent(btnStart);
		
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
