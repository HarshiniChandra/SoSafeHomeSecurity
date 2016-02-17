package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import generateBill.SSBarGraph;
import homeSecurity.Building;
import homeSecurity.FireAlarm;
import homeSecurity.Sensor;
import homeSecurity.User;
import homeSecurity.subArea;
import homeSecurityFunctions.drawpoly;

/**
 * This class contains all the methods to access the Database and fetch
 * information required.
 */
public class DbConnector {

	private static Connection conn;

	public DbConnector() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sosecurity?" + "user=ssuser&password=sspwd");
			System.out.println("I am connected to DB " + conn.toString());
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	/**
	 * Function to return the connection
	 * 
	 * @return connection to database
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * Function to close the connection
	 */
	public void closeConn() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to get the user information based to userName and password
	 * provided and validate
	 * 
	 * @param user
	 */
	public static void getUser(User user) {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sosecurity?" + "user=ssuser&password=sspwd");
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = (Statement) conn.createStatement();
				String getUser = "select * from SoSecurityUser s, building b where userName = \"" + user.getUserName()
						+ "\" and s.userID = b.userID and s.password = \"" + user.getPassword() + "\"";
				System.out.println(getUser);
				try {
					rs = stmt.executeQuery(getUser);
					try {
						while (rs.next()) {
							String imagePath = null;
							int bldID = 0;
							int numColumns = rs.getMetaData().getColumnCount();
							for (int i = 1; i <= numColumns; i++) {
								user.setUserID(rs.getLong("userID"));
								user.setUserName(rs.getString("userName"));
								user.setPassword(rs.getString("password"));
								user.setPhoneNumber(rs.getString("phoneNumber"));
								user.setEmail(rs.getString("email"));
								imagePath = rs.getString("imagePath");
								bldID = rs.getInt("bldID");
							}
							Building b = new Building(bldID, imagePath);
							user.setBldID(b);
							System.out.println(user.toString());
							return;
						}
					} finally {
						try {
							rs.close();
						} catch (Throwable ignore) {
							/*
							 * Propagate the original exception instead of this
							 * one that you may want just logged
							 */
							ignore.printStackTrace();
						}
					}
				} finally {
					try {
						stmt.close();
					} catch (Throwable ignore) {
						/*
						 * Propagate the original exception instead of this one
						 * that you may want just logged
						 */
						ignore.printStackTrace();
					}
				}
			} finally {
				// It's important to close the connection when you are done with
				// it
				try {
					conn.close();
				} catch (Throwable ignore) {
					/*
					 * Propagate the original exception instead of this one that
					 * you may want just logged
					 */
					ignore.printStackTrace();
				}
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return;
	}

	/**
	 * Fetch the sub-areas for a building that will be used to select the
	 * subareas when clicked on it.
	 * 
	 * @param dp
	 */
	public static void getBldSubAreas(drawpoly dp) {
		Building b = dp.getBld();
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sosecurity?" + "user=ssuser&password=sspwd");
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = (Statement) conn.createStatement();
				String getSubArea = "select * from subArea where bldID =" + b.getBldID();
				System.out.println(getSubArea);
				try {
					rs = stmt.executeQuery(getSubArea);
					try {
						while (rs.next()) {
							int numColumns = rs.getMetaData().getColumnCount();
							Sensor s = new Sensor();
							FireAlarm f = new FireAlarm();
							int corX = 0;
							int corY = 0;
							int width = 0;
							int height = 0;
							int subAreaID = 0;
							Boolean hasSensor = false;
							Boolean hasFire = false;
							for (int i = 1; i <= numColumns; i++) {
								corX = rs.getInt("corX");
								corY = rs.getInt("corY");
								width = rs.getInt("width");
								height = rs.getInt("height");
								subAreaID = rs.getInt("subAreaID");
								hasSensor = rs.getBoolean("hasSensor");
								hasFire = rs.getBoolean("hasFire");
							}
							// Get sensor for this subArea
							System.out.println("Get sensor for subArea " + subAreaID + " numcols: " + numColumns);
							getSensorForSubArea(subAreaID, s);
							getFireAlarmForSubArea(subAreaID, f);
							subArea sa = new subArea(subAreaID, corX, corY, width, height, hasSensor, hasFire, 0, 0);
							b.addSubArea(sa);
						}
					} finally {
						try {
							rs.close();
						} catch (Throwable ignore) {
							/*
							 * Propagate the original exception instead of this
							 * one that you may want just logged
							 */
							System.out.println(ignore);
						}
					}
				} finally {
					try {
						stmt.close();
					} catch (Throwable ignore) {
						/*
						 * Propagate the original exception instead of this one
						 * that you may want just logged
						 */
						System.out.println(ignore);
					}
				}
			} finally {
				// It's important to close the connection when you are done with
				// it
				try {
					conn.close();
				} catch (Throwable ignore) {
					/*
					 * Propagate the original exception instead of this one that
					 * you may want just logged
					 */
					System.out.println(ignore);
				}
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return;
	}

