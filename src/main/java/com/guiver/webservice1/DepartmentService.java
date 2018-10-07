package com.guiver.webservice1;

import com.guiver.webservice1.model.Department;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.guiver.webservice1.exceptions.EntityNotFoundException;

@Path("department")
@Stateless
public class DepartmentService {
    //inyecta una instancia del  entity manager
    @PersistenceContext(unitName = "com.guiver_webservice1_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Department> findAllDepartments(){
        /*TypedQuery<Departments> query = entityManager.createNamedQuery("Department.findAll", Department.class);
        List<Departments> departments =query.getResultList();
        return departments;*/
        
       CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
       cq.select(cq.from(Department.class));
       List<Department> departments = entityManager.createQuery(cq).getResultList();
       return departments;
    }

   /* @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Department findByName(@PathParam("name") String name){
        /* CRITERIA
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Departments> dept = cq.from(Department.class);
        cq.where(cb.equal(dept.get("departmentName"), "Administration"));
        cq.select(dept);
        Department department = (Department) entityManager.createQuery(cq).getSingleResult();
        return department;*/
        
        /* JPQL ESTATICO
        TypedQuery<Departments> query = entityManager.createNamedQuery("Department.findByDepartmentName", Department.class);
        query.setParameter("departmentName", name);
        Department department = query.getSingleResult();
        return department;*/
        
      /* //JPQL DINAMICO
        TypedQuery<Departments> query=entityManager.createQuery("SELECT d FROM Department d WHERE d.departmentName LIKE :name", Department.class);
        query.setParameter("name", name);
        Department department = query.getSingleResult();
        return department;
    }*/
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Department findById(@PathParam("id") Integer id){
        /*TypedQuery query = entityManager.createNamedQuery("Department.findByDepartmentId", Department.class);
        query.setParameter("departmentId", id);
        Department department = (Department) query.getSingleResult();*/
        return entityManager.find(Department.class, id);
      }

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDepartment(Department entity){
        entityManager.persist(entity);
        entityManager.flush();
        //return entity.getDepartmentId();
        return Response.status(Status.CREATED).entity("{\"id\":" + entity.getDepartmentId().toString() + "}").build(); //retorna el id en formato json
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void editDepartment(@PathParam("id") Integer id, Department entity){
        entity.setDepartmentId(id);
        entityManager.merge(entity);
    }
    
    @DELETE
    @Path("{id}")
    public void deleteDepartment(@PathParam("id") Integer id) throws EntityNotFoundException{
        Department entity = entityManager.find(Department.class, id);
        if(entity == null){//Lanza la excepcion 404 not found
            //throw new WebApplicationException(Response.Status.NOT_FOUND);
            throw new EntityNotFoundException("No se encuentra el departamento");

        }
        entityManager.remove(entity);
    }
}


