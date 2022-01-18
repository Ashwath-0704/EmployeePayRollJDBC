package EmplyPayRoll.Test;

import Model.EmployeePayRollDBService;
import Model.IOService;
import Service.EmplyPayRollMain;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


public class emplyPayRollTest {

	@Test // UC2
	public void givenEmployeePayrollInDB_whenRetrived_shouldMatchEmployeeCount() {
		EmplyPayRollMain emplyPayRollMain = new EmplyPayRollMain();
		List<EmployeePayRollDBService> employeePayRollJDBCs = emplyPayRollMain
				.readEmployeePayrollData(IOService.DATABASE_IO);
		Assert.assertEquals(8, employeePayRollJDBCs.size());
	}

	@Test // UC3
	public void givenNewSalaryForEmployee_whenUpdated_shouldMatchSyncWithDBStatemnt() {
		EmplyPayRollMain emplyPayRollMain = new EmplyPayRollMain();
		long updatedRowsInDB = emplyPayRollMain.updateEmployeePayrollData("Terissa", 80000.00);
		Assert.assertEquals(2, updatedRowsInDB);
	}

	@Test // UC4
	public void givenNewSalaryForEmployee_whenUpdated_shouldMatchSyncWithDBPreparedStatemnt() {
		EmplyPayRollMain emplyPayRollMain = new EmplyPayRollMain();
		long updatedRowsInDB = emplyPayRollMain.updateEmployeePayrollDataUsingPreparedStatemnt("Ashwath_Naidu",
				50000.00);
		Assert.assertEquals(1, updatedRowsInDB);
	}

	@Test // Refactored -> UC4
	public void givenEmployeeName_shouldMatchSyncWithDBPreparedStatemnt() {
		EmplyPayRollMain emplyPayRollMain = new EmplyPayRollMain();
		List<EmployeePayRollDBService> updatedRowsInDB = emplyPayRollMain
				.queryEmployeePayrollDataUsingPreparedStatemnt("Ashwath_Naidu");
		Assert.assertEquals(1, updatedRowsInDB.size());
	}

	@Test // UC5
	public void givenDateRange_shouldMatchSyncWithDB_ReturnCountOfEmployee() throws SQLException {
		EmplyPayRollMain emplyPayRollMain = new EmplyPayRollMain();
		LocalDate startDate =LocalDate.of(2021,8,10);
		LocalDate endDate =LocalDate.of(2021,10,30);
		List<EmployeePayRollDBService> updatedRowsInDB = emplyPayRollMain.queryEmployeePayrollDBReturnEmployeeList(startDate,endDate);
		Assert.assertEquals(5, updatedRowsInDB.size());
	}

	@Test // UC6
	public void givenEmployeesDB_shouldFindOperation_ReturnOperationData() throws SQLException {
		EmplyPayRollMain emplyPayRollMain = new EmplyPayRollMain();
		List<EmployeePayRollDBService> operationDataList = emplyPayRollMain.queryToFindNumberOfOperationSUM_AVG_MIN_MAX_COUNT();
		Assert.assertEquals(2, operationDataList.size());
	}
}
