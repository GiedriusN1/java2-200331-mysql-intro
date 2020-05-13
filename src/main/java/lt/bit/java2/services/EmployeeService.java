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
        ResultSet rs;

        try (Connection connection = DBService.getConnectionFromCP()) {

            String sql = "SELECT * FROM employees_test LIMIT ?, ?;";

            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, pageSize * pageNo);
            prepareStatement.setInt(2, pageSize);

            resultSet = prepareStatement.executeQuery();
            int i = 0;
//            int lastEmpNo = 0;
//            //           pasiziurim ka gavom resultSet
            while (resultSet.next()) {
                i++;
                System.out.println(" -> " + i + " ciklas, emp_no  " +
                        resultSet.getInt("emp_no") + " " +
                        resultSet.getString("first_name") + " " +
                        resultSet.getString("last_name") + " " +
                        resultSet.getString("gender") + " " +
                        resultSet.getDate("birth_date") + " " +
                        resultSet.getDate("hire_date")
                );

                // gaunam einamojo employee emp_no pagal kuri darysim selekta is salaries_test
                int currEmpNo = resultSet.getInt("emp_no");

                Employee currentEmployee; // = null;
                currentEmployee = EmployeeMap.fromResultSet(resultSet);
                employeeList.add(currentEmployee);

                Salary salary; // = null;
                String sal = "SELECT * FROM salaries_test WHERE emp_no = ?;";
                PreparedStatement prep = connection.prepareStatement(sal);
                prep.setInt(1, currEmpNo);
                rs = prep.executeQuery();
                List<Salary> algeles = new ArrayList<>();

                while (rs.next()) {

                    System.out.println(" Salary -> " +
                            rs.getInt("emp_no") + " " +
                            rs.getDate("from_date") + " " +
                            rs.getDate("to_date") + " " +
                            rs.getInt("salary")
                    );

                    Salary currentSalary;
                    currentSalary = SalaryMap.fromResultSet(rs);
                    algeles.add(currentSalary);
                }
                currentEmployee.setSalaries(algeles);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }
}
