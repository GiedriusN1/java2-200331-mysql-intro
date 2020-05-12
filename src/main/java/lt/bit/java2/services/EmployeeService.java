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
        // resultSet = null;

        // pabandymas pasiimti is employees_test ir salaries_test lenteliu ir ideti i resultSet

        try (Connection connection = DBService.getConnectionFromCP()) {
            // pabandymas pasiimti is employees_test ir salaries_test lenteliu ir ideti i resultSet
            String sql = "SELECT * FROM (SELECT * FROM employees_test" +
                    " LIMIT ?, ?) employees_test" +
                    " LEFT JOIN salaries_test USING (emp_no);";

            //           String sql = "SELECT * FROM employees_test  LIMIT ?, ?;";


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
// isimapinam visa resultSet, gaunam klaida nes 7 eilutes, o norim 4
//       employeeList.add(EmployeeMap.fromResultSet(resultSet));
//                String sal = "SELECT * FROM salaries_test  WHERE (emp_no = resultSet.getInt(\"emp_no\") );";
//                try (PreparedStatement prepareStatement1 = connection.prepareStatement(sal)) {


                int currEmpNo = resultSet.getInt("emp_no");
                Employee currentEmployee; // = null;
                Salary salary; // = null;

                currentEmployee = EmployeeMap.fromResultSet(resultSet);
    //            System.out.println(currentEmployee);
 //               int sal = SalaryMap.fromResultSet(resultSet).getSalary();
                //          salary = SalaryMap.fromResultSet(resultSet).setEmployee(currentEmployee);
 //               System.out.println(sal);
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


                } else {   // jei empNo jau yra liste employeeList, pridedam tik salary i lista salary.
                // cia reiktu i pasikarojancio emp_no Employee  Salaries ideti papildima lista
                    currentEmployee.setSalaries(algeles);

                    currentEmployee.getSalaries().add(salary);
                    salary.setEmployee(currentEmployee);

                    salary.setSalary(currentEmployee.getEmpNo());
           //         employeeList.set(currentEmployee.getEmpNo(), currentEmployee);
           // klaida  Index 11 out of bounds for length 1

                }

                lastEmpNo = currEmpNo;

                //              currentEmployee.setSalaries().add(salary);
                //                 currEmpNo = resultSet.getInt("emp_no");
                //               employeeList.add(currentEmployee);

                //                   salary.setEmployee(currentEmployee);
                //   } else {
                //       salary = SalaryMap.fromResultSet(resultSet);
                //       currentEmployee.getSalaries().add(salary);
                //       salary.setEmployee(currentEmployee);

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


//            } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }

}
