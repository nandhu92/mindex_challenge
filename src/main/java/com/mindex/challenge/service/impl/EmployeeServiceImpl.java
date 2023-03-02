package com.mindex.challenge.service.impl;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    ReportingStructure reportingStructure;

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Reading employee with id [{}]", id);

        /* calling findEmployee to find the employee details in the database  */
        Employee employee = findEmployee(id);
        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);
        
        return employeeRepository.save(employee);
    }
    
    @Override
    public ReportingStructure createReportingStructure(String id) {
    	LOG.debug("creating full reporting structure for employee with id [{}]", id);
    	
    	Employee employee = findEmployee(id);
    	
        reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(calculateNumberOfReports(employee));
    	
    	return reportingStructure;
    }
    
    /*This Method finds an employee in the database by the id*/
    @Override
    public Employee findEmployee(String id) {
    	Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        return employee;
    }
    
    /*This method calculates the total number of direct reports of an employee on the fly*/
    public int calculateNumberOfReports(Employee employee) {
		List<Employee> directReportList = employee.getDirectReports();
	
		if (directReportList == null) {
			return 0;
		}
        /* totalReports keeps track of the total number of direct reports */
		int totalReports = directReportList.size();
		for (Employee emp : directReportList) {
			try {
				totalReports += calculateNumberOfReports(findEmployee(emp.getEmployeeId()));
			}
			catch(RuntimeException ex) {
				
			}
		}
		return totalReports;
	}
	
}
