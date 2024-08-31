package org.kwan.javaspringstarter.output.rdbms.repository;

import org.kwan.javaspringstarter.output.rdbms.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    List<EmployeeEntity> findByName(String name);
}
