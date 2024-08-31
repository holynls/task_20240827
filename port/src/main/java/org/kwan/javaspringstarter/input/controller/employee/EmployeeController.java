package org.kwan.javaspringstarter.input.controller.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.kwan.javaspringstarter.application.employee.GetEmployeeApplication;
import org.kwan.javaspringstarter.application.employee.ManageEmployeeApplication;
import org.kwan.javaspringstarter.application.employee.usecase.command.CreateEmployeeCommand;
import org.kwan.javaspringstarter.input.controller.employee.model.CreateEmployeeApiRequest;
import org.kwan.javaspringstarter.input.controller.employee.model.EmployeeApiResponse;
import org.kwan.javaspringstarter.user.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class EmployeeController {

    private final GetEmployeeApplication getEmployeeApplication;
    private final ManageEmployeeApplication manageEmployeeApplication;
    private final ObjectMapper objectMapper;

    public EmployeeController(
        GetEmployeeApplication getEmployeeApplication,
        ManageEmployeeApplication manageEmployeeApplication,
        ObjectMapper objectMapper
    ) {
        this.getEmployeeApplication = getEmployeeApplication;
        this.manageEmployeeApplication = manageEmployeeApplication;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeApiResponse>> getEmployees(
        @RequestParam Integer page,
        @RequestParam Integer pageSize
    ) {
        List<Employee> employees = getEmployeeApplication.getEmployeeList(page, pageSize);

        return ResponseEntity.ok(employees.stream().map(EmployeeApiResponse::from).toList());
    }

    @GetMapping("/employee/name/{name}")
    public ResponseEntity<List<EmployeeApiResponse>> getEmployeeByName(@PathVariable String name) {
        List<Employee> employee = getEmployeeApplication.getEmployeeByName(name);

        return ResponseEntity.ok(employee.stream().map(EmployeeApiResponse::from).toList());
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeApiResponse> getEmployeeById(@PathVariable Long id) {
        Employee employee = getEmployeeApplication.getEmployeeById(id);

        return ResponseEntity.ok(EmployeeApiResponse.from(employee));
    }

    @PostMapping("/employee")
    public ResponseEntity<Void> createEmployee(
        @RequestBody(required = false) String text,
        @RequestBody(required = false) MultipartFile file
    ) {
        try {
            if (text != null) {
                if (text.startsWith("{")) {
                    manageEmployeeApplication.createByJson(text);
                } else {
                    manageEmployeeApplication.createByCsv(text);
                }
            }

            if (file != null) {
                if (file.getOriginalFilename().endsWith(".json")) {
                    manageEmployeeApplication.createByJson(new String(file.getBytes()));
                } else if (file.getOriginalFilename().startsWith(".csv")) {
                    manageEmployeeApplication.createByCsv(new String(file.getBytes()));
                } else {
                    throw new IllegalArgumentException("Invalid file format");
                }
            }
        } catch (Exception e) {
            log.error("Failed to read file", e);
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/employees/file/csv")
    public ResponseEntity<List<EmployeeApiResponse>> registerEmployeesByCsvFile(
        @RequestParam("file") MultipartFile file
    ) {
        List<CreateEmployeeCommand> commands = List.of();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            parseCsv(br.lines().toList().toString(), commands);
        } catch (Exception e) {
            log.error("Failed to read CSV file", e);
            return ResponseEntity.badRequest().build();
        }

        manageEmployeeApplication.createEmployee(commands);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/employees/file/json")
    public ResponseEntity<List<EmployeeApiResponse>> registerEmployeesByJsonFile(
        @RequestParam("file") MultipartFile file
    ) {
        List<CreateEmployeeCommand> commands;
        try {
            commands = objectMapper.readValue(file.getInputStream(), new TypeReference<>() {});
        } catch (Exception e) {
            log.error("Failed to read JSON file", e);
            return ResponseEntity.badRequest().build();
        }

        manageEmployeeApplication.createEmployee(commands);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/employees/body/csv")
    public ResponseEntity<List<EmployeeApiResponse>> registerEmployeesByCsvBody(
        @RequestBody String csvBody
    ) {
        List<CreateEmployeeCommand> commands = new ArrayList<>();

        try {
            parseCsv(csvBody, commands);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        manageEmployeeApplication.createEmployee(commands);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/employees/body/json")
    public ResponseEntity<List<EmployeeApiResponse>> registerEmployeesByJsonBody(
        @RequestBody List<CreateEmployeeApiRequest> createEmployeeApiRequests
    ) {
        List<CreateEmployeeCommand> commands = new ArrayList<>();

        createEmployeeApiRequests.forEach(request -> {
            CreateEmployeeCommand command = new CreateEmployeeCommand(
                request.getName(),
                request.getEmail(),
                request.getTel(),
                request.getJoinedAt()
            );
            commands.add(command);
        });

        manageEmployeeApplication.createEmployee(commands);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void parseCsv(String csvBody, List<CreateEmployeeCommand> commands) {
        String[] lines = csvBody.split("\n");
        for (String line : lines) {
            parseCsvLine(line, commands);
        }
    }

    private static void parseCsvLine(String line, List<CreateEmployeeCommand> commands) {
        String[] data = line.split(",");
        CreateEmployeeCommand command = new CreateEmployeeCommand(
            data[0],
            data[1],
            data[2],
            data[3]
        );
        commands.add(command);
    }
}