	/**
	 * Fetch the fireAlarm object in the selected subArea
	 * 
	 * @param subAreaID
	 * @param f
	 */

	public static void getFireAlarmForSubArea(int subAreaID, FireAlarm f) {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sosecurity?" + "user=ssuser&password=sspwd");
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = (Statement) conn.createStatement();
				String getSensor = "select * from fireAlarm s where s.subAreaID=" + subAreaID;
				System.out.println(getSensor);
				try {
					rs = stmt.executeQuery(getSensor);
					try {
						while (rs.next()) {
							int numColumns = rs.getMetaData().getColumnCount();
							java.sql.ResultSetMetaData rsmd = rs.getMetaData();
							for (int i = 1; i <= numColumns; i++) {
								System.out.println("Here !! " + rsmd.getColumnLabel(i) + " numcols:" + numColumns);
								f.setFireAlarmID(rs.getInt("fireAlarmID"));
								f.setFromTime(rs.getDate("start_time"));
								f.setToDate(rs.getDate("end_time"));
								f.setIsArmed(rs.getBoolean("isArmed"));
							}
							System.out.println(f.toString());
						}
					} finally {
						try {
							rs.close();
						} catch (Throwable ignore) {
							/*
							 * Propagate the original exception instead of this
							 * one that you may want just logged
							 */
						}
					}
				} finally {
					try {
						stmt.close();
					} catch (Throwable ignore) {
						/*
						 * Propagate the original exception instead of this one
						 * that you may want just logged
						 */
					}
				}
			} finally {
				// It's important to close the connection when you are done with
				// it
				try {
					conn.close();
				} catch (Throwable ignore) {
					/*
					 * Propagate the original exception instead of this one that
					 * you may want just logged
					 */
				}
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return;
	}

	/**
	 * Fetch the sensor in the selected subArea.
	 * 
	 * @param subAreaID
	 * @param s
	 */
	public static void getSensorForSubArea(int subAreaID, Sensor s) {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sosecurity?" + "user=ssuser&password=sspwd");
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = (Statement) conn.createStatement();
				String getSensor = "select * from sensor s where s.subAreaID=" + subAreaID;
				System.out.println(getSensor);
				try {
					rs = stmt.executeQuery(getSensor);
					try {
						while (rs.next()) {
							// rs = stmt.getResultSet();
							int numColumns = rs.getMetaData().getColumnCount();
							java.sql.ResultSetMetaData rsmd = rs.getMetaData();
							for (int i = 1; i <= numColumns; i++) {
								System.out.println("Here !! " + rsmd.getColumnLabel(i) + " numcols:" + numColumns);
								s.setSensorID(rs.getInt("sensorID"));
								s.setFromTime(rs.getDate("start_time"));
								s.setToDate(rs.getDate("end_time"));
								s.setIsArmed(rs.getBoolean("isArmed"));
							}
							System.out.println(s.toString());
						}
					} finally {
						try {
							rs.close();
						} catch (Throwable ignore) {
							/*
							 * Propagate the original exception instead of this
							 * one that you may want just logged
							 */
						}
					}
				} finally {
					try {
						stmt.close();
					} catch (Throwable ignore) {
						/*
						 * Propagate the original exception instead of this one
						 * that you may want just logged
						 */
					}
				}
			} finally {
				// It's important to close the connection when you are done with
				// it
				try {
					conn.close();
				} catch (Throwable ignore) {
					/*
					 * Propagate the original exception instead of this one that
					 * you may want just logged
					 */
				}
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return;
	}

