package org.kwan.javaspringstarter.application.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.kwan.javaspringstarter.application.employee.usecase.CreateEmployeeUseCase;
import org.kwan.javaspringstarter.application.employee.usecase.command.CreateEmployeeCommand;
import org.kwan.javaspringstarter.user.model.Employee;
import org.kwan.javaspringstarter.user.writer.EmployeeWriter;
import org.kwan.javaspringstarter.validate.EmployeeValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ManageEmployeeApplication implements CreateEmployeeUseCase {

    private final EmployeeWriter employeeWriter;

    private final ObjectMapper objectMapper;


    public ManageEmployeeApplication(EmployeeWriter employeeWriter, ObjectMapper objectMapper) {
        this.employeeWriter = employeeWriter;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = false)
    @Override
    public List<Employee> createEmployee(List<CreateEmployeeCommand> commands) {
        List<Employee> employeesToBeSave = commands.stream()
                .map(CreateEmployeeCommand::toDomain)
                .toList();

        employeesToBeSave.forEach(EmployeeValidator::validate);

        List<Employee> employees = employeeWriter.createEmployees(employeesToBeSave);

        log.debug("Created employees: {}", employees);

        return employees;
    }

    @Override
    public List<Employee> createByCsv(String csv) {
        try {
            List<Employee> employees = parseCsvLine(csv);

            employees.forEach(EmployeeValidator::validate);

            return employeeWriter.createEmployees(employees);
        } catch (Exception e) {
            log.error("Failed to parse CSV, csv: {}", csv, e);
            throw new IllegalArgumentException("Failed to parse CSV");
        }
    }

    @Override
    public List<Employee> createByJson(String json) {
        try {
            List<Employee> employees = objectMapper.readValue(
                json,
                new TypeReference<>() {}
            );

            employees.forEach(EmployeeValidator::validate);

            return employeeWriter.createEmployees(employees);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse JSON, json: {}", json, e);
            throw new IllegalArgumentException("Failed to parse JSON");
        }
    }

    private List<Employee> parseCsvLine(String csvBody) {
        List<Employee> employees = new ArrayList<>();

        String[] lines = csvBody.split("\n");

        for (String line : lines) {
            String[] data = line.split(",");
            // CSV columns are in the order: name, email, tel, joinedAt
            Employee employee = new Employee(0L, data[0], data[1], data[2], LocalDate.parse(data[3]));

            employees.add(employee);
        }

        return employees;
    }
}
