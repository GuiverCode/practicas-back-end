package com.guiver.webservice1;

import com.guiver.webservice1.model.Departments;
import com.guiver.webservice1.model.Employees;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("employees")
@Stateless
public class EmployeeService {

    @PersistenceContext(unitName = "com.guiver_webservice1_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EmployeesDto> findAllEmployees() {
        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery(Employees.class);
        cq.select(cq.from(Employees.class));
        List<Employees> employees = entityManager.createQuery(cq).getResultList();
        List<EmployeesDto> employeesDto = new ArrayList<>();

        for (Employees employee : employees) {
            EmployeesDto employeeDto = new EmployeesDto();
            employeeDto.employeeId = employee.getEmployeeId();
            employeeDto.firtsName = employee.getFirstName();
            employeeDto.lastName = employee.getLastName();
            employeeDto.email = employee.getEmail();
            employeeDto.phoneNumber = employee.getPhoneNumber();
            employeeDto.hireDate = employee.getHireDate();
            employeeDto.salary = employee.getSalary();
            employeeDto.commissionPct = employee.getCommissionPct();
            try {
                employeeDto.managerId = employee.getManagerId().getEmployeeId();
                employeeDto.departmentName = employee.getDepartment().getDepartmentName();
            } catch (NullPointerException e) {
                employeeDto.managerId = null;
                 employeeDto.departmentName = null;
            }
            employeesDto.add(employeeDto);
        }
    
        return employeesDto;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createEmployee(EmployeesDto employesDto){
        Employees newEmployee = new Employees();
        
        newEmployee.setEmail(employesDto.email);
        newEmployee.setFirstName(employesDto.firtsName);
        newEmployee.setHireDate(employesDto.hireDate);
        newEmployee.setLastName(employesDto.lastName);
        newEmployee.setSalary(employesDto.salary);
        newEmployee.setCommissionPct(employesDto.commissionPct);
        newEmployee.setPhoneNumber(employesDto.phoneNumber);
        newEmployee.setJobId(employesDto.jobId);
        newEmployee.setDepartment(entityManager.find(Departments.class, employesDto.departmentId));
        newEmployee.setManagerId(entityManager.find(Employees.class, employesDto.managerId));
        
        entityManager.persist(newEmployee);
        
    }
}

class EmployeesDto {

    public Integer employeeId;
    public String firtsName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String jobId;
    public Date hireDate;
    public BigDecimal salary;
    public BigDecimal commissionPct;
    public Integer managerId;
    public String departmentName;
    public Integer departmentId;
}
