	package homeSecurity;

import java.util.Date;

public class FireAlarm {

	private int fireAlarmID;
	private Date fromTime;
	private Date toDate;
	private Boolean isArmed;
	private int subAreaID;
	private Long userID;
	private Date active_date;


	@Override
	public String toString() {
		return "FireAlarm [fireAlarmID=" + fireAlarmID + ", fromTime=" + fromTime + ", toDate=" + toDate + ", isArmed="
				+ isArmed + ", subAreaID=" + subAreaID + ", userID=" + userID + ", active_date=" + active_date + "]";
	}

	public int getSubAreaID() {
		return subAreaID;
	}

	public void setSubAreaID(int subAreaID) {
		this.subAreaID = subAreaID;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public int getFireAlarmID() {
		return fireAlarmID;
	}

	public void setFireAlarmID(int fireAlarmID) {
		this.fireAlarmID = fireAlarmID;
	}

	public Date getFromTime() {
		return fromTime;
	}

	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}

	public Date getToDate() {
		return toDate;
	}

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
