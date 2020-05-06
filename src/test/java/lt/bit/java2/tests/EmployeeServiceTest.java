package lt.bit.java2.tests;

import lt.bit.java2.model.Employee;
import lt.bit.java2.services.DBService;
import lt.bit.java2.services.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
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
        // stmt.execute("drop table if exists employees");
        stmt.execute(
                "create table employees (" +
                        " emp_no int," +
                        " first_name varchar(14)," +
                        " last_name varchar(16)," +
                        " gender char(1)," +
                        " birth_date date," +
                        " hire_date date" +
                        ")");
        stmt.execute(
                "insert into employees values" +
                        " (1, 'A1', 'B1', 'F', '2000-01-01', '2018-03-01')," +
                        " (2, 'A2', 'B2', 'M', '2000-01-02', '2018-03-02')," +
                        " (3, 'A3', 'B3', 'F', '2000-01-03', '2018-03-03')," +
                        " (4, 'A4', 'B4', 'M', '2000-01-04', '2018-03-04')," +
                        " (5, 'A5', 'B5', 'F', '2000-01-05', '2018-03-05')," +
                        " (6, 'A6', 'B6', 'M', '2000-01-06', '2018-03-06')"
                        // TODO pabaigti

        );
//        stmt.execute("drop table if exists salaries");
        stmt.execute(
                "create table salaries (" +
                        " emp_no int," +
                        " from_date date," +
                        " to_date date," +
                        " salary int" +
                        ")");
        stmt.execute(
                "insert into salaries values" +
                        " (1, '2018-03-01', '9999-01-01', 1500)," +
                        " (3, '2018-03-03', '2018-04-01', 1000)," +
                        " (3, '2018-04-01', '9999-01-01', 2000)," +
                        " (4, '2018-03-04', '2018-05-01', 1100)," +
                        " (4, '2018-05-01', '2020-02-15', 1200)," +
                        " (4, '2020-02-15', '9999-01-01', 1300)," +
                        " (5, '2018-03-05', '9999-01-01', 1111)"
                        // TODO pabaigti
        );
        connection.commit();
    }

    @AfterEach
    void stop() throws SQLException {
        Connection connection = DBService.getConnectionFromCP();
        Statement stmt = connection.createStatement();
        stmt.execute("drop table if exists employees");
        stmt.execute("drop table if exists salaries");
        connection.commit();
    }

    @Test
    void test() {
        // Page #:         0         1            2
        // Employees:  1-2-3-4-5 6-7-8-9-10 11-12-13-14
        // Salaries:   - - - - - - - - - -  2  3  -  -
        List<Employee> employees = EmployeeService.loadEmployees(2, 5);
        assertNotNull(employees);
        assertEquals(4, employees.size());
        assertNotNull(employees.get(0).getSalaries());
        assertEquals(2, employees.get(0).getSalaries().size());
        assertEquals(3, employees.get(1).getSalaries().size());
    }

    @Test
    void ok() {
        // visada OK :)
    }
}