	public static void main(String[] args) {
		// The newInstance() call is a work around for some
		// broken Java implementations

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		new DbConnector();
		User u = new User();
		u.setUserName("user1");
		u.setPassword("user1pwd");
		getUser(u);
		Building b = u.getBld();
		drawpoly dp = new drawpoly(b);
		getBldSubAreas(dp);
	}

	/**
	 * Updates the subArea with the fireAlarm or Sensor when enabled or disabled
	 * 
	 * @param sa
	 */
	public static void updateSubArea(subArea sa) {
		try {
			System.out.println("Here !!!");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sosecurity?" + "user=ssuser&password=sspwd");
			try {
				// String getSensor = "UPDATE sensor SET subAreaID=2, userID=1,
				// isArmed = true WHERE sensorID=1";
				String updateSubArea = "UPDATE subArea SET hasSensor= ?, hasFire=? WHERE subAreaID=?";
				System.out.println(updateSubArea);
				PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(updateSubArea);
				preparedStmt.setBoolean(1, sa.getHasSensor());
				preparedStmt.setBoolean(2, sa.getHasFire());
				preparedStmt.setInt(3, sa.getSubAreaID());

				// execute the java prepared statement
				int affectedRow = preparedStmt.executeUpdate();
				System.out.println(affectedRow);
			} finally {
				// It's important to close the connection when you are done with
				// it
				try {
					conn.close();
				} catch (Throwable ignore) {
					/*
					 * Propagate the original exception instead of this one that
					 * you may want just logged
					 */
				}
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return;
	}

	/**
	 * Insert a sensor to the Database with refereces to User and Building in
	 * which it is installed
	 * 
	 * @param sensor
	 */

	public static void insertSensor(Sensor sensor) {
		System.out.println("Insert sensor " + sensor.toString());
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sosecurity?" + "user=ssuser&password=sspwd");
			try {

				String updateSubArea = "insert into Sensor (subAreaID, userID, start_time, end_time, isArmed, active_date)"
						+ "values (?,?,?,?,?, ?)";
				System.out.println(updateSubArea);
				PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(updateSubArea);
				preparedStmt.setInt(1, sensor.getSubAreaID());
				preparedStmt.setLong(2, sensor.getUserID());
				preparedStmt.setDate(3, new java.sql.Date(sensor.getFromTime().getTime()));
				preparedStmt.setDate(6, new java.sql.Date(sensor.getActive_date().getTime()));
				if (sensor.getToDate() == null) {
					preparedStmt.setDate(4, null);
				} else {
					preparedStmt.setDate(4, new java.sql.Date(sensor.getToDate().getTime()));
				}
				preparedStmt.setBoolean(5, sensor.getIsArmed());

				// execute the java prepared statement
				int affectedRow = preparedStmt.executeUpdate();
				System.out.println(affectedRow);
			} finally {
				// It's important to close the connection when you are done with
				// it
				try {
					conn.close();
				} catch (Throwable ignore) {
					/*
					 * Propagate the original exception instead of this one that
					 * you may want just logged
					 */
				}
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return;
	}

	/**
	 * upDate the sensor with new start and end time and also enabled/disabled
	 * information in Database
	 * 
	 * @param sensor
	 */
	public static void updateSensor(Sensor sensor) {
		System.out.println("Update sensor " + sensor.toString());
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sosecurity?" + "user=ssuser&password=sspwd");
			try {
				// String getSensor = "UPDATE sensor SET subAreaID=2, userID=1,
				// isArmed = true WHERE sensorID=1";
				String updateSubArea = "UPDATE sensor SET subAreaID= ? , userID= ?, isArmed=?, start_time=?, end_time= ? "
						+ "WHERE sensorID=?";
				System.out.println(updateSubArea);
				PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(updateSubArea);
				preparedStmt.setInt(1, sensor.getSubAreaID());
				preparedStmt.setLong(2, sensor.getUserID());
				preparedStmt.setBoolean(3, sensor.getIsArmed());
				preparedStmt.setDate(4, new java.sql.Date(sensor.getFromTime().getTime()));
				if (sensor.getToDate() == null) {
					preparedStmt.setDate(5, null);
				} else {
					preparedStmt.setDate(5, new java.sql.Date(sensor.getToDate().getTime()));
				}
				preparedStmt.setInt(6, sensor.getSensorID());

				// execute the java prepared statement
				int affectedRow = preparedStmt.executeUpdate();
				System.out.println(affectedRow);
			} finally {
				// It's important to close the connection when you are done with
				// it
				try {
					conn.close();
				} catch (Throwable ignore) {
					/*
					 * Propagate the original exception instead of this one that
					 * you may want just logged
					 */
					ignore.printStackTrace();
				}
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return;
	}

	/**
	 * Update the fireAlaram table for a particular row with new start_time,
	 * end_time and enabled.disabled information
	 * 
	 * @param fire
	 */

	public static void updateFire(FireAlarm fire) {
		System.out.println("Update FireAlarm " + fire.toString());
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sosecurity?" + "user=ssuser&password=sspwd");
			try {

				String updateSubArea = "UPDATE fireAlarm SET subAreaID= ? , userID= ?, isArmed=?, start_time=?, end_time= ? "
						+ "WHERE fireAlarmID=?";
				System.out.println(updateSubArea);
				PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(updateSubArea);
				preparedStmt.setInt(1, fire.getSubAreaID());
				preparedStmt.setLong(2, fire.getUserID());
				preparedStmt.setBoolean(3, fire.getIsArmed());
				preparedStmt.setDate(4, new java.sql.Date(fire.getFromTime().getTime()));
				if (fire.getToDate() == null) {
					preparedStmt.setDate(5, null);
				} else {
					preparedStmt.setDate(5, new java.sql.Date(fire.getToDate().getTime()));
				}
				preparedStmt.setInt(6, fire.getFireAlarmID());

				// execute the java prepared statement
				int affectedRow = preparedStmt.executeUpdate();
				System.out.println(affectedRow);
			} finally {
				// It's important to close the connection when you are done with
				// it
				try {
					conn.close();
				} catch (Throwable ignore) {
					/*
					 * Propagate the original exception instead of this one that
					 * you may want just logged
					 */
					ignore.printStackTrace();
				}
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return;
	}

	/**
	 * Insert a new row into the table fireAlarm in database.
	 * 
	 * @param fire
	 */
	public static void insertFireAlarm(FireAlarm fire) {
		System.out.println("Insert FireAlarm " + fire.toString());
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sosecurity?" + "user=ssuser&password=sspwd");
			try {
				// String getSensor = "UPDATE sensor SET subAreaID=2, userID=1,
				// isArmed = true WHERE sensorID=1";
				String updateSubArea = "insert into fireAlarm (subAreaID, userID, start_time, end_time, isArmed, active_date)"
						+ "values (?,?,?,?,?,?)";
				System.out.println(updateSubArea);
				PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(updateSubArea);
				preparedStmt.setInt(1, fire.getSubAreaID());
				preparedStmt.setLong(2, fire.getUserID());
				preparedStmt.setDate(3, new java.sql.Date(fire.getFromTime().getTime()));
				if (fire.getToDate() == null) {
					preparedStmt.setDate(4, null);
				} else {
					preparedStmt.setDate(4, new java.sql.Date(fire.getToDate().getTime()));
				}
				preparedStmt.setBoolean(5, fire.getIsArmed());
				preparedStmt.setDate(6, new java.sql.Date(fire.getActive_date().getTime()));

				// execute the java prepared statement
				int affectedRow = preparedStmt.executeUpdate();
				System.out.println(affectedRow);
			} finally {
				// It's important to close the connection when you are done with
				// it
				try {
					conn.close();
				} catch (Throwable ignore) {
					/*
					 * Propagate the original exception instead of this one that
					 * you may want just logged
					 */
				}
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return;
	}

	/**
	 * Get the total number of sensors activated for a particular user from the
	 * Database
	 * 
	 * @param userID
	 * @return Number of Sensors
	 */
	public static int getTotalSensors(long userID) {
		System.out.println("Get Total FireAlarms " + userID);
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sosecurity?" + "user=ssuser&password=sspwd");
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = (Statement) conn.createStatement();
				String countSensor = "select count(1) as totalSensors from sensor where userID = " + userID;
				System.out.println(countSensor);
				try {
					rs = stmt.executeQuery(countSensor);
					try {
						int count = 0;
						while (rs.next()) {
							int numColumns = rs.getMetaData().getColumnCount();
							for (int i = 1; i <= numColumns; i++) {
								count = rs.getInt("totalSensors");
							}
							System.out.println("Count = " + count);
							return count;
						}
					} finally {
						try {
							rs.close();
						} catch (Throwable ignore) {
							/*
							 * Propagate the original exception instead of this
							 * one that you may want just logged
							 */
							ignore.printStackTrace();
						}
					}
				} finally {
					try {
						stmt.close();
					} catch (Throwable ignore) {
						/*
						 * Propagate the original exception instead of this one
						 * that you may want just logged
						 */
						ignore.printStackTrace();
					}
				}
			} finally {
				// It's important to close the connection when you are done
				try {
					conn.close();
				} catch (Throwable ignore) {
					/*
					 * Propagate the original exception instead of this one that
					 * you may want just logged
					 */
					ignore.printStackTrace();
				}
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return 0;
	}

	/**
	 * Get the total number of FireAlarms activated for a particular user from
	 * the Database
	 * 
	 * @param userID
	 * @return Number of FireAlarms
	 */
	public static int getTotalFireAlarms(long userID) {
		System.out.println("Get Total FireAlarms " + userID);
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sosecurity?" + "user=ssuser&password=sspwd");
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = (Statement) conn.createStatement();
				String countSensor = "select count(1) as totalFire from fireAlarm where userID = " + userID;
				System.out.println(countSensor);
				try {
					rs = stmt.executeQuery(countSensor);
					try {
						int count = 0;
						while (rs.next()) {
							int numColumns = rs.getMetaData().getColumnCount();
							for (int i = 1; i <= numColumns; i++) {
								count = rs.getInt("totalFire");
							}
							System.out.println("Count = " + count);
							return count;
						}
					} finally {
						try {
							rs.close();
						} catch (Throwable ignore) {
							/*
							 * Propagate the original exception instead of this
							 * one that you may want just logged
							 */
							ignore.printStackTrace();
						}
					}
				} finally {
					try {
						stmt.close();
					} catch (Throwable ignore) {
						/*
						 * Propagate the original exception instead of this one
						 * that you may want just logged
						 */
						ignore.printStackTrace();
					}
				}
			} finally {
				// It's important to close the connection when you are done
				try {
					conn.close();
				} catch (Throwable ignore) {
					/*
					 * Propagate the original exception instead of this one that
					 * you may want just logged
					 */
					ignore.printStackTrace();
				}
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return 0;
	}

	/**
	 * Get total number of sensor permonth for a particular user.
	 * 
	 * @param userID
	 * @param bg
	 */
	public static void getSensorCountClassData(long userID, SSBarGraph bg) {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sosecurity?" + "user=ssuser&password=sspwd");
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = (Statement) conn.createStatement();
				String getCC = "SELECT month, count, @total:=@total+count AS total FROM (SELECT @total:=0) t "
						+ "STRAIGHT_JOIN (SELECT MONTH(active_date) AS month, COUNT(*) AS count FROM sensor "
						+ "where userID = " + userID + " and MONTH(active_date) <= MONTH(CURRENT_DATE()) GROUP"
						+ " BY MONTH(active_date)) AS m";
				System.out.println(getCC);
				try {
					rs = stmt.executeQuery(getCC);
					try {
						while (rs.next()) {
							int numColumns = rs.getMetaData().getColumnCount();
							int count = 0;
							int total = 0;
							int month = 0;

							for (int i = 1; i <= numColumns; i++) {
								count = rs.getInt("count");
								total = rs.getInt("total");
								month = rs.getInt("month");
							}
							System.out.println("[month=" + month + ", count=" + count + ", total=" + total + "]");
							bg.addData(month, total * 10);
						}
					} finally {
						try {
							rs.close();
						} catch (Throwable ignore) {
							/*
							 * Propagate the original exception instead of this
							 * one that you may want just logged
							 */
							System.out.println(ignore);
						}
					}
				} finally {
					try {
						stmt.close();
					} catch (Throwable ignore) {
						/*
						 * Propagate the original exception instead of this one
						 * that you may want just logged
						 */
						System.out.println(ignore);
					}
				}
			} finally {
				// It's important to close the connection when you are done with
				// it
				try {
					conn.close();
				} catch (Throwable ignore) {
					/*
					 * Propagate the original exception instead of this one that
					 * you may want just logged
					 */
					System.out.println(ignore);
				}
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return;
	}

	/**
	 * get total number of fireAlarms for a particular user for a particular
	 * month.
	 * 
	 * @param userID
	 * @param bg
	 */
	public static void getFireCountClassData(long userID, SSBarGraph bg) {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sosecurity?" + "user=ssuser&password=sspwd");
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = (Statement) conn.createStatement();
				String getCC = "SELECT month, count, @total:=@total+count AS total FROM (SELECT @total:=0) t "
						+ "STRAIGHT_JOIN (SELECT MONTH(active_date) AS month, COUNT(*) AS count FROM fireAlarm "
						+ "where userID = " + userID + " and MONTH(active_date) <= MONTH(CURRENT_DATE()) GROUP"
						+ " BY MONTH(active_date)) AS m";
				System.out.println(getCC);
				try {
					rs = stmt.executeQuery(getCC);
					try {
						while (rs.next()) {
							int numColumns = rs.getMetaData().getColumnCount();
							int count = 0;
							int total = 0;
							int month = 0;

							for (int i = 1; i <= numColumns; i++) {
								count = rs.getInt("count");
								total = rs.getInt("total");
								month = rs.getInt("month");
							}
							System.out.println("[month=" + month + ", count=" + count + ", total=" + total + "]");
							bg.addData(month, total * 5);
						}
					} finally {
						try {
							rs.close();
						} catch (Throwable ignore) {
							/*
							 * Propagate the original exception instead of this
							 * one that you may want just logged
							 */
							System.out.println(ignore);
						}
					}
				} finally {
					try {
						stmt.close();
					} catch (Throwable ignore) {
						/*
						 * Propagate the original exception instead of this one
						 * that you may want just logged
						 */
						System.out.println(ignore);
					}
				}
			} finally {
				// It's important to close the connection when you are done with
				// it
				try {
					conn.close();
				} catch (Throwable ignore) {
					/*
					 * Propagate the original exception instead of this one that
					 * you may want just logged
					 */
					System.out.println(ignore);
				}
			}
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return;
	}

}
