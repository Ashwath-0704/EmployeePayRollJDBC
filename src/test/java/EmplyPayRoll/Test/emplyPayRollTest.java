package EmplyPayRoll.Test;

import Model.EmployeePayRollDBService;
import Model.IOService;
import Service.EmplyPayRollMain;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
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
}
