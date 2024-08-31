package org.kwan.assignment.input.controller.employee.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.kwan.assignment.application.employee.usecase.command.CreateEmployeeCommand;

@Data
@Accessors(chain = true)
public class CreateEmployeeApiRequest {
    private String name;
    private String email;
    private String tel;
    private String joinedAt;

    public CreateEmployeeCommand toCommand() {
        return new CreateEmployeeCommand(
            name,
            email,
            tel,
            joinedAt
        );
    }
}
