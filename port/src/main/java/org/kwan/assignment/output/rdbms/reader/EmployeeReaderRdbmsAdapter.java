package org.kwan.assignment.output.rdbms.reader;

import lombok.val;
import org.kwan.assignment.output.rdbms.entity.EmployeeEntity;
import org.kwan.assignment.output.rdbms.entity.QEmployeeEntity;
import org.kwan.assignment.output.rdbms.repository.EmployeeRepository;
import org.kwan.assignment.user.model.Employee;
import org.kwan.assignment.user.model.GetEmployeeCriteria;
import org.kwan.assignment.user.reader.EmployeeReader;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public class EmployeeReaderRdbmsAdapter extends QuerydslRepositorySupport implements EmployeeReader {

    private final EmployeeRepository employeeRepository;

    public EmployeeReaderRdbmsAdapter(EmployeeRepository employeeRepository) {
        super(EmployeeEntity.class);
        this.employeeRepository = employeeRepository;
    }

    private final QEmployeeEntity employee = QEmployeeEntity.employeeEntity;

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(EmployeeEntity::toDomain).orElse(null);
    }

    @Override
    public List<Employee> getEmployeeByName(String name) {
        return employeeRepository.findByName(name).stream().map(EmployeeEntity::toDomain).toList();
    }

    @Override
    public List<Employee> getEmployees(GetEmployeeCriteria criteria) {
        val query = from(employee);

        if (criteria.getPage() != null) {
            query.offset((long) (criteria.getPage()-1) * criteria.getPageSize());
            query.limit(criteria.getPageSize());
        }

        return query.fetch().stream().map(EmployeeEntity::toDomain).toList();
    }
}
