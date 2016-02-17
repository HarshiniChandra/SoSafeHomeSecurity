package homeSecurity;
/**
 * Building with building Id, ImagePath, and SubAreas
 */
import java.util.ArrayList;

public class Building {
	private int bldID;
	private String imagePath;
	private ArrayList<subArea> saList;
/**
 * 
 * @param bldID
 * @param imagePath
 */
	public Building(int bldID, String imagePath) {
		this.bldID = bldID;
		this.setImagePath(imagePath);
		saList = new ArrayList<subArea>();
	}
/**
 * 
 * @return BuildingId
 */
	public int getBldID() {
		return bldID;
	}
/**
 * 
 * @param bldID
 */
	public void setBldID(int bldID) {
		this.bldID = bldID;
	}
/**
 * 
 * @return subArea list
 */
	public ArrayList<subArea> getSa() {
		return saList;
	}
/**
 * 
 * @param sa SubAre List
 */
	public void setSa(ArrayList<subArea> sa) {
		this.saList = sa;
	}
	/**
	 * 
	 * @param sa
	 */
	public void addSubArea(subArea sa) {
		this.saList.add(sa);
	}

	public String toString() {
		return "Building [bldID=" + bldID + ",imagePath="+imagePath +", sa=" + saList + "]";
	}
/**
 * 
 * @return imagePath
 */
	public String getImagePath() {
		return imagePath;
	}
/**
 * 
 * @param imagePath
 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
