package homeSecurityFunctions;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import database.DbConnector;
import homeSecurity.User;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;

public class LoginPage extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	JButton btnLogin;
	Color c = new Color(255, 255, 105);
	Font f = new Font("Lucida Grande", Font.BOLD, 32);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
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
	public LoginPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. Contents are Panel, UserName,
	 * Password, login button and Register Button
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 663, 473);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("SoSecurity Systems");

		JPanel panelLogin = new JPanel();
		panelLogin.setBounds(184, 72, 392, 314);
		panelLogin.setLayout(null);

		JLabel lblUsername = new JLabel("USER NAME");
		lblUsername.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(161, 134, 117, 38);
		panelLogin.add(lblUsername);

		textField = new JTextField();
		textField.setBounds(337, 134, 184, 38);
		panelLogin.add(textField);
		textField.setColumns(15);

		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblPassword.setBounds(161, 202, 117, 29);
		panelLogin.add(lblPassword);

		btnLogin = new JButton("LOGIN");
		btnLogin.setBounds(175, 298, 117, 29);
		btnLogin.addActionListener(this);
		panelLogin.add(btnLogin);

		JButton btnRegister = new JButton("REGISTER");
		btnRegister.setBounds(381, 298, 117, 29);
		panelLogin.add(btnRegister);

		frame.getContentPane().add(panelLogin);

		passwordField = new JPasswordField();
		passwordField.setBounds(337, 198, 184, 38);
		panelLogin.add(passwordField);

		final ImageIcon icon = new ImageIcon("security.png");
		JLabel lblWelcome = new JLabel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};
		lblWelcome.setText("WELCOME TO SoSafe SECURITY SYSTEM");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setForeground(Color.YELLOW);
		lblWelcome.setFont(f);
		lblWelcome.setBounds(6, 6, 655, 50);
		panelLogin.setBackground(c);
		panelLogin.add(lblWelcome);

	}

	/**
	 * When the userName and password is entered, it will validates in Database.
	 * With the given values user can login to the system. If the entered values
	 * are wrong then it will throw the error message.
	 */
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnLogin)) {
			String userName = textField.getText();
			char[] password = passwordField.getPassword();
			System.out.println(password);
			String pwd = String.valueOf(password);
			User u = new User();
			u.setUserName(userName);
			u.setPassword(pwd);
			DbConnector.getUser(u);
			if (u.getUserID() == 0) {
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "Username or Password Entered does not match");
			} else {
				AddingSensor window = new AddingSensor(u);
				window.setVisible(true);
				frame.dispose();
			}
		}
	}

}
