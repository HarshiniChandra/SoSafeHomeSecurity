package homeSecurityFunctions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.DbConnector;
import homeSecurity.Building;
import homeSecurity.subArea;

public class drawpoly extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private static JLabel label;
	private ArrayList<Rectangle> rectList;
	private Building bld;
	private Graphics2D graph;
	private boolean paintRect = false;
	private ArrayList<subArea> selectedSubArea;

	public void insertImage(String fileName) {
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException ex) {
			System.out.println(ex.toString());
		}
	}
	
	public void addRectList(Rectangle rectangle) {
		rectList.add(rectangle);
	}

	public Building getBld() {
		return bld;
	}
/**
 * The layout of the building will be divided into subAreas.
 */
	public drawpoly() {
		selectedSubArea = new ArrayList<subArea>();
		bld = new Building(1, "simple-home-plan.png");
		subArea sa1 = new subArea(1, 31, 66, 97, 176, false, false,0,0);
		bld.addSubArea(sa1);
		subArea sa2 = new subArea(2, 131, 66, 247, 176, true, true,0, 0);
		bld.addSubArea(sa2);
		subArea sa3 = new subArea(3, 380, 66, 190, 176, false, true, 0, 0);
		bld.addSubArea(sa3);
		subArea sa4 = new subArea(4, 107, 245, 192, 151, false, false, 0, 0);
		bld.addSubArea(sa4);
		subArea sa5 = new subArea(5, 302, 245, 113, 151, true, false, 0, 0);
		bld.addSubArea(sa5);
		subArea sa6 = new subArea(6, 421, 245, 116, 151, false, false, 0, 0);
		bld.addSubArea(sa6);
		System.out.println("HERE !!!!!!!!");
		insertImage(bld.getImagePath());
		label = new JLabel(new ImageIcon(image));
		label.addMouseListener((MouseListener) this);
		this.setPreferredSize(new Dimension(600,450));
		addSensorIndicators();
		add(label);
	}

	public ArrayList<subArea> getSelectedSubArea() {
		return selectedSubArea;
	}
/**
 * For each of the subArea it is drawn as a rectangle.
 * On clicking on the image layout it will show the rectangle in selected area.
 * @param building
 */
	public drawpoly(Building building) {
		bld = building;
		selectedSubArea = new ArrayList<subArea>();
		rectList = new ArrayList<Rectangle>();
		DbConnector.getBldSubAreas(this);
		insertImage(bld.getImagePath());
		System.out.println(bld.getSa().size());
		Iterator<subArea> i = bld.getSa().iterator();
		while(i.hasNext()) {
			subArea s = i.next();
			System.out.println(s.toString());
			Rectangle rect = new Rectangle(s.getCorX(), s.getCorY(), s.getWidth(), s.getHeight());
			rectList.add(rect);
		}
		label = new JLabel();
		label.addMouseListener((MouseListener) this);
		label.setIcon(new ImageIcon(image));
		addSensorIndicators();
		this.setPreferredSize(new Dimension(600,450));
		add(label);
	}
/**
 * Set the Sensors and Fire Alarm Indicators as green and Red Respectively.
 */
	private void addSensorIndicators() {
		graph = image.createGraphics();
		Iterator<subArea> i = bld.getSa().iterator();
		while(i.hasNext()) {
			subArea s = i.next();
			if(s.getHasSensor()) {
				System.out.println(s.toString());
				graph.setColor(Color.GREEN);
				graph.fillOval(s.getCorX()+10, s.getCorY()+20, 20, 20);
			}
			if(s.getHasFire()) {
				System.out.println(s.toString());
				graph.setColor(Color.RED);
				graph.fillOval(s.getCorX()+40, s.getCorY()+20, 20, 20);
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new drawpoly());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		gd.getDisplayMode().getWidth();
		gd.getDisplayMode().getHeight();
		frame.setSize(600, 450);
		frame.setVisible(true);
	}
	/**
	 * Paint the rectangle on the Image to show the Indicator.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (paintRect) {
			insertImage(bld.getImagePath());
			paintRect = false;
			label.setIcon(null);
			label.setIcon(new ImageIcon(image));
			addSensorIndicators();
		}
	}

	public void setPaintRect(boolean flag) {
		paintRect = flag;
	}
	
	public static JLabel getlabel() {
		return label;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(label)) {
			Rectangle r= null;
			subArea s = null;
			System.out.println("Label clicked " + e.getX() + " " + e.getY());
			ArrayList<subArea> saList = bld.getSa();
			Iterator<subArea> it = saList.iterator();
			while (it.hasNext()) {
				s = it.next();
				r = new Rectangle(s.getCorX(), s.getCorY(), s.getWidth(), s.getHeight());
				if(r.contains(e.getX(), e.getY()))
				{
					System.out.println("Point is in "+r.toString());
					break;
				}
			}
			selectedSubArea.add(s);
			graph = image.createGraphics();
			graph.setColor(new Color(0.40f, 0f, 0f, 0.40f));//RED
			graph.fillRect(s.getCorX(), s.getCorY(), s.getWidth(), s.getHeight());
			repaint();
			graph.dispose();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public void clearSelected() {
		System.out.println(selectedSubArea.size());
		selectedSubArea.removeAll(selectedSubArea);
		System.out.println(selectedSubArea.size());
	}
}