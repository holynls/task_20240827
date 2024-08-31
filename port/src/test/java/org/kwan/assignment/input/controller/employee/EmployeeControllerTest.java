package org.kwan.assignment.input.controller.employee;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAndGetEmployeeWithJson() throws Exception {
        // Given
        String json = """
            [
              {
                "name": "John Doe",
                "email": "test@test.com",
                "tel": "12345678923",
                "joinedAt": "2021-01-01"
              },
              {
                "name": "Jane Doe",
                "email": "test2@test.com",
                "tel": "12345678911",
                "joinedAt": "2021-01-02"
              }
            ]    
              """;

        mockMvc.perform(post("/api/employee")
            .contentType("application/json")
            .content(json)
        ).andExpect(status().isCreated());

        mockMvc.perform(get("/api/employees?page=1&pageSize=10"))
            .andExpect(status().isOk())
            .andExpect(content().json("""
                [
                  {
                    "name": "John Doe",
                    "email": "test@test.com",
                    "tel": "12345678923",
                    "joinedAt": "2021-01-01"
                  },
                  {
                    "name": "Jane Doe",
                    "email": "test2@test.com",
                    "tel": "12345678911",
                    "joinedAt": "2021-01-02"
                  }
                ]
             """));

    }

    @Test
    void createAndGetEmployeeWithCsv() throws Exception {
        // Given
        String csv = """
            김철수,test@test.com,01038423841,2021-01-01
            홍길동,hong@test.com,01038423843,2021-01-02
              """;

        mockMvc.perform(post("/api/employee")
            .contentType("application/json")
            .content(csv)
        ).andExpect(status().isCreated());

        mockMvc.perform(get("/api/employees?page=1&pageSize=10"))
            .andExpect(status().isOk())
            .andExpect(content().json("""
                [
                  {
                    "name": "김철수",
                    "email": "test@test.com",
                    "tel": "01038423841",
                    "joinedAt": "2021-01-01"
                  },
                  {
                    "name": "홍길동",
                    "email": "hong@test.com",
                    "tel": "01038423843",
                    "joinedAt": "2021-01-02"
                  }
                ]
             """));
    }

    @Test
    void createAndGetEmployeeWithCsvFile() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "employee.csv",
            "text/csv",
            """
            김철수,test@test.com,01038423841,2021-01-01
            홍길동,hong@test.com,01038423843,2021-01-02
            """.getBytes());

        mockMvc.perform(multipart("/api/employee")
            .file(file)
        ).andExpect(status().isCreated());

        mockMvc.perform(get("/api/employees?page=1&pageSize=10"))
            .andExpect(status().isOk())
            .andExpect(content().json("""
                [
                  {
                    "name": "김철수",
                    "email": "test@test.com",
                    "tel": "01038423841",
                    "joinedAt": "2021-01-01"
                  },
                  {
                    "name": "홍길동",
                    "email": "hong@test.com",
                    "tel": "01038423843",
                    "joinedAt": "2021-01-02"
                  }
                ]
             """));
    }

    @Test
    void createAndGetEmployeeWithJsonFile() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "employee.csv",
            "text/csv",
            """
            [
              {
                "name": "John Doe",
                "email": "test@test.com",
                "tel": "12345678923",
                "joinedAt": "2021-01-01"
              },
              {
                "name": "Jane Doe",
                "email": "test2@test.com",
                "tel": "12345678911",
                "joinedAt": "2021-01-02"
              }
            ]    
            """.getBytes());

        mockMvc.perform(multipart("/api/employee")
            .file(file)
        ).andExpect(status().isCreated());

        mockMvc.perform(get("/api/employees?page=1&pageSize=10"))
            .andExpect(status().isOk())
            .andExpect(content().json("""
                [
                  {
                    "name": "John Doe",
                    "email": "test@test.com",
                    "tel": "12345678923",
                    "joinedAt": "2021-01-01"
                  },
                  {
                    "name": "Jane Doe",
                    "email": "test2@test.com",
                    "tel": "12345678911",
                    "joinedAt": "2021-01-02"
                  }
                ]
             """));
    }

    @Nested
    class CreateEmployeeFailed {
        @Test
        void wrongFileType() throws Exception {
            MockMultipartFile file = new MockMultipartFile(
                "file",
                "employee.txt",
                "text/text",
                """
                [
                  {
                    "name": "John Doe",
                    "email": "test@test.com",
                    "tel": "12345678923",
                    "joinedAt": "2021-01-01"
                  },
                  {
                    "name": "Jane Doe",
                    "email": "test2@test.com",
                    "tel": "12345678911",
                    "joinedAt": "2021-01-02"
                  }
                ]    
                """.getBytes());

            mockMvc.perform(multipart("/api/employee")
                .file(file)
            ).andExpect(status().isBadRequest());
        }

        @Test
        void wrongMailAddress() throws Exception {
            String csv = """
            김철수,testtest.com,01038423841,2021-01-01
            홍길동,hong@test.com,01038423843,2021-01-02
              """;

            mockMvc.perform(post("/api/employee")
                .contentType("application/json")
                .content(csv)
            ).andExpect(status().isBadRequest());
        }

        @Test
        void wrongTel() throws Exception {
            String csv = """
            김철수,test@test.com,01038423841,2021-01-01
            홍길동,hong@test.com,0103842,2021-01-02
              """;

            mockMvc.perform(post("/api/employee")
                .contentType("application/json")
                .content(csv)
            ).andExpect(status().isBadRequest());
        }
    }
}