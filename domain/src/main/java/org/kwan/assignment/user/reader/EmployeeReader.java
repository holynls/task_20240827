package org.kwan.assignment.user.reader;

import org.kwan.assignment.user.model.Employee;
import org.kwan.assignment.user.model.GetEmployeeCriteria;

import java.util.List;

public interface EmployeeReader {
    Employee getEmployeeById(Long id);

    List<Employee> getEmployeeByName(String name);
    List<Employee> getEmployees(GetEmployeeCriteria criteria);
}
