package Model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class EmployeePayRollDBService {
	int Employee_ID;
	String EmployeeName, Department, gender, Country, address;
	public Double salary;

	public EmployeePayRollDBService() {
		super();
	}


	public EmployeePayRollDBService(int Employee_ID, String EmployeeName, String Department) {
		super();
		this.Employee_ID = Employee_ID;
		this.EmployeeName = EmployeeName;
		this.Department = Department;
	}

	public EmployeePayRollDBService(int Employee_ID, String EmployeeName, String Department, Double salary) {
		this(Employee_ID, EmployeeName, Department);
		this.salary = salary;
	}

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

	private static List<EmployeePayRollDBService> getEmployeeListData(String query) throws SQLException {
		Connection connection;
		List<EmployeePayRollDBService> employeePayRollJDBCs = new ArrayList<>();
		connection = getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		while (resultSet.next())
			employeePayRollJDBCs.add(
					new EmployeePayRollDBService(resultSet.getInt("Employee_ID"), resultSet.getString("EmployeeName"),
							resultSet.getString("Department"), resultSet.getDouble("salary")));
		System.out.println(employeePayRollJDBCs.size());
		employeePayRollJDBCs.forEach(System.out::println);
		statement.close();
		resultSet.close();
		connection.close();
		return employeePayRollJDBCs;
	}

	private static void getResultSetEmplpoyeeDB(String query) throws SQLException {
		Connection connection;
		connection = getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		displayEmployeeListDB(resultSet);
		statement.close();
		resultSet.close();
		connection.close();

	}

	private static void displayEmployeeListDB(ResultSet resultSet) throws SQLException {
		while (resultSet.next())
			System.out.println(resultSet.getString("Gender")+" -> (SUM->"+resultSet.getInt("Sum_Of_salary")+")-> (AVG-> "+resultSet.getInt("Avg_Of_salary")+")-> (MIN-> "+resultSet.getInt("Min_Of_salary")+")-> (MAX -> "+resultSet.getInt("Max_Of_salary")+") -> (Count->"+resultSet.getInt("Count")+")");
	}

	// UC1
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

	// UC2 Ability for Employee Payroll Service to retrieve the EmployeePayroll from the Database
	public static List<EmployeePayRollDBService> readData() {
		String query = "SELECT * FROM employee_payroll;";

		try {
			List<EmployeePayRollDBService> employeePayRollJDBCs  = getEmployeeListData(query);
		return employeePayRollJDBCs;
		} catch (SQLException e) {
			System.out.println("An error occurred while connecting MySQL databse");
			e.printStackTrace();
		}
		return null;
	}

	// UC3 Ability to update the salary i.e. the base pay for Employee Terisa to 3000000.00 and sync it with Database
	public static long updateEmployeePayrollData(String name, Double salary) {
		return updateEmployeePayrollDataUsingStatemnt(name, salary);
	}

	// UC3 Ability to update the salary i.e. the base pay for Employee Terisa to 3000000.00 and sync it with Database
	private static long updateEmployeePayrollDataUsingStatemnt(String name, Double salary) {
		String query = String.format("UPDATE employee_payroll SET salary=%.2f WHERE EmployeeName='%s';", salary, name);
		Connection connection;
		try {
			connection = getConnection();
			Statement statement = connection.createStatement();
			return statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
