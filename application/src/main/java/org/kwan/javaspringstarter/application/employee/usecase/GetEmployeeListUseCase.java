package org.kwan.javaspringstarter.application.employee.usecase;

import org.kwan.javaspringstarter.user.model.Employee;

import java.util.List;

public interface GetEmployeeListUseCase {
    List<Employee> getEmployeeList(Integer page, Integer pageSize);

    List<Employee> getEmployeeByName(String name);
}
