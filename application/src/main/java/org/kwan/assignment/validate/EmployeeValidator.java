package org.kwan.assignment.validate;

import org.kwan.assignment.user.model.Employee;

import java.util.regex.Pattern;

public class EmployeeValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static void validate(Employee employee) {
        validateEmail(employee.getEmail());
        validateTel(employee.getTel());
    }

    private static void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
    }

    private static void validateTel(String tel) {
        if (tel == null || !tel.matches("\\d{11}")) {
            throw new IllegalArgumentException("Invalid telephone number. It must be 11 digits: " + tel);
        }
    }
}
