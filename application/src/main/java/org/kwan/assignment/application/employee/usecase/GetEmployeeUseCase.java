package org.kwan.assignment.application.employee.usecase;

import org.kwan.assignment.user.model.Employee;

public interface GetEmployeeUseCase {
    Employee getEmployeeById(Long id);
}
