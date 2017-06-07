import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.Color;

public class SignupPanel extends JPanel {
	public JTextField usernameField;
	public JTextField passwordField;
	public JTextField passwordFieldAgain;
	public JButton returnButton;
	public JButton signupButton;
	public JLabel errorLabel;

	/**
	 * Create the panel.
	 */
	public SignupPanel() {
		
		usernameField = new JTextField();
		usernameField.setText("USERNAME");
		usernameField.setColumns(20);
		
		passwordField = new JTextField();
		passwordField.setText("PASSWORD");
		passwordField.setColumns(20);
		
		passwordFieldAgain = new JTextField();
		passwordFieldAgain.setText("PASSWORD AGAIN");
		passwordFieldAgain.setColumns(20);
		
		signupButton = new JButton("SIGN UP");
		
		JLabel lblNewLabel = new JLabel("Create Account");
		
		returnButton = new JButton("RETURN");
		
		errorLabel = new JLabel("");
		errorLabel.setForeground(Color.RED);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(lblNewLabel)
							.addComponent(passwordField)
							.addComponent(usernameField)
							.addComponent(signupButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(passwordFieldAgain)
							.addComponent(returnButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(errorLabel))
					.addContainerGap(304, Short.MAX_VALUE))
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
					.addComponent(passwordFieldAgain, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(signupButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(returnButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(errorLabel)
					.addContainerGap(243, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}
