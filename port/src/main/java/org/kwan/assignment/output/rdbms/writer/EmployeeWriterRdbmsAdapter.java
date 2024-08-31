package org.kwan.assignment.output.rdbms.writer;

import org.kwan.assignment.output.rdbms.entity.EmployeeEntity;
import org.kwan.assignment.output.rdbms.repository.EmployeeRepository;
import org.kwan.assignment.user.model.Employee;
import org.kwan.assignment.user.writer.EmployeeWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeWriterRdbmsAdapter implements EmployeeWriter {

    private final EmployeeRepository employeeRepository;

    public EmployeeWriterRdbmsAdapter(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee crateEmployee(Employee employee) {
        EmployeeEntity employeeEntity = EmployeeEntity.from(employee);

        return employeeRepository.save(employeeEntity).toDomain();
    }

    @Override
    public List<Employee> createEmployees(List<Employee> employees) {
        List<EmployeeEntity> employeeEntityList = employees.stream().map(EmployeeEntity::from).toList();

        return employeeRepository.saveAll(employeeEntityList).stream().map(EmployeeEntity::toDomain).toList();
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        EmployeeEntity employeeEntity = employeeRepository.findById(employee.getId()).orElseThrow();

        employeeEntity.update(employee);

        return employeeRepository.save(employeeEntity).toDomain();
    }
}
