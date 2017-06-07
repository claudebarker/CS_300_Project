
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MainPanel extends JPanel {

	public JTextArea messageBox = new JTextArea();
	public JList usersList = new JList();
	public JButton sendButton = new JButton("Send");
	public JTextArea chatBox = new JTextArea();
	
	/**
	 * Create the panel.
	 */
	public MainPanel() {
		GroupLayout gl_mainViewPanel = new GroupLayout(this);
		gl_mainViewPanel.setHorizontalGroup(
			gl_mainViewPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainViewPanel.createSequentialGroup()
					.addGap(7)
					.addComponent(usersList, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_mainViewPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_mainViewPanel.createSequentialGroup()
							.addComponent(messageBox, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(sendButton, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
						.addComponent(chatBox, GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_mainViewPanel.setVerticalGroup(
			gl_mainViewPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_mainViewPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_mainViewPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(usersList, GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
						.addGroup(gl_mainViewPanel.createSequentialGroup()
							.addComponent(chatBox, GroupLayout.PREFERRED_SIZE, 412, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_mainViewPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(sendButton, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
								.addComponent(messageBox, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))))
					.addContainerGap())
		);
		
		setLayout(gl_mainViewPanel);
	}

}
