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
	public List<EmployeePayRollDBService> readEmployeePayrollData(IOService databaseIo) {
		if (databaseIo.equals(IOService.DATABASE_IO))
			this.employeePayrollList = new EmployeePayRollDBService().readData();
		return this.employeePayrollList;
	}
	
}
