package animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import homeSecurity.User;

public class AnimationMainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AnimationMainPanel mPanel;
	JPanel upperPanel;
	private ImageIcon icon;
	private int width;
	private int height;
	String lblString;

	/**
	 * Set common parameters for a label
	 * 
	 * @param label
	 * @param lblString
	 */
	public void setLabelParams(JLabel label, String lblString) {
		label.setText(lblString);
		label.setForeground(Color.WHITE);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		label.setOpaque(false);
	}

	/**
	 * MainFrame for Animation object...Splits the Main frame into 2 panel, the
	 * upper panel will have the title and bottom panel will have the animation
	 * object
	 * 
	 * @param eventType
	 * @param user
	 */
	public AnimationMainFrame(String eventType, User user) {
		setTitle("SOSECURITY-SYSTEM-TEST");
		this.setLayout(new GridLayout(2, 1));
		if (eventType.equals("FIRE EVENT")) {
			lblString = "<html>" + eventType + " TRIGGERED<br>" + "SENDING MESSAGE TO<br>    " + user.getPhoneNumber();
			icon = new ImageIcon("fire.png");
			width = 300;
			height = 400;
		} else {
			lblString = "<html>" + eventType + " TRIGGERED<br>" + "SENDING EMAIL TO<br>    " + user.getEmail();
			icon = new ImageIcon("burglary.png");
			width = 600;
			height = 400;
		}
		upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout(1, 1));
		upperPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.black));

		JLabel label = new JLabel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				System.out.println("Here!!!");
				g.drawImage(icon.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};
		setLabelParams(label, lblString);
		upperPanel.add(label);

		add(upperPanel);
		mPanel = new AnimationMainPanel(eventType);
		mPanel.setPreferredSize(new Dimension(width, height));
		mPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.black));

		setSize(width, height);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// add AnimationMainPanel JPanel to JFrame
		add(mPanel);
		setVisible(true);
		// Display JFrame in center of screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent evt) {
				System.out.println("Closed the window");
				mPanel.closeAllApps();
			}
		});
	}

	public static void main(String[] args) {
		User u = new User();
		new AnimationMainFrame("BURGLARY EVENT", u);
	}
}
