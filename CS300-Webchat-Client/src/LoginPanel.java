import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.Color;

public class LoginPanel extends JPanel {
	public JTextField passwordField;
	public JTextField usernameField;
	public JButton loginButton;
	public JButton newUserButton;
	public JLabel errorLabel;

	/**
	 * Create the panel.
	 */
	public LoginPanel() {
		
		JLabel lblNewLabel = new JLabel("Login");
		
		usernameField = new JTextField();
		usernameField.setText("USERNAME");
		usernameField.setColumns(20);
		
		passwordField = new JTextField();
		passwordField.setText("PASSWORD");
		passwordField.setColumns(20);
		
		loginButton = new JButton("LOGIN");
		
		newUserButton = new JButton("NEW USER");
		
		errorLabel = new JLabel("");
		errorLabel.setForeground(Color.RED);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(newUserButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(passwordField, Alignment.LEADING)
							.addComponent(usernameField, Alignment.LEADING)
							.addComponent(lblNewLabel, Alignment.LEADING)
							.addComponent(loginButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(errorLabel))
					.addContainerGap(409, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(loginButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(newUserButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(errorLabel)
					.addContainerGap(325, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}
