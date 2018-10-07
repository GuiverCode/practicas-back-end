package com.guiver.webservice1.model;

import com.guiver.webservice1.model.Employee;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-04T18:02:42")
@StaticMetamodel(Departments.class)
public class Departments_ { 

    public static volatile SingularAttribute<Departments, String> departmentName;
    public static volatile SingularAttribute<Departments, Employee> manager;
    public static volatile SingularAttribute<Departments, Integer> locationId;
    public static volatile SingularAttribute<Departments, Integer> departmentId;

}