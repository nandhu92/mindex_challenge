package com.mindex.challenge.controller;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Request to create employee profile received  [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Request to get employee details received  for id [{}]", id);

        return employeeService.read(id);
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Request to update employee details received for id [{}] and employee [{}]", id, employee);
        
        employee.setEmployeeId(id);
        
        return employeeService.update(employee);
    }
    /* This endpoint accepts employeeId and returns the fully filled out ReportingStructure for the specified employeeId */
    
    @GetMapping("/employee/reportingStructure/{id}")
    public ReportingStructure createReportingStructure(@PathVariable String id) {
    	LOG.debug("Request to get full ReportingStructure details for id [{}]", id);
    	
    	return employeeService.createReportingStructure(id);
    }
    
}
