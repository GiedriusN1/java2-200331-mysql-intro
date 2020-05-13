package lt.bit.java2.services;

import com.sun.jdi.IntegerValue;
import lt.bit.java2.model.Employee;
import lt.bit.java2.model.Salary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    /**
     * Grazinti employee puslapi
     *
     * @param pageNo   puslapio numeris (numeruojame nuo 0)
     * @param pageSize puslapio dydis
     * @return
     **/
    public static List<Employee> loadEmployees(int pageNo, int pageSize) {
        List<Employee> employeeList = new ArrayList<>();
        ResultSet resultSet;

        try (Connection connection = DBService.getConnectionFromCP()) {

            String sql = "SELECT * FROM (SELECT * FROM employees_test" +
                    " LIMIT ?, ?) employees_test" +
                    " LEFT JOIN salaries_test USING (emp_no);";

            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, pageSize * pageNo);
            prepareStatement.setInt(2, pageSize);

            resultSet = prepareStatement.executeQuery();

            int lastEmpNo = 0;
            //           pasiziurim ka gavom resultSet
            while (resultSet.next()) {
                System.out.println(" -> " +
                        resultSet.getInt("emp_no") + " " +
                        resultSet.getString("first_name") + " " +
                        resultSet.getString("last_name") + " " +
                        resultSet.getString("gender") + " " +
                        resultSet.getDate("birth_date") + " " +
                        resultSet.getDate("hire_date") + " " +
                        resultSet.getInt("salary") + " " +
                        resultSet.getDate("to_date") + " " +
                        resultSet.getDate("from_date")
                );

                int currEmpNo = resultSet.getInt("emp_no");
                Employee currentEmployee; // = null;
                Salary salary; // = null;

                currentEmployee = EmployeeMap.fromResultSet(resultSet);

                List<Salary> algeles = new ArrayList<>();
                salary = SalaryMap.fromResultSet(resultSet);
                salary.setSalary(currentEmployee.getEmpNo());
                algeles.add(salary);

                // ziurim ar tai naujas EmpNo
                if (currEmpNo != lastEmpNo) {

                    System.out.println(
                            "Naujo empNo Salary duomenys: " +
                                    salary.getSalary() + " " +
                                    salary.getEmpNo() + " " +
                                    salary.getFromDate() + " " +
                                    salary.getToDate()
                    );

                    currentEmployee.setSalaries(algeles);
                    employeeList.add(currentEmployee);

                } else {
                    /** TODO jei empNo toks pats kaip pries tai buves, pridedam tik salary i employee lista salary.
                     TODO cia reiktu burtazodzio kad  Employee liste  Salaries ideti papildoma salary lista
                     **/
                }
                lastEmpNo = currEmpNo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("EmployeeService.employeeList.size  " + employeeList.size());
        return employeeList;
    }
}
