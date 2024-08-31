package org.kwan.javaspringstarter.application.employee.usecase;

import org.kwan.javaspringstarter.user.model.Employee;

public interface GetEmployeeUseCase {
    Employee getEmployeeById(Long id);
}
