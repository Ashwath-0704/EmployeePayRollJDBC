package Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import Model.EmployeePayRollDBService;
import Model.IOService;

public class EmplyPayRollMain {

	private List<EmployeePayRollDBService> employeePayrollList;

	/*
	   UC1 :- Ability to create a payroll service database and have java program connect to database
	   UC2 :- Ability for Employee Payroll Service to retrieve the Employee Payroll from the Database
	*/
	public List<EmployeePayRollDBService> readEmployeePayrollData(IOService databaseIo) {
		if (databaseIo.equals(IOService.DATABASE_IO))
			this.employeePayrollList = new EmployeePayRollDBService().readData();
		return this.employeePayrollList;
	}

	/*
		UC3 :- Ability to update the salary i.e. the base pay for Employee Terisa to 3000000.00 and sync itwith Database
	*/
	public long updateEmployeePayrollData(String name, Double salary) {
		new EmployeePayRollDBService();
		long result = EmployeePayRollDBService.updateEmployeePayrollData(name, salary);
		if (result == 0)return result;
		return result;
	}
	/*
		UC4 :- Ability to update the salary i.e. the base pay for Employee Terisa to 3000000.00 and sync it with Database using JDBC PreparedStatement
	*/
	public long updateEmployeePayrollDataUsingPreparedStatement(String name, double salary) {
		new EmployeePayRollDBService();
		long result = EmployeePayRollDBService.updateEmployeePayrollDataUsingPreparedStatement(name, (int) salary);
		return result;
	}

	// Refactored -> UC4
	public List<EmployeePayRollDBService> queryEmployeePayrollDataUsingPreparedStatement(String name) {
		new EmployeePayRollDBService();
		this.employeePayrollList = EmployeePayRollDBService.queryEmployeePayrollDataUsingPreparedStatement(name);
		return this.employeePayrollList;
	}
	/*
		UC5 :- Ability to retrieve all employees who have joined in a particular data range from the payroll service database
	*/
	public List<EmployeePayRollDBService> queryEmployeePayrollDBReturnEmployeeList(LocalDate startDate, LocalDate endDate) throws SQLException {
		this.employeePayrollList = EmployeePayRollDBService.queryEmployeePayrollDBReturnEmployeeList(startDate,endDate);
		return this.employeePayrollList;
	}
	/*
		UC6 :- 	Ability to find sum, average, min, max and number of male and female employees
	*/
	public int queryToFindNumberOfOperationSUM_AVG_MIN_MAX_COUNT() {
		return EmployeePayRollDBService.queryEmployeePayrollDBReturnOperation();
	}

}
