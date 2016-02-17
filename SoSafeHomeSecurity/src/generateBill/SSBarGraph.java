package generateBill;
/**
 * In this frame it shows the User Bill information
 * in the left side and the bill BarGraph in the right side.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import database.DbConnector;
import homeSecurity.User;

public class SSBarGraph extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5640805373698616941L;
	private Map<Integer, Integer> hash;
	JPanel panelLeft;
	JPanel Panel;
	JButton btnBack;
	User user;
	Color c = new Color(255,255,105);
	Font f = new Font("Lucida Grande", Font.BOLD, 17);

	/**
	 * This method will read the given file line by line.
	 */
	public void getDataFromDB() {
		DbConnector.getSensorCountClassData(1, this);
		DbConnector.getFireCountClassData(1, this);
	}

	public int getLength() {
		return hash.size();
	}


	public Map<Integer, Integer> getHash() {
		return hash;
	}

	/**
	 * 
	 * @param month
	 * @param total
	 */
	public void addData(int month, int total) {
		if(hash.get(month) == null) {
			//Insert if nothing found
			System.out.println("new Month: "+month+" total"+total);
			hash.put(new Integer(month), new Integer(total));
		} else {
			//Update total if found
			System.out.println("Update Month: "+month+" total"+total);
			hash.put(new Integer(month), hash.get(new Integer(month)) + new Integer(total));
		}
	}
/**
 * 
 * @param user
 */
	public SSBarGraph(User user) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		this.user = user;
		hash = new HashMap<Integer, Integer>();

		getDataFromDB();
		getContentPane().setLayout(null);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setSize(new Dimension(1275, 700));
		getContentPane().add(splitPane);
		
		panelLeft = new JPanel();
		panelLeft.setBorder(new TitledBorder(null, "USER INFORMATION", TitledBorder.CENTER, TitledBorder.TOP, f, null));
		createLeftPanel();
		splitPane.setLeftComponent(panelLeft);
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		splitPane.setDividerLocation(0.5);
		JPanel jp = createNewPanel();
		getContentPane();
		splitPane.setRightComponent(jp);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		System.out.println("Width:" + width + " Height:" + height);
		setSize(width, height);
	}
	/**
	 * In this method it gives the billing information. 
	 * Number of Sensors, Number of FireAlarms and Total Cost!
	 */
	private void createLeftPanel() {
		System.out.println("I am here and I am done !!!");

		panelLeft.setLayout(new GridLayout(8,2));
		panelLeft.setVisible(true);
		panelLeft.setBackground(c);
		int totalSensors = DbConnector.getTotalSensors(user.getUserID());
		int totalFireAlarms = DbConnector.getTotalFireAlarms(user.getUserID());

		
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setBounds(26, 51, 72, 23);
		panelLeft.add(lblUsername);
		
		JLabel lblNewLabel_1 = new JLabel(user.getUserName());
		lblNewLabel_1.setBounds(158, 52, 90, 20);
		panelLeft.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("BuildingId");
		lblNewLabel.setBounds(26, 86, 85, 23);
		panelLeft.add(lblNewLabel);
		
		JLabel label = new JLabel("111");
		label.setBounds(158, 87, 71, 20);
		panelLeft.add(label);
		//Total Number of Sensors used 
		JLabel lblNoOfSensors = new JLabel("Total Number of Sensors");
		lblNoOfSensors.setBounds(26, 118, 90, 29);
		panelLeft.add(lblNoOfSensors);
		
		JLabel lblNewLabel_3 = new JLabel(Integer.toString(totalSensors));
		lblNewLabel_3.setBounds(158, 118, 71, 23);
		panelLeft.add(lblNewLabel_3);
		
		JLabel lblSensors = new JLabel("SensorCost");
		lblSensors.setBounds(26, 153, 90, 22);
		panelLeft.add(lblSensors);
		
		int costSensors = 10*totalSensors;
		JLabel lblNewLabel_2 = new JLabel(Integer.toString(costSensors));
		lblNewLabel_2.setBounds(158, 153, 71, 23);
		panelLeft.add(lblNewLabel_2);
		//total Number of FireAlarms used
		JLabel lblFirealarm = new JLabel("Total Number of FireAlarms");
		lblFirealarm.setBounds(26, 190, 90, 22);
		panelLeft.add(lblFirealarm);
		
		JLabel label_1 = new JLabel(Integer.toString(totalFireAlarms));
		label_1.setBounds(158, 193, 61, 16);
		panelLeft.add(label_1);
		
		//Fire Alarm cost calculated
		int costFireAlarms = 5*totalFireAlarms;
		JLabel lblNewLabel_4 = new JLabel("FireAlarms Cost");
		lblNewLabel_4.setBounds(26, 228, 110, 23);
		panelLeft.add(lblNewLabel_4);
		
		JLabel label_2 = new JLabel(Integer.toString(costFireAlarms));
		label_2.setBounds(158, 229, 61, 20);
		panelLeft.add(label_2);
		
		JLabel lblTotalCost = new JLabel("Total");
		lblTotalCost.setBounds(26, 277, 72, 22);
		panelLeft.add(lblTotalCost);
		
		//Total Cost of the Usage
		int totalCost = costFireAlarms+ costSensors;
		JLabel lblNewLabel_5 = new JLabel(Integer.toString(totalCost));
		lblNewLabel_5.setBounds(158, 280, 61, 16);
		panelLeft.add(lblNewLabel_5);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(this);
		btnBack.setBounds(47, 311, 117, 29);
		panelLeft.add(btnBack);

		panelLeft.validate();
		panelLeft.repaint();
		System.out.println("Done add all to center");
	}

	/**
	 * Panel to draw SSBarGraph
	 * 
	 * @return JPanel
	 */
	public JPanel createNewPanel() {
		JPanel jp = new JPanel();
		jp.setBounds(5, 0, 600, 800);
		jp.setBorder(new TitledBorder(null, "BAR GRAPH OF CHARGES PER MONTH", TitledBorder.CENTER, TitledBorder.TOP, f, null));
		DrawBarChart db = new DrawBarChart(this);
		jp.setBackground(c);
		jp.add(db);
		jp.setVisible(true);
		return jp;
	}
	
	public static void main(String[] args) {
		User user = new User();
		user.setUserID(1);
		new SSBarGraph(user);
	}

	/**
	 * 
	 * @param month
	 * @return month name
	 */

	public String getMonthName(int month) {
		return new DateFormatSymbols().getMonths()[month- 1];
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}

}
