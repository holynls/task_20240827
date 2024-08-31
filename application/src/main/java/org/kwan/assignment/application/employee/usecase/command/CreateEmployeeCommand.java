package org.kwan.assignment.application.employee.usecase.command;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.kwan.assignment.user.model.Employee;
import org.kwan.assignment.validate.SelfValidating;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = false)
public class CreateEmployeeCommand extends SelfValidating {

    @NonNull
    String name;
    @NonNull
    String email;
    @NonNull
    String tel;
    @NonNull
    String joinedAt;

    public CreateEmployeeCommand(String name, String email, String tel, String joinedAt) {
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.joinedAt = joinedAt;
        // validate를 호출하는건 외부에서 알 수 없으므로
        // of, from 등의 static method를 이용하지 않음을 컨벤션으로 맞추는 것이 좋음
        validate();
    }

    public Employee toDomain() {
        return new Employee(
            0L,
            name,
            email,
            tel,
            LocalDate.parse(joinedAt)
        );
    }
}
