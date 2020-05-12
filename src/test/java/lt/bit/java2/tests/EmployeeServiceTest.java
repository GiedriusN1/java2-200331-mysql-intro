package lt.bit.java2.tests;

import lt.bit.java2.model.Employee;
import lt.bit.java2.services.DBService;
import lt.bit.java2.services.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmployeeServiceTest {

    @BeforeEach
    void start() throws SQLException {
        // sukurti 14 employee irasus
        // is kuriu 11'as turi tureti 2 salary irasus, o 12'as turi tureti 3
        Connection connection = DBService.getConnectionFromCP();
        Statement stmt = connection.createStatement();
        stmt.execute("drop table if exists employees_test");
        stmt.execute(
                "create table employees_test (" +
                        " emp_no int," +
                        " first_name varchar(14)," +
                        " last_name varchar(16)," +
                        " gender char(1)," +
                        " birth_date date," +
                        " hire_date date" +
                        ")");
        stmt.execute(
                "insert into employees_test values" +
                        " (1, 'A1', 'B1', 'F', '2000-01-01', '2018-03-01')," +
                        " (2, 'A2', 'B2', 'M', '2000-01-02', '2018-03-02')," +
                        " (3, 'A3', 'B3', 'F', '2000-01-03', '2018-03-03')," +
                        " (4, 'A4', 'B4', 'M', '2000-01-04', '2018-03-04')," +
                        " (5, 'A5', 'B5', 'F', '2000-01-05', '2018-03-05')," +
                        " (6, 'A6', 'B6', 'M', '2000-01-06', '2018-03-06')," +
                        " (7, 'A7', 'B7', 'F', '2000-01-07', '2018-03-07')," +
                        " (8, 'A8', 'B8', 'F', '2000-01-08', '2018-03-08')," +
                        " (9, 'A9', 'B9', 'F', '2000-01-09', '2018-03-09')," +
                        " (10, 'A10', 'B10', 'F', '2000-01-10', '2018-03-10')," +
                        " (11, 'A11', 'B11', 'F', '2000-01-11', '2018-03-11')," +
                        " (12, 'A12', 'B12', 'F', '2000-01-12', '2018-03-12')," +
                        " (13, 'A13', 'B13', 'M', '2000-01-13', '2018-03-13')," +
                        " (14, 'A14', 'B14', 'F', '2000-01-14', '2018-03-14')"

        );


        stmt.execute("drop table if exists salaries_test");
        stmt.execute(
                "create table salaries_test (" +
                        " emp_no int," +
                        " from_date date," +
                        " to_date date," +
                        " salary int" +
                        ")");
        stmt.execute(
                "insert into salaries_test values" +
                        " (1, '2018-03-01', '9999-01-01', 1500)," +
                        " (3, '2018-03-03', '2018-04-01', 1000)," +
                        " (3, '2018-04-01', '9999-01-01', 2000)," +
                        " (4, '2018-03-04', '2018-05-01', 1100)," +
                        " (4, '2018-05-01', '2020-02-15', 1200)," +
                        " (4, '2020-02-15', '9999-01-01', 1300)," +
                        " (5, '2018-03-05', '9999-01-01', 1111)," +
                        " (5, '2018-04-05', '9999-01-01', 1111)," +
                        " (6, '2020-02-15', '9999-01-01', 1300)," +
                        " (7, '2020-02-15', '9999-01-01', 1300)," +
                        " (8, '2020-02-15', '9999-01-01', 1300)," +
                        " (9, '2020-02-15', '9999-01-01', 1300)," +
                        " (9, '2020-02-15', '9999-01-01', 1300)," +
                        " (10, '2020-02-15', '9999-01-01', 1300)," +
                        " (10, '2018-03-05', '9999-01-01', 1111)," +
                        " (10, '2020-02-15', '9999-01-01', 1300)," +
                        " (11, '2020-02-15', '9999-01-01', 1300)," +
                        " (11, '2020-02-15', '9999-01-01', 1300)," +
                        " (12, '2020-01-15', '9999-01-01', 1300)," +
                        " (12, '2020-03-15', '9999-01-01', 1300)," +
                        " (12, '2020-02-15', '9999-01-01', 1300)," +
                        " (14, '2020-02-01', '9999-01-01', 999)"

        );
      //  connection.commit();


//     ResultSet rs1 = stmt.executeQuery("SELECT COUNT(*) FROM employees_test");
//     rs1.next();
//     System.out.println("Total count of rows in employees_test: " + rs1.getInt(1));
//
//     ResultSet rs2 = stmt.executeQuery("SELECT COUNT(*) FROM salaries_test");
//     rs2.next();
//     System.out.println("Total count of rows in salaries_test: " + rs2.getInt(1));
    }

    @AfterEach
    void stop() throws SQLException {
       // Connection connection = DBService.getConnectionFromCP();
       // Statement stmt = connection.createStatement();
       // stmt.execute("drop table if exists employees");
       // stmt.execute("drop table if exists salaries");
       // connection.commit();
    }

    @Test
    void test() {
        // Page #:         0          1            2
        // Employees:  1-2-3-4-5  6-7-8-9-10  11-12-13-14
        // Salaries:   - - - - -  - - - - -    2  3  -  -
        List<Employee> employees = EmployeeService.loadEmployees(2, 5);
        assertNotNull(employees);
        assertEquals(4, employees.size());
       // assertNotNull(employees.get(0).getSalaries());
      //  assertEquals(2, employees.get(0).getSalaries().size());
       // assertEquals(3, employees.get(1).getSalaries().size());
    }

    @Test
    void ok() {
        // visada OK :)
    }
}
