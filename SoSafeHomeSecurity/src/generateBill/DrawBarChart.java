package generateBill;
/**
 * BarGraph shows the Activity Log of the Bill of the month.
 * It will shows the cost of the bill in the y-axis and months in the x-axis.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class DrawBarChart extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SSBarGraph data;
/**
 * This method Draws the bar chart
 */
	public DrawBarChart() {
	}

	public DrawBarChart(SSBarGraph sSBarGraph) {
		data = sSBarGraph;
	}

	/**
	 * Specifications about the barChart 
	 */
	public void paintComponent(Graphics g) {
		setBackground(Color.white);
		setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
		g.setColor(Color.red);
		g.drawLine(50, 500, 550, 500);
		g.drawLine(75, 5, 75, 530);
		g.setFont(new Font("SansSerif", Font.PLAIN, 10));
		int indy= 500;
		Integer val=0;
		for(int i=0; i<= 28; i++) {
			g.drawLine(70, indy, 80, indy);
			g.drawString(val.toString(), 40, indy);
			val = val+5;
			indy = indy-20;
		}
		g.setColor(Color.black);
		int indx = 100;
		Map<Integer, Integer> hash = data.getHash();
		for (Map.Entry<Integer, Integer> entry : hash.entrySet()) {
		    int p = entry.getValue();
			g.fillRect(indx, 500-(p*4), 50, (p*4));
			g.drawString(data.getMonthName(entry.getKey()),indx, 520);
			indx = indx+90;
		}
	}
	public Dimension getPreferredSize() {
		return new Dimension(550, 630);
	}

}
