package org.kwan.javaspringstarter.user.reader;

import org.kwan.javaspringstarter.user.model.Employee;
import org.kwan.javaspringstarter.user.model.GetEmployeeCriteria;

import java.util.List;

public interface EmployeeReader {
    Employee getEmployeeById(Long id);

    List<Employee> getEmployeeByName(String name);
    List<Employee> getEmployees(GetEmployeeCriteria criteria);
}
