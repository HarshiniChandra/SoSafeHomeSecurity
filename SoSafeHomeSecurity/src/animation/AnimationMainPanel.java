package animation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class AnimationMainPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;
	private AnimationImage currentPosition = new AnimationImage();
	private Clip clip;
	private String clipFile;
	private Timer t1;
	private Timer t2;
	private ImageIcon image_icon;
	private int frameHeight;
	private int width;
	private String eventType;

	/**
	 * Main Panel for Animation which has 2 timers one to move the image and the
	 * other to play the sound, this will be done based on the kind of event
	 * 
	 * @param eventType
	 */
	public AnimationMainPanel(String eventType) {
		this.eventType = eventType;
		if (eventType.equals("FIRE EVENT")) {
			image_icon = new ImageIcon("Beedoo-Minion.png");
			width = 300;
			setFrameHeight(200);
			clipFile = "beedoo.wav";
		} else {
			image_icon = new ImageIcon("mail.png");
			width = 600;
			setFrameHeight(200);
			clipFile = "beedoo.wav";
		}
		// get ImageIcon image_icon and
		// store it in Image image
		image = image_icon.getImage();
		prepareImage(image, this);
		currentPosition.setX(width / 2 - 50);
		// set currentPosition's X and Y Position
		currentPosition.setY(20);
		this.setBackground(Color.WHITE);
		setDoubleBuffered(true);
		t1 = new Timer(25, paintTimer);
		t1.setActionCommand("AnimationTimer");
		t1.start();
		if (eventType.equals("FIRE EVENT")) {
			t2 = new Timer(5500, paintTimer);
			t2.setActionCommand("SoundTimer");
			t2.start();
			playSound();
		}
	}

	/**
	 * This function is overiding the default paint method to draw the image at
	 * a given position.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		// Draws the Image at the
		// correct X and Y co-ordinates.
		g2d.drawImage(image, currentPosition.getX(), currentPosition.getY(), this);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	/**
	 * This method is to play sound with Fire Hazard happens
	 */
	public void playSound() {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(clipFile));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}

	/**
	 * This is the function that will be called when the timer expires ... It
	 * will set the position of the image.
	 */
	Action paintTimer = new AbstractAction() {

		private static final long serialVersionUID = 1L;
		// Play music and then animation
		int direction = 1;

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("AnimationTimer")) {
				if (currentPosition.getY() >= 100) {
					direction = 0;
				}
				if (currentPosition.getY() == 20) {
					direction = 1;
				}
				// set X and Y co-ordinates that will then be fetched when
				// drawing the ball Image on the JPanel.
				// System.out.println("Direction: " + direction + " Y point: " +
				// currentPosition.getY());
				if (direction == 1) {
					currentPosition.setX(currentPosition.getX() + 0);
					currentPosition.setY(currentPosition.getY() + 5);
				} else {
					currentPosition.setX(currentPosition.getX() + 0);
					currentPosition.setY(currentPosition.getY() - 5);
				}
				repaint();
			} else {
				playSound();
			}
		}
	};

	public void closeAllApps() {
		t1.stop();
		if (eventType.equals("FIRE EVENT")) {
			t2.stop();
		}
	}

	/**
	 * @return the clip
	 */
	public Clip getClip() {
		return clip;
	}

	/**
	 * @param clip
	 *            the clip to set
	 */
	public void setClip(Clip clip) {
		this.clip = clip;
	}

	/**
	 * @return the frameHeight
	 */
	public int getFrameHeight() {
		return frameHeight;
	}

	/**
	 * @param frameHeight
	 *            the frameHeight to set
	 */
	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}
}
