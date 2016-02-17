package homeSecurity;

import java.util.Date;

public class Sensor {
	private int sensorID;
	private long userID;
	private int subAreaID;
	private Date fromTime;
	private Date toDate;
	private Boolean isArmed;
	private Date active_date;

	@Override
	public String toString() {
		return "Sensor [sensorID=" + sensorID + ", userID=" + userID + ", subAreaID=" + subAreaID + ", fromTime="
				+ fromTime + ", toDate=" + toDate + ", isArmed=" + isArmed + ", active_date=" + active_date + "]";
	}
/**
 * 
 * @return userID
 */
	public long getUserID() {
		return userID;
	}
/**
 * 
 * @param userID
 */
	public void setUserID(long userID) {
		this.userID = userID;
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
/**
 * 
 * @return sensorID
 */
	public int getSensorID() {
		return sensorID;
	}
/**
 * 
 * @param sensorID
 */
	public void setSensorID(int sensorID) {
		this.sensorID = sensorID;
	}
/**
 * 
 * @return fromTime
 */
	public Date getFromTime() {
		return fromTime;
	}
/**
 * 
 * @param fromTime
 */
	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}
/**
 * 
 * @return toDate
 */
	public Date getToDate() {
		return toDate;
	}
/**
 * 
 * @param toDate
 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Boolean getIsArmed() {
		return isArmed;
	}

	public void setIsArmed(Boolean isArmed) {
		this.isArmed = isArmed;
	}

	/**
	 * @return the active_date
	 */
	public Date getActive_date() {
		return active_date;
	}

	/**
	 * @param active_date the active_date to set
	 */
	public void setActive_date(Date active_date) {
		this.active_date = active_date;
	}

}
