package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;

@Service
public class CompensationServiceImpl implements CompensationService {
	
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private CompensationRepository compensationRepository;
	
	
	@Autowired
	private EmployeeService employeeService;
	
	@Override
    /* creates compensation for employees */
    public Compensation create(Compensation compensation) {
    	LOG.debug("Creating compensation [{}]", compensation);
    	
    	compensationRepository.insert(compensation);

    /* The following code can be used in case new employee id needs to be created instead of adding compensation
    to current employee details

        Employee employee = employeeService.create(compensation.getEmployee());
    	compensation.setEmployee(employee);

     */
    	return compensation;
    }
    
    @Override

    /* finds employee by id to get compensation details */
    public Compensation read(String id) {
    	LOG.debug("Reads compensation for employee with id [{}]", id);
    	
    	Employee employee = employeeService.findEmployee(id);
		Compensation compensation = compensationRepository.findByEmployee(employee);
        if (compensation == null) {
            throw new RuntimeException("compensation data unavailable for employeeId: " + employee.getEmployeeId());
        }
        return compensation;
    
    }
	
    
}
