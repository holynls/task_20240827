package org.kwan.assignment.application.employee;

import org.kwan.assignment.application.employee.usecase.GetEmployeeListUseCase;
import org.kwan.assignment.application.employee.usecase.GetEmployeeUseCase;
import org.kwan.assignment.user.model.Employee;
import org.kwan.assignment.user.model.GetEmployeeCriteria;
import org.kwan.assignment.user.reader.EmployeeReader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetEmployeeApplication implements GetEmployeeListUseCase, GetEmployeeUseCase {

    private final EmployeeReader employeeReader;

    public GetEmployeeApplication(EmployeeReader employeeReader) {
        this.employeeReader = employeeReader;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeReader.getEmployeeById(id);
    }

    @Override
    public List<Employee> getEmployeeByName(String name) {
        return employeeReader.getEmployeeByName(name);
    }

    @Override
    public List<Employee> getEmployeeList(Integer page, Integer pageSize) {
        return employeeReader.getEmployees(
            new GetEmployeeCriteria(page, pageSize)
        );
    }
}
