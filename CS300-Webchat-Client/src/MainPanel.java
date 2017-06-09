
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JScrollPane;

public class MainPanel extends JPanel {

	public JTextArea messageBox = new JTextArea();
	public JList usersList = new JList();
	public JButton sendButton = new JButton("Send");
	public JTextArea chatBox = new JTextArea();
	public JButton getLogFilebtn = new JButton("Get log file");
	public final JScrollPane chatScrollPane = new JScrollPane();
	private final JScrollPane scrollPane_1 = new JScrollPane();
	private final JScrollPane scrollPane_2 = new JScrollPane();
	
	/**
	 * Create the panel.
	 */
	public MainPanel() {
		GroupLayout gl_mainViewPanel = new GroupLayout(this);
		gl_mainViewPanel.setHorizontalGroup(
			gl_mainViewPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainViewPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_mainViewPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_mainViewPanel.createSequentialGroup()
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 334, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_mainViewPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(sendButton, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
								.addComponent(getLogFilebtn, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)))
						.addComponent(chatScrollPane, GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_mainViewPanel.setVerticalGroup(
			gl_mainViewPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_mainViewPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_mainViewPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
						.addGroup(gl_mainViewPanel.createSequentialGroup()
							.addComponent(chatScrollPane, GroupLayout.PREFERRED_SIZE, 417, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_mainViewPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_mainViewPanel.createSequentialGroup()
									.addComponent(sendButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(getLogFilebtn))
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		scrollPane_2.setViewportView(usersList);
		scrollPane_1.setViewportView(messageBox);
		messageBox.setLineWrap(true);
		chatScrollPane.setViewportView(chatBox);
		chatBox.setLineWrap(true);
		
		setLayout(gl_mainViewPanel);
	}
}
