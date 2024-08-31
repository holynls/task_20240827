package org.kwan.assignment.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetEmployeeCriteria {
    Integer page;
    Integer pageSize;
}
