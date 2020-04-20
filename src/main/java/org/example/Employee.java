package org.example;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Employee {

    private String id;
    private String name;
    private String job;
    private String managerId;
    private String hireDate;
    private String department;
    private String salary;
    private String commission;
    private String grade;

    @Override
    public String toString() {
        return "Id='" + id + '\'' +
                ", Name ='" + name + '\'' +
                ", Job ='" + job + '\'' +
                ", Manager Id ='" + managerId + '\'' +
                ", HireDate ='" + hireDate + '\'' +
                ", Department ='" + department + '\'' +
                ", Salary ='" + salary + '\'' +
                ", Commission ='" + commission + '\'' +
                ", Grade ='" + grade + '\'' +
                '}';
    }
}
