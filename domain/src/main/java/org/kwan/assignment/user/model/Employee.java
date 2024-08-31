package org.kwan.assignment.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Value
@Accessors(chain = true)
public class Employee {
    Long id;
    String name;
    String email;
    String tel;
    LocalDate joinedAt;

    @JsonCreator
    public Employee(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("tel") String tel,
        @JsonProperty("joinedAt") LocalDate joinedAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.joinedAt = joinedAt;
    }
}
