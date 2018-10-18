package com.guiver.webservice1.model;

import com.guiver.webservice1.model.Location;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-17T20:25:50")
@StaticMetamodel(Department.class)
public class Department_ { 

    public static volatile SingularAttribute<Department, String> departmentName;
    public static volatile SingularAttribute<Department, Integer> departmentId;
    public static volatile SingularAttribute<Department, Location> location;
    public static volatile SingularAttribute<Department, Integer> managerId;

}