import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;

public class ConnectToServerPanel extends JPanel {
	public JTextField serverIPField;
	public JTextField portField;
	public JButton connectButton;
	public JLabel errorLabel;

	/**
	 * Create the panel.
	 */
	public ConnectToServerPanel() {
		
		JLabel lblConnectToServer = new JLabel("CONNECT TO SERVER");
		
		serverIPField = new JTextField();
		serverIPField.setText("127.0.0.1");
		serverIPField.setColumns(25);
		
		portField = new JTextField();
		portField.setText("60010");
		portField.setColumns(10);
		
		connectButton = new JButton("CONNECT");
		
		JLabel lblServerIp = new JLabel("Server IP");
		
		JLabel lblPort = new JLabel("Port");
		
		errorLabel = new JLabel("");
		errorLabel.setForeground(Color.RED);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblConnectToServer)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(serverIPField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblServerIp))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPort)
								.addComponent(portField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(errorLabel)
						.addComponent(connectButton))
					.addContainerGap(315, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(lblConnectToServer)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPort)
						.addComponent(lblServerIp))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(serverIPField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(portField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(connectButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(errorLabel)
					.addContainerGap(412, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}
