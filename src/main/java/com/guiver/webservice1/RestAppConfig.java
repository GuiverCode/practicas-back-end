/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guiver.webservice1;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * Clase de configuracion Rest
 * @author guille
 */
@javax.ws.rs.ApplicationPath("webresources")//base URI
public class RestAppConfig extends Application {

    //Get a set of root resource and provider classes.
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(com.guiver.webservice1.DepartmentService.class);
        resources.add(com.guiver.webservice1.EmployeeService.class);
        resources.add(com.guiver.webservice1.exceptions.EntityNotFoundExceptionMapper.class);
        resources.add(com.guiver.webservice1.exceptions.ConstraintViolationExceptionMapper.class);
        return resources;
    }
}
