package org.example;

import java.io.File;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) {

        File fileConfig = new File("dataSource.xml");
        try(Dao dao = new Dao(fileConfig)) {
            Connection connection = dao.getConnection();
            EmpService empService = new EmpService(connection);
            while (true) {
                Scanner menuScanner = new Scanner(System.in);
                System.out.println("1. Show all employees info.");
                System.out.println("2. Show employee info by ID.");
                System.out.println("3. Delete employee by ID.");
                System.out.println("4. Add new employee.");
                System.out.println("5. Exit.");
                int x = menuScanner.nextInt();
                switch (x) {
                    case 1:
                        List<Employee> employeeList = empService.getAllEmpInfo();
                        int count = 1;
                        for (Employee employee: employeeList) {
                            System.out.println(count + ". " + employee.toString());
                            count++;
                        }
                        break;
                    case 2:
                        Scanner scannerCase2 = new Scanner(System.in);
                        System.out.println("Enter employee id:");
                        int idCase2 = scannerCase2.nextInt();
                        System.out.println(empService.getEmpInfo(idCase2).toString());
                        break;
                    case 3:
                        Scanner scannerCase3 = new Scanner(System.in);
                        System.out.println("Enter employee id:");
                        int idCase3 = scannerCase3.nextInt();
                        if (empService.deleteEmployee(idCase3)) {
                            System.out.println("Employee successfully deleted.");
                        } else {
                            System.out.println("Error! Employee not deleted.");
                        }
                        break;
                    case 4:
                        if (empService.addEmployee()) {
                            System.out.println("Employee successfully added.");
                        } else {
                            System.out.println("Error! Employee not added.");
                        }
                        break;
                    case 5:
                        return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
