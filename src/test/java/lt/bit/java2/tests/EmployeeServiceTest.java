package lt.bit.java2.tests;

import lt.bit.java2.model.Employee;
import lt.bit.java2.services.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmployeeServiceTest {

    @BeforeEach
    void start() {
        // sukurti 6 employee irasus
        // is kuriu 3'ias turi tureti 2 salary irasus, o 4'as turi 3
    }

    @AfterEach
    void stop() {

    }

    // tikuosi kad is viso yra 6 employees irasai
    // ir 3'ias employee irasas turi 2 salary irasus
//    @Test
//    void test() {
//        List<Employee> employees = EmployeeService.loadEmployees(2, 5);
//        assertNotNull(employees);
//        assertEquals(4, employees.size());
//        assertNotNull(employees.get(0).getSalaries());
//        assertEquals(2, employees.get(0).getSalaries().size());
//        assertEquals(3, employees.get(1).getSalaries().size());
//    }
}
