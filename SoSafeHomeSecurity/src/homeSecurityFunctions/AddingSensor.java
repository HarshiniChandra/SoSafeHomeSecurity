package homeSecurityFunctions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import animation.AnimationMainFrame;
import database.DbConnector;
import generateBill.SSBarGraph;
import homeSecurity.FireAlarm;
import homeSecurity.Sensor;
import homeSecurity.User;
import homeSecurity.subArea;

public class AddingSensor extends JFrame implements MouseListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;

	JPanel panelEast;
	JPanel panelNorth;
	drawpoly panelCenter;
	JPanel panelWest;
	JPanel panelSouth;
	JRadioButton rdbtnEnableSensor;
	JRadioButton rdbtnDisableSensor;
	JRadioButton rdbtnEnableFire;
	JRadioButton rdbtnDisableFire;
	JFormattedTextField formattedTextField;
	JFormattedTextField fromformattedTextField;
	JButton btnClearSelection;
	JButton btnSave;
	ButtonGroup sensorButtonGroup;
	ButtonGroup fireButtonGroup;
	ButtonGroup eventButtonGroup;
	JRadioButton rdbtnFire;
	JRadioButton rdbtnBurglary;
	JButton btnTriggerEvent;
	JButton btnGenerateBill;
	JButton btnBack;
	JPanel billPanel;
	Color c = new Color(255, 255, 105);

	/**
	 * Launch the application.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User u = new User();
					u.setUserName("user1");
					u.setPassword("user1pwd");
					DbConnector.getUser(u);
					AddingSensor window = new AddingSensor(u);
					window.setUser(u);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * left side of the Panel gives an option to add the sensor and Fire Alarm,
	 * Enable and Disable option to set the Sensors and Fire Alarms. Buttons to
	 * add the Sensors and Clear Screen.
	 */
	public void AddEastPanel() {
		panelEast.setBackground(Color.RED);
		panelEast.setForeground(Color.BLACK);
		panelEast.setLayout(null);
		panelEast.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		JLabel lblSensor = new JLabel("SENSOR");
		lblSensor.setHorizontalAlignment(SwingConstants.CENTER);
		lblSensor.setBounds(78, 75, 80, 23);
		panelEast.add(lblSensor);

		rdbtnEnableSensor = new JRadioButton("ENABLE");
		rdbtnEnableSensor.setBounds(6, 110, 111, 23);
		panelEast.add(rdbtnEnableSensor);

		rdbtnDisableSensor = new JRadioButton("DISABLE");
		rdbtnDisableSensor.setBounds(133, 110, 111, 23);
		panelEast.add(rdbtnDisableSensor);

		sensorButtonGroup = new ButtonGroup();
		sensorButtonGroup.add(rdbtnEnableSensor);
		sensorButtonGroup.add(rdbtnDisableSensor);

		JLabel lblFirealarm = new JLabel("FIREALARM");
		lblFirealarm.setBounds(78, 179, 92, 16);
		panelEast.add(lblFirealarm);

		rdbtnEnableFire = new JRadioButton("ENABLE");
		rdbtnEnableFire.setBounds(6, 216, 111, 23);
		rdbtnEnableFire.addActionListener(this);
		panelEast.add(rdbtnEnableFire);

		rdbtnDisableFire = new JRadioButton("DISABLE");
		rdbtnDisableFire.setBounds(133, 216, 111, 23);
		rdbtnDisableFire.addActionListener(this);
		panelEast.add(rdbtnDisableFire);

		fireButtonGroup = new ButtonGroup();
		fireButtonGroup.add(rdbtnEnableFire);
		fireButtonGroup.add(rdbtnDisableFire);

		btnSave = new JButton("SAVE");
		btnSave.addActionListener(this);
		btnSave.setBounds(6, 422, 238, 66);
		panelEast.add(btnSave);

		DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

		fromformattedTextField = new JFormattedTextField(format);
		Date date = new Date();
		System.out.println(format.format(date));
		fromformattedTextField.setText(format.format(date));
		fromformattedTextField.setBounds(94, 275, 155, 29);
		panelEast.add(fromformattedTextField);

		JLabel lblFromdate = new JLabel("FROM-DATE");
		lblFromdate.setBounds(6, 278, 92, 23);
		panelEast.add(lblFromdate);
		/**
		 * Date Pattern to enter the Date and Time to Enable the Sensor
		 */
		JLabel lblmmddyyyyHhmm = new JLabel("(MM/dd/yyyy hh:mi:ss)");
		lblmmddyyyyHhmm.setBounds(94, 304, 150, 16);
		panelEast.add(lblmmddyyyyHhmm);

		JSeparator separator = new JSeparator();
		separator.setOpaque(true);
		separator.setBorder(null);
		separator.setBounds(8, 155, 236, 12);
		panelEast.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 251, 238, 12);
		panelEast.add(separator_1);

		JLabel lblTodate = new JLabel("TO-DATE");
		lblTodate.setBounds(6, 348, 61, 16);
		panelEast.add(lblTodate);

		formattedTextField = new JFormattedTextField(format);
		formattedTextField.setBounds(94, 342, 155, 29);
		panelEast.add(formattedTextField);

		JLabel lblNewLabel = new JLabel("(MM/dd/yyyy hh:mi:ss)");
		lblNewLabel.setBounds(94, 371, 150, 16);
		panelEast.add(lblNewLabel);
		panelEast.setVisible(true);

		panelEast.setBackground(c);
		panelEast.setPreferredSize(new Dimension(250, 10));
		JLabel lblAddSensorOr = new JLabel("ADD SENSOR /FIREALARM");
		lblAddSensorOr.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddSensorOr.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblAddSensorOr.setBounds(6, 6, 238, 61);
		panelEast.add(lblAddSensorOr);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(6, 59, 238, 12);
		panelEast.add(separator_2);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(6, 398, 238, 12);
		panelEast.add(separator_3);

		btnClearSelection = new JButton("CLEAR SELECTION");
		btnClearSelection.addActionListener(this);
		btnClearSelection.setBounds(6, 508, 238, 61);
		panelEast.add(btnClearSelection);
		getContentPane().add(panelEast, BorderLayout.WEST);
	}

	/**
	 * Create the application.
	 */
	public AddingSensor(User user) {
		setName("SOSECURITY");
		this.user = user;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 1280, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		panelEast = new JPanel();
		panelNorth = new JPanel();
		panelWest = new JPanel();
		panelSouth = new JPanel();
		AddEastPanel();
		AddPanelNorth();
		AddPanelWest();
		AddPanelSouth();
		AddPanelCenter();

		this.validate();

	}

	private void AddPanelNorth() {

		panelNorth.setPreferredSize(new Dimension(1280, 100));
		panelNorth.setLayout(new GridLayout(1, 1));
		getContentPane().add(panelNorth, BorderLayout.NORTH);
		final ImageIcon icon = new ImageIcon("security.png");
		JLabel lblNewLabel_1 = new JLabel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};
		String lblString = "WELCOME " + user.getUserName().toUpperCase() + " TO SOSECURITY CONFIGURATION MENU";
		lblNewLabel_1.setText(lblString);
		lblNewLabel_1.setOpaque(false);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setVerticalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.YELLOW);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 32));
		panelNorth.add(lblNewLabel_1);
	}

	/**
	 * This Method is for the Right side of the Frame,to trigger the events.
	 * Events are Burglary and Fire Hazard.
	 */
	private void AddPanelWest() {

		panelWest.setPreferredSize(new Dimension(250, 10));
		panelWest.setBackground(c);
		getContentPane().add(panelWest, BorderLayout.EAST);
		panelWest.setLayout(new GridLayout(5, 1));
		panelWest.setBorder(new MatteBorder(2, 2, 2, 2, Color.black));
		JPanel panel1 = new JPanel();
		panel1.setOpaque(false);
		panel1.setLayout(null);
		panel1.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));

		JLabel lbltriggerEvent = new JLabel("TRIGGER EVENT");
		lbltriggerEvent.setBounds(6, 6, 236, 121);
		lbltriggerEvent.setHorizontalAlignment(SwingConstants.CENTER);
		lbltriggerEvent.setVerticalAlignment(SwingConstants.CENTER);
		lbltriggerEvent.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		panel1.add(lbltriggerEvent);
		panelWest.add(panel1);
		JPanel panel2 = new JPanel();
		panel2.setOpaque(false);
		panel2.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel2.setLayout(new GridLayout(1, 2));
		rdbtnBurglary = new JRadioButton("BURGLARY");
		panel2.add(rdbtnBurglary);
		JLabel lblburglary = new JLabel(new ImageIcon("burglary-clip.png"));
		panel2.add(lblburglary);
		panelWest.add(panel2);

		JPanel panel3 = new JPanel();
		panel3.setOpaque(false);
		panel3.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel2.setLayout(new GridLayout(1, 2));
		rdbtnFire = new JRadioButton("FIRE HAZARD");
		rdbtnFire.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel firelbl = new JLabel(new ImageIcon("house-fire.png"));
		panel3.setLayout(new GridLayout(1, 2));
		panel3.add(rdbtnFire);
		panel3.add(firelbl);
		panelWest.add(panel3);

		eventButtonGroup = new ButtonGroup();
		eventButtonGroup.add(rdbtnBurglary);
		eventButtonGroup.add(rdbtnFire);
		btnTriggerEvent = new JButton("TRIGGER EVENT");
		btnTriggerEvent.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnTriggerEvent.addActionListener(this);
		panelWest.add(btnTriggerEvent);

		/**
		 * Generate bill option to view the bill of the Sensor and fire Alarms
		 * Used in the particular month.
		 */
		btnGenerateBill = new JButton("GENERATE BILL");
		btnGenerateBill.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnGenerateBill.addActionListener(this);
		panelWest.add(btnGenerateBill);
	}

	private void AddPanelSouth() {
		panelSouth.setPreferredSize(new Dimension(1280, 10));
		panelSouth.setBackground(Color.BLUE);
		getContentPane().add(panelSouth, BorderLayout.SOUTH);
	}

	/**
	 * This method is for the Center of the frame which shows the Layout of the
	 * house.
	 */

	private void AddPanelCenter() {
		panelCenter = new drawpoly(user.getBld());
		panelCenter.setBackground(Color.WHITE);
		panelCenter.setAlignmentY(CENTER_ALIGNMENT);
		panelCenter.setAlignmentX(CENTER_ALIGNMENT);
		panelCenter.setMaximumSize(new Dimension(600, 600));
		getContentPane().add(panelCenter, BorderLayout.CENTER);
	}

	/**
	 * Button to enable the Sensor
	 */
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(rdbtnEnableSensor)) {
			System.out.println("Radio button :" + e.getSource().equals(rdbtnEnableSensor));
		}
	}

	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Clear Selection button to clear the selected areas on the building
	 * layout.
	 */
	public void clearAllSelection() {
		System.out.println("Clearing all selected");
		sensorButtonGroup.clearSelection();
		fireButtonGroup.clearSelection();
		eventButtonGroup.clearSelection();
		panelCenter.setPaintRect(true);
		panelCenter.repaint();
		panelCenter.validate();
		panelCenter.clearSelected();
	}

	/**
	 * After selecting the subArea in the building, you have to save it.
	 * 
	 * @param e
	 */
	public void handleSaveButtonAction(ActionEvent e) {
		System.out.println("Save button clicked");
		// First check if radio button is selected
		boolean isSensorChangeSelected = false;
		boolean isSensorArmed = false;
		if (rdbtnEnableSensor.isSelected() || rdbtnDisableSensor.isSelected()) {
			isSensorChangeSelected = true;
			if (rdbtnEnableSensor.isSelected()) {
				isSensorArmed = true;
			}
		}
		boolean isFireChangeSelected = false;
		boolean isFireArmed = false;
		if (rdbtnEnableFire.isSelected() || rdbtnDisableFire.isSelected()) {
			isFireChangeSelected = true;
			if (rdbtnEnableFire.isSelected()) {
				isFireArmed = true;
			}
		}
		// Get Selected subArea from the center
		ArrayList<subArea> saList = panelCenter.getSelectedSubArea();
		if ((saList == null || saList.isEmpty()) && (!isFireChangeSelected || !isSensorChangeSelected)) {
			Component frame = null;
			JOptionPane.showMessageDialog(frame,
					"Please select an area first in the image, and Select sensor or FireAlarm to be armed",
					getWarningString(), JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// Save all configuration for user
		// Check if the sensor is already configured
		Iterator<subArea> it = saList.iterator();
		while (it.hasNext()) {
			// Check if there is a sensor configured for this subArea
			subArea s = it.next();
			System.out.println("Get sensors for " + s.toString());
			Sensor sensor = new Sensor();
			FireAlarm fire = new FireAlarm();
			DbConnector.getSensorForSubArea(s.getSubAreaID(), sensor);
			DbConnector.getFireAlarmForSubArea(s.getSubAreaID(), fire);
			System.out.println(sensor.toString());
			boolean insertSensor = false;
			boolean insertFire = false;
			if ((sensor.getSensorID() == 0) && (isSensorChangeSelected)) {
				insertSensor = true;
			}
			if ((fire.getFireAlarmID() == 0) && (isFireChangeSelected)) {
				insertFire = true;
			}
			sensor.setIsArmed(isSensorArmed);
			fire.setIsArmed(isFireArmed);
			String fromDate = fromformattedTextField.getText();
			String toDate = formattedTextField.getText();
			Date fDate = null;
			Date tDate = null;

			try {
				fDate = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.ENGLISH).parse(fromDate);
				if (!toDate.isEmpty()) {
					tDate = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss", Locale.ENGLISH).parse(toDate);
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			sensor.setSubAreaID(s.getSubAreaID());
			fire.setSubAreaID(s.getSubAreaID());
			sensor.setUserID(user.getUserID());
			fire.setUserID(user.getUserID());
			sensor.setFromTime(fDate);
			fire.setFromTime(fDate);
			if (isSensorChangeSelected)
				s.setHasSensor(isSensorArmed);
			if (isFireChangeSelected)
				s.setHasFire(isFireArmed);
			DbConnector.updateSubArea(s);

			sensor.setToDate(tDate);
			try {
				sensor.setActive_date(new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(fromDate));
				fire.setActive_date(new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(fromDate));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			fire.setToDate(tDate);
			System.out.println("Updated sensor is :" + sensor.toString());
			// Insert to Database
			if (insertSensor) {
				DbConnector.insertSensor(sensor);
			} else if (isFireChangeSelected) {
				// Do not update if the not selected... It will flip it other
				// wise
				DbConnector.updateSensor(sensor);
			}
			if (insertFire) {
				DbConnector.insertFireAlarm(fire);
			} else if (isFireChangeSelected) {
				// Do not update if the not selected... It will flip it other
				// wise
				DbConnector.updateFire(fire);
			}
		}
		clearAllSelection();
	}

	/**
	 * For the logged in user, generate the bill of the Sensors and Fire Alarms
	 * used in that month.
	 */
	public void generateBill() {
		System.out.println(getContentPane().toString());
		new SSBarGraph(user);
	}

	public void handleTriggerEvent(ActionEvent e) {
		System.out.println("Triggered Event");
		boolean isBurglary = false;
		if (rdbtnBurglary.isSelected()) {
			isBurglary = true;
		}
		boolean isFire = false;
		if (rdbtnFire.isSelected()) {
			isFire = true;
		}
		// Get Selected subArea from the center
		ArrayList<subArea> saList = panelCenter.getSelectedSubArea();
		if (saList == null || saList.isEmpty() || (!isBurglary && !isFire)) {
			Component frame = null;
			JOptionPane.showMessageDialog(frame,
					"Please select an area first in the image and Select one of fire or Burglary event",
					getWarningString(), JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// Save all configuration for user
		// Check if the sensor is already configured
		Iterator<subArea> it = saList.iterator();
		while (it.hasNext()) {
			System.out.println("Triggered Event Fire");
			subArea sa = it.next();
			if (sa.getHasFire() && isFire) {
				// Check if the date is good.
				FireAlarm fire = new FireAlarm();
				DbConnector.getFireAlarmForSubArea(sa.getSubAreaID(), fire);
				Date todayDate = new Date();
				Date endDate = fire.getToDate();
				Date fromDate = fire.getFromTime();
				if (endDate == null)
					endDate = todayDate;
				System.out.println(endDate.toString());
				System.out.println(todayDate.toString());
				if ((todayDate.after(fromDate) || todayDate.equals(fromDate))
						&& (todayDate.before(endDate) || todayDate.equals(endDate))) {
					System.out.println("Trigger Fire Event");
					new AnimationMainFrame("FIRE EVENT", user);
					clearAllSelection();
					return;
				} else {
					System.out.println("Fire Event Not Triggerred");
					Component frame = null;
					JOptionPane.showMessageDialog(frame, "FireAlarm not activated in SubArea", getWarningString(),
							JOptionPane.INFORMATION_MESSAGE);
					clearAllSelection();
					return;
				}
			}
			if (sa.getHasSensor() && isBurglary) {
				Sensor sensor = new Sensor();
				DbConnector.getSensorForSubArea(sa.getSubAreaID(), sensor);
				Date todayDate = new Date();
				Date endDate = sensor.getToDate();
				Date fromDate = sensor.getFromTime();
				if (endDate == null)
					endDate = todayDate;
				System.out.println(endDate.toString());
				System.out.println(todayDate.toString());
				if ((todayDate.after(fromDate) || todayDate.equals(fromDate))
						&& (todayDate.before(endDate) || todayDate.equals(endDate))) {
					System.out.println("Trigger Burglary Event");
					new AnimationMainFrame("BURGLARY EVENT", user);
					clearAllSelection();
					return;
				} else {
					Component frame = null;
					JOptionPane.showMessageDialog(frame, "Sensor not activated in SubArea", getWarningString(),
							JOptionPane.INFORMATION_MESSAGE);
					clearAllSelection();
					return;
				}
			} else {
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "Sensor not activated in SubArea", getWarningString(),
						JOptionPane.INFORMATION_MESSAGE);
				clearAllSelection();
				return;
			}
		}
		clearAllSelection();
	}

	/**
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnClearSelection)) {
			System.out.println("Clear button clicked");
			clearAllSelection();
		}
		if (o.equals(btnSave)) {
			handleSaveButtonAction(e);
		}
		if (o.equals(btnTriggerEvent)) {
			handleTriggerEvent(e);
		}
		if (o.equals(btnGenerateBill)) {
			generateBill();
		}
	}
}
