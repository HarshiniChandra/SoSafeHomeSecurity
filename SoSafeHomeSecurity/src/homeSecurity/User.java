package homeSecurity;
/**
 * This file has the User Information, sensorList, and AlarmList
 */

import java.util.List;

public class User {
	private long userID;
	private String userName;
	private String password;
	private Building bld;
	private String phoneNumber;
	private String email;
	private List<Sensor> sensorList;
	private List<FireAlarm> alarmList;

	/**
	 * Parameterized Constructor with the following parameters.
	 * @param userID
	 * @param username
	 * @param password
	 * @param phoneNumber
	 * @param email
	 * @param building
	 * 
	 */
	public User(long userID, String username, 
			String password, String phoneNumber,
			String email, Building building) {
		this.userID = userID;
		this.userName = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.bld = building;
	}

	public User() {
	}

	/**
	 * This method is to get the phoneNumber of the user
	 * @return phoneNumber of the user
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * 
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * 
	 * @return email address of the User
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
		return "User [userID=" + userID + ", userName=" + userName + ", password=" + password + ", Building="
				+ bld.toString() + ", sensorList=" + sensorList + ", alarmList=" + alarmList + "]";
	}

	/**
	 * 
	 * @return userId of the User.
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
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @return buildingId
	 */
	public Building getBld() {
		return bld;
	}

	/**
	 * 
	 * @param bld
	 */
	public void setBldID(Building bld) {
		this.bld = bld;
	}

	/**
	 * 
	 * @return SensorList
	 */
	public List<Sensor> getSensorList() {
		return sensorList;
	}

	/**
	 * 
	 * @param sensorList
	 */
	public void setSensorList(List<Sensor> sensorList) {
		this.sensorList = sensorList;
	}

	/**
	 * 
	 * @return AlarmList
	 */

	public List<FireAlarm> getAlarmList() {
		return alarmList;
	}

	/**
	 * 
	 * @param alarmList
	 */
	public void setAlarmList(List<FireAlarm> alarmList) {
		this.alarmList = alarmList;
	}

}
