package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmpService {

    Connection connection;

    public List<Employee> getAllEmpInfo () {
        List<Employee> employeeList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "select empno,\n" +
                            "       ename,\n" +
                            "       job,\n" +
                            "       nvl(mgr, 0) mgr,\n" +
                            "       hiredate,\n" +
                            "       dname,\n" +
                            "       sal,\n" +
                            "       nvl(comm, 0) comm,\n" +
                            "       grade\n" +
                            "from emp left join dept d on emp.deptno = d.deptno left join salgrade on emp.sal between salgrade.minsal and salgrade.hisal");
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getString("empno"));
                employee.setName((resultSet.getString("ename")));
                employee.setCommission(resultSet.getString("comm"));
                employee.setDepartment(resultSet.getString("dname"));
                employee.setGrade(resultSet.getString("grade"));
                employee.setHireDate(resultSet.getString("hiredate"));
                employee.setJob(resultSet.getString("job"));
                employee.setManagerId(resultSet.getString("mgr"));
                employee.setSalary(resultSet.getString("sal"));
                employeeList.add(employee);
            }
            resultSet.close();
            statement.close();
            return employeeList;
        } catch (SQLException e) {
            e.printStackTrace();
            return employeeList;
        }
    }

    public Employee getEmpInfo (int id) {
        Employee employee = new Employee();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "select empno,\n" +
                            "       ename,\n" +
                            "       job,\n" +
                            "       nvl(mgr, 0) mgr,\n" +
                            "       hiredate,\n" +
                            "       dname,\n" +
                            "       sal,\n" +
                            "       nvl(comm, 0) comm,\n" +
                            "       grade\n" +
                            "from emp left join dept d on emp.deptno = d.deptno left join salgrade on emp.sal between salgrade.minsal and salgrade.hisal\n" +
                            "where empno = " + id);
            resultSet.next();
            employee.setId(resultSet.getString("empno"));
            employee.setName((resultSet.getString("ename")));
            employee.setCommission(resultSet.getString("comm"));
            employee.setDepartment(resultSet.getString("dname"));
            employee.setGrade(resultSet.getString("grade"));
            employee.setHireDate(resultSet.getString("hiredate"));
            employee.setJob(resultSet.getString("job"));
            employee.setManagerId(resultSet.getString("mgr"));
            employee.setSalary(resultSet.getString("sal"));
            resultSet.close();
            statement.close();
            return employee;
        } catch (SQLException e) {
            e.printStackTrace();
            return employee;
        }
    }

    public boolean deleteEmployee (int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from emp where emp.empno = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addEmployee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        System.out.println("Enter id:");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter job:");
        String job = scanner.nextLine();
        System.out.println("Enter manager id:");
        int mgr = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter salary:");
        float salary = Float.parseFloat(scanner.nextLine());
        System.out.println("Enter commissions:");
        float comm = Float.parseFloat(scanner.nextLine());
        System.out.println("Enter hire date in format 'YYYY/MM/DD'");
        String hireDate = scanner.nextLine();
        System.out.println("Enter department id:");
        int depno = Integer.parseInt(scanner.nextLine());

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO emp VALUES (?,?,?,?,/*TO_DATE(*/?,/*'YYYY/MM/DD'),*/?,?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, job);
            if (mgr == 0) {
                preparedStatement.setNull(4, Types.VARCHAR);
            } else {
                preparedStatement.setInt(4, mgr);
            }
            preparedStatement.setDate(5, Date.valueOf(hireDate));
            Date date = Date.valueOf(hireDate);
            preparedStatement.setDate(5, date);
            preparedStatement.setFloat(6, salary);
            if (comm == 0) {
                preparedStatement.setNull(7, Types.FLOAT);
            } else {
                preparedStatement.setFloat(7,comm);
            }
            preparedStatement.setInt(8,depno);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public EmpService(Connection connection) {
        this.connection = connection;
    }
}
