package org.kwan.javaspringstarter.application.employee.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.kwan.javaspringstarter.application.employee.usecase.command.CreateEmployeeCommand;
import org.kwan.javaspringstarter.user.model.Employee;

import java.util.List;

public interface CreateEmployeeUseCase {
    List<Employee> createEmployee(List<CreateEmployeeCommand> commands);
    List<Employee> createByCsv(String csv);
    List<Employee> createByJson(String json) throws JsonProcessingException;
}
