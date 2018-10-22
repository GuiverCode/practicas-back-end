package com.guiver.webservice1;

import com.guiver.webservice1.exceptions.UniqueConstraintViolationException;
import com.guiver.webservice1.model.Department;
import com.guiver.webservice1.model.Employee;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("employee")
@Stateless
public class EmployeeService {

    @PersistenceContext(unitName = "com.guiver_webservice1_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Employee findById(@PathParam("id") Integer id){
        Employee employee = entityManager
                .createNamedQuery("Employee.findByEmployeeId", Employee.class)
                .setParameter("employeeId", id)
                .getSingleResult();
        return employee;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EmployeesDto> findAllEmployees() {
        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery(Employee.class);
        cq.select(cq.from(Employee.class));
        List<Employee> employees = entityManager.createQuery(cq).getResultList();
        List<EmployeesDto> employeesDto = new ArrayList<>();

        for (Employee employee : employees) {
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
    
        
    /*
        retorna los empleados de un departamento
    */
    @GET
    @Path("/department/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> findEmployeesByDepartment(@PathParam("id") Integer id){
        List<Employee> employees = 
                entityManager.createQuery("select e from Employee e WHERE e.department.departmentId = :id", Employee.class)
                .setParameter("id", id)
                .getResultList();
        return employees;
    }
    
    @POST //AGREGAR LOCATION HEADER
    @Consumes(MediaType.APPLICATION_JSON)
    public void createEmployee(EmployeesDto employesDto){
        Employee newEmployee = new Employee();
        
        //VERIFICA SI YA EXISTE EMAIL
        TypedQuery<Employee> query=entityManager.createNamedQuery("Employee.findByEmail", Employee.class);
        query.setParameter("email", employesDto.email);
        Employee result1;
        try {
            result1 = query.getSingleResult();
        } catch (NoResultException e) {
            result1 = null;
        }
        
        if(result1 != null){
            throw new UniqueConstraintViolationException("{\"message\": \"Ya existe el email\"}");
        }
        
        newEmployee.setEmail(employesDto.email);
        newEmployee.setFirstName(employesDto.firtsName);
        newEmployee.setHireDate(employesDto.hireDate);
        newEmployee.setLastName(employesDto.lastName);
        newEmployee.setSalary(employesDto.salary);
        newEmployee.setCommissionPct(employesDto.commissionPct);
        newEmployee.setPhoneNumber(employesDto.phoneNumber);
        newEmployee.setJobId(employesDto.jobId);
        newEmployee.setDepartment(entityManager.find(Department.class, employesDto.departmentId));
        newEmployee.setManagerId(entityManager.find(Employee.class, employesDto.managerId));
        
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
