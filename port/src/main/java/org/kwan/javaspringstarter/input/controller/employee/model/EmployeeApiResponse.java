package org.kwan.javaspringstarter.input.controller.employee.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.kwan.javaspringstarter.user.model.Employee;


@Data
@Accessors(chain = true)
public class EmployeeApiResponse {
    private String name;
    private String email;
    private String tel;
    private String joinedAt;

    public static EmployeeApiResponse from(Employee employee) {
        return new EmployeeApiResponse()
                .setName(employee.getName())
                .setEmail(employee.getEmail())
                .setTel(employee.getTel())
                .setJoinedAt(employee.getJoinedAt().toString());
    }

}
