package org.kwan.assignment.output.rdbms.repository;

import org.kwan.assignment.output.rdbms.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    List<EmployeeEntity> findByName(String name);
}
