package homeSecurity;
/**
 * 
 * @author harshini
 * This shows the subArea with the co-ordinates, and whether it has the sensor or not.
 *
 */

public class subArea {
	private int subAreaID;
	private int corX;
	private int corY;
	private int width;
	private int height;
	private Boolean hasSensor;
	private Boolean hasFire;
/**
 * 
 * @param subAreaID
 * @param corX
 * @param corY
 * @param width
 * @param height
 * @param hasSensor
 * @param hasFire
 * @param sensorID
 * @param fireID
 */
	public subArea(int subAreaID, int corX, 
			int corY, int width, int height,
			Boolean hasSensor, Boolean hasFire,
			int sensorID, int fireID) {
		this.subAreaID = subAreaID;
		this.corX = corX;
		this.corY = corY;
		this.width = width;
		this.height = height;
		this.hasSensor = hasSensor;
		this.hasFire = hasFire;
	}
/**
 * 
 * @return subAreaID
 */
	public int getSubAreaID() {
		return subAreaID;
	}
/**
 * 
 * @param subAreaID
 */
	public void setSubAreaID(int subAreaID) {
		this.subAreaID = subAreaID;
	}

	public String toString() {
		return "subArea [corX=" + corX + ", corY=" + corY + ", width=" + width + ", height=" + height + ", hasSensor="
				+ hasSensor + ", hasFire=" + hasFire + "]";
	}

	public int getCorX() {
		return corX;
	}

	public void setCorX(int corX) {
		this.corX = corX;
	}

	public int getCorY() {
		return corY;
	}

	public void setCorY(int corY) {
		this.corY = corY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
/**
 * 
 * @return has Sensor 
 */
	public Boolean getHasSensor() {
		return hasSensor;
	}
/**
 * 
 * @param hasSensor
 */
	public void setHasSensor(Boolean hasSensor) {
		this.hasSensor = hasSensor;
	}
/**
 * 
 * @return hasFire
 */
	public Boolean getHasFire() {
		return hasFire;
	}
/**
 * 
 * @param hasFire 
 */
	public void setHasFire(Boolean hasFire) {
		this.hasFire = hasFire;
	}

}
