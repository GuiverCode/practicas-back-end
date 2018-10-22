package com.guiver.webservice1.model;

import com.guiver.webservice1.model.Department;
import com.guiver.webservice1.model.Employee;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-18T14:52:42")
@StaticMetamodel(Employee.class)
public class Employee_ { 

    public static volatile CollectionAttribute<Employee, Employee> employeesCollection;
    public static volatile SingularAttribute<Employee, String> lastName;
    public static volatile SingularAttribute<Employee, String> firstName;
    public static volatile SingularAttribute<Employee, String> jobId;
    public static volatile SingularAttribute<Employee, Date> hireDate;
    public static volatile SingularAttribute<Employee, String> phoneNumber;
    public static volatile SingularAttribute<Employee, Integer> employeeId;
    public static volatile SingularAttribute<Employee, Employee> managerId;
    public static volatile SingularAttribute<Employee, BigDecimal> salary;
    public static volatile SingularAttribute<Employee, Department> department;
    public static volatile SingularAttribute<Employee, String> email;
    public static volatile SingularAttribute<Employee, BigDecimal> commissionPct;

}