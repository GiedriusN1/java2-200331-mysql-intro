package lt.bit.java2.services;

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
     */
    public static List<Employee> loadEmployees(int pageNo, int pageSize) {
        List<Employee> employeeList = new ArrayList<>();
        ResultSet resultSet;
        resultSet = null;

        // pabandymas pasiimti is employees_test ir salaries_test lenteliu ir ideti i resultSet

        try (Connection connection = DBService.getConnectionFromCP()) {
            String sql = "SELECT * FROM (SELECT * FROM employees_test" +
                    " LIMIT ?, ?) employees_test" +
                    " LEFT JOIN salaries_test USING (emp_no);";


            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, pageSize  * pageNo);
            prepareStatement.setInt(2, pageSize);

            resultSet = prepareStatement.executeQuery();


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

                employeeList.add(EmployeeMap.fromResultSet(resultSet));
      }


       } catch (SQLException e) {
          e.printStackTrace();
       }


//        try {
//            Employee currentEmployee = null;
//           int currEmpNo = 0;
//            Salary salary = null;
//            do {
//                if (currEmpNo != resultSet.getInt("emp_no")) {
//                    currentEmployee = EmployeeMap.fromResultSet(resultSet);
//                    salary = SalaryMap.fromResultSet(resultSet);
//                currentEmployee.getSalaries().add(salary);
//                   currEmpNo = resultSet.getInt("emp_no");
//                    employeeList.add(currentEmployee);
//                    salary.setEmployee(currentEmployee);
//                } else {
//                    salary = SalaryMap.fromResultSet(resultSet);
//                    currentEmployee.getSalaries().add(salary);
//                    salary.setEmployee(currentEmployee);
//                }
//            }
//            while (resultSet.next());

                // bandau susideti duomenis
//       try {
//
//            while (resultSet.next()) {
//                 employeeList.add(EmployeeMap.fromResultSet(resultSet));
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

        System.out.println("EmployeeService.employeeList.size  " + employeeList.size());

        return employeeList;


                // SELECT * FROM employees  LIMIT 5 OFFSET 10
                // SELECT * FROM employees  LIMIT 10,5

                // SELECT * FROM employees  LIMIT ? OFFSET ?
                // 1? <= 10
                // 2? <= 5


            }

        }


