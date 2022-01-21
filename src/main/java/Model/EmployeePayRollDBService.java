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
	static List<EmployeePayRollDBService> employeePayRollDBServicesListNEW;
	private int sum,avg,count,max,min;

	public EmployeePayRollDBService() {
		super();
	}

	public EmployeePayRollDBService(int sum,int avg,int max,int min,int count) {
		super();
		this.sum = sum;
		this.avg = avg;
		this.count = count;
		this.max = max;
		this.min = min;
	}

	public EmployeePayRollDBService(int Employee_ID, String EmployeeName) {
		super();
		this.Employee_ID = Employee_ID;
		this.EmployeeName = EmployeeName;
	}

	public EmployeePayRollDBService(int Employee_ID, String EmployeeName,Double salary) {
		this(Employee_ID, EmployeeName);
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
					new EmployeePayRollDBService(resultSet.getInt("Employee_ID"), resultSet.getString("EmployeeName"), resultSet.getDouble("salary")));
		System.out.println(employeePayRollJDBCs.size());
		employeePayRollJDBCs.forEach(System.out::println);
		statement.close();
		resultSet.close();
		connection.close();
		return employeePayRollJDBCs;
	}

	private static int getResultSetEmplpoyeeDB(String query) throws SQLException {
		Connection connection;
		connection = getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		int rs = displayEmployeeListDB(resultSet);
		statement.close();
		resultSet.close();
		connection.close();
		return rs;
	}

	private static int displayEmployeeListDB(ResultSet resultSet) throws SQLException {
		List<EmployeePayRollDBService> employeePayRollDBData = new ArrayList<>();
		while (resultSet.next())
			employeePayRollDBData.add(new EmployeePayRollDBService(resultSet.getInt("Sum_Of_salary"),resultSet.getInt("Avg_Of_salary"),resultSet.getInt("Min_Of_salary"),resultSet.getInt("Max_Of_salary"),resultSet.getInt("Count")));
//		System.out.println(resultSet.getString("Gender")+" -> (SUM->"+resultSet.getInt("Sum_Of_salary")+")-> (AVG-> "+resultSet.getInt("Avg_Of_salary")+")-> (MIN-> "+resultSet.getInt("Min_Of_salary")+")-> (MAX -> "+resultSet.getInt("Max_Of_salary")+") -> (Count->"+resultSet.getInt("Count")+")");
		employeePayRollDBData.forEach(System.out::println);
		return employeePayRollDBData.size();
	}

	/*
           UC1 :- Ability to create a payroll service database and have java program connect to database
        */
	public static void connectToMysql() {
		try {
			Class.forName(IOService.DRIVER_IO.file);
			System.out.println("Driver loaded");
			getListDriver(); // Driver list
			getConnection();
		} catch (SQLException e) {
			System.out.println("An error occurred while connecting MySQL database");
			e.getMessage();
		} catch (ClassNotFoundException e) {
			System.out.println("cannot find the driver in the class path !");
			e.getMessage();
		}

	}

	/*
	   UC2 :- Ability for Employee Payroll Service to retrieve the Employee Payroll from the Database
	*/
	public static List<EmployeePayRollDBService> readData() {
		String query = "SELECT * FROM employee;";
//		String query = "SELECT * FROM employee_payroll;";

		try {
			List<EmployeePayRollDBService> employeePayRollJDBCs  = getEmployeeListData(query);
		return employeePayRollJDBCs;
		} catch (SQLException e) {
			System.out.println("An error occurred while connecting MySQL database");
			e.printStackTrace();
		}
		return null;
	}

	/*
		UC3 :- Ability to update the salary i.e. the base pay for Employee Terissa to 3000000.00 and sync it with Database
	*/
	public static long updateEmployeePayrollData(String name, Double salary) {
		return updateEmployeePayrollDataUsingStatement(name, salary);
	}

	/*
		UC3 :- Ability to update the salary i.e. the base pay for Employee Terisa to 3000000.00 and sync it with Database
	*/
	private static long updateEmployeePayrollDataUsingStatement(String name, Double salary) {
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
	/*
		UC4 :- Ability to update the salary i.e. the base pay for Employee Terisa to 3000000.00 and sync it with Database using JDBC PreparedStatement
	*/
	public static long updateEmployeePayrollDataUsingPreparedStatement(String name, int salary) {

		String query = "UPDATE employee_payroll SET salary = ? WHERE EmployeeName = ? ";
		Connection connection;
		try {
			connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, salary);
			preparedStatement.setString(2, name);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// Refactored -> UC4
	public static List<EmployeePayRollDBService> queryEmployeePayrollDataUsingPreparedStatement(String name) {
		List<EmployeePayRollDBService> listOfScyedDB = new ArrayList<>();
		final String query = "SELECT * FROM employee_payroll WHERE EmployeeName = ? ";
		Connection connection;
		try {
			connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
				listOfScyedDB.add(new EmployeePayRollDBService(resultSet.getInt("Employee_ID"),
						resultSet.getString("EmployeeName"),resultSet.getDouble("salary")));
			System.out.println(listOfScyedDB.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfScyedDB;
	}

	/*
		UC5 :- Ability to retrieve all employees who have joined in a particular data range from the payroll service database
	*/
	public static List<EmployeePayRollDBService> queryEmployeePayrollDBReturnEmployeeList(LocalDate startDate,LocalDate endDate) throws SQLException {
		String query = String.format("SELECT * FROM employee_payroll WHERE start_date BETWEEN '%s' AND '%s';",Date.valueOf(startDate), Date.valueOf(endDate));
		return getEmployeeListData(query);
	}

	/*
		UC6 :- 	Ability to find sum, average, min, max and number of male and female employees
	*/
	public static int queryEmployeePayrollDBReturnOperation() {
		final String query = "SELECT gender As Gender,SUM(salary) AS Sum_Of_salary, AVG(salary) AS Avg_Of_salary,MIN(salary) AS Min_Of_salary,MAX(salary) AS Max_Of_salary,COUNT(salary) AS Count FROM employee_payroll GROUP BY gender;";
		try {
			return getResultSetEmplpoyeeDB(query);
		} catch (SQLException e) {
			System.out.println("An error occurred while connecting MySQL database");
			e.printStackTrace();
		}
		return 0;
	}

//	@Override // Uncomment to view output
//	public String toString() {
//		return "EmployeePayRollDBService{" +"sum=" + sum +", avg=" + avg +", count=" + count +", max=" + max +", min=" + min +'}';
//	}

	/*
		UC7 :- Ability to add a new Employee to the Payroll
	*/
	public static List<EmployeePayRollDBService> addNewEmployeePayrollToDatabaseService(String name, String phoneNumber, String address, String department, double basicPay, double deductions, double taxablePay, double tax, double netPay, String city, String country, String gender, LocalDate startDate, int salary) {
		final String insert = String.format("INSERT INTO employee_payroll(`EmployeeName`,`PhoneNumber`,`Address`,`Department`,`BasicPay`,`Deductions`,`TaxablePay`,`Tax`,`NetPay`,`City`,`Country`,`gender`,`start_date`,`salary`) VALUES('%s','%s','%s','%s',%e,%e,%e,%e,%e,'%s','%s','%s','%s','%s');",name,phoneNumber,address,department,basicPay,deductions,taxablePay,tax,netPay,city,country,gender,startDate,salary);
		Connection connection ;
		try {
			connection = getConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate(insert);
			List<EmployeePayRollDBService> employeePayRollDBServicesList = readData();
			return employeePayRollDBServicesList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public String toString() {
		return "EmployeePayRollDBService{" +"Employee_ID=" + Employee_ID +", EmployeeName='" + EmployeeName + '\'' +", salary=" + salary +'}';
	}
}
