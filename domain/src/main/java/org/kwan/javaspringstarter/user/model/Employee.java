package org.kwan.javaspringstarter.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Employee {
    private Long id;
    private String name;
    private String email;
    private String tel;
    private LocalDate joinedAt;
}
