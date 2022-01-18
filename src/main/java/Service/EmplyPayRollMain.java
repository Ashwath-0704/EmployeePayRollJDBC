package Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Model.EmployeePayRollDBService;
import Model.IOService;

public class EmplyPayRollMain {

	private List<EmployeePayRollDBService> employeePayrollList;

	// UC1 Reading the data from MySql
	// UC2 Reading the data from MySql after updated
	public List<EmployeePayRollDBService> readEmployeePayrollData(IOService databaseIo) {
		if (databaseIo.equals(IOService.DATABASE_IO))
			this.employeePayrollList = new EmployeePayRollDBService().readData();
		return this.employeePayrollList;
	}


	// UC3
	public long updateEmployeePayrollData(String name, Double salary) {
		new EmployeePayRollDBService();
		long result = EmployeePayRollDBService.updateEmployeePayrollData(name, salary);
		if (result == 0)return result;
		return result;
	}

	// UC4
	public long updateEmployeePayrollDataUsingPreparedStatemnt(String name, double salary) {
		new EmployeePayRollDBService();
		long result = EmployeePayRollDBService.updateEmployeePayrollDataUsingPreparedStatemnt(name, (int) salary);
		return result;
	}

	// Refactored -> UC4
	public List<EmployeePayRollDBService> queryEmployeePayrollDataUsingPreparedStatemnt(String name) {
		new EmployeePayRollDBService();
		this.employeePayrollList = EmployeePayRollDBService.queryEmployeePayrollDataUsingPreparedStatemnt(name);
		return this.employeePayrollList;
	}

	// UC5
	public List<EmployeePayRollDBService> queryEmployeePayrollDBReturnEmployeeList(LocalDate startDate, LocalDate endDate) throws SQLException {
		this.employeePayrollList = EmployeePayRollDBService.queryEmployeePayrollDBReturnEmployeeList(startDate,endDate);
		return this.employeePayrollList;
	}

}
