package Model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class EmployeePayRollDBService {

	private static Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(IOService.DATABASE_IO.url, IOService.DATABASE_IO.userName,
				IOService.DATABASE_IO.password);
		System.out.println("Successfully connected to MySQL database test" + connection);
		return connection;
	}

	private static void getListDriver() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while (driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println("   " + driverClass.getClass().getName());
		}
	}

	// UC1 Ability to create a payroll service database and have java program connect to database
	public static void connectToMysql() {
		try {
			Class.forName(IOService.DRIVER_IO.file);
			System.out.println("Driver loaded");
			getListDriver(); // Driver list
			getConnection();
		} catch (SQLException e) {
			System.out.println("An error occurred while connecting MySQL databse");
			e.getMessage();
		} catch (ClassNotFoundException e) {
			System.out.println("cannot find the driver in the class path !");
			e.getMessage();
		}

	}
}
