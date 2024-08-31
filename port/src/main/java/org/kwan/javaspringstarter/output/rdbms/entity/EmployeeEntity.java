package org.kwan.javaspringstarter.output.rdbms.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.kwan.javaspringstarter.user.model.Employee;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "employees")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "tel")
    private String tel;

    @Column(name = "joined_at")
    private LocalDate joinedAt;

    public Employee toDomain() {
        return new Employee(id, name, email, tel, joinedAt);
    }

    public static EmployeeEntity from(Employee employee) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(0L);
        entity.setName(employee.getName());
        entity.setEmail(employee.getEmail());
        entity.setTel(employee.getTel());
        entity.setJoinedAt(employee.getJoinedAt());
        return entity;
    }

    public void update(Employee employee) {
        setName(employee.getName());
        setEmail(employee.getEmail());
        setTel(employee.getTel());
        setJoinedAt(employee.getJoinedAt());
    }
}
