package com.guiver.webservice1;

import com.guiver.webservice1.model.Departments;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("departments")
@Stateless
public class DepartmentService {
    //inyecta una instancia del  entity manager
    @PersistenceContext(unitName = "com.guiver_webservice1_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Departments> findAllDepartments(){
        /*TypedQuery<Departments> query = entityManager.createNamedQuery("Departments.findAll", Departments.class);
        List<Departments> departments =query.getResultList();
        return departments;*/
        
       CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
       cq.select(cq.from(Departments.class));
       List<Departments> departments = entityManager.createQuery(cq).getResultList();
       return departments;
    }

   /* @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Departments findByName(@PathParam("name") String name){
        /* CRITERIA
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Departments> dept = cq.from(Departments.class);
        cq.where(cb.equal(dept.get("departmentName"), "Administration"));
        cq.select(dept);
        Departments department = (Departments) entityManager.createQuery(cq).getSingleResult();
        return department;*/
        
        /* JPQL ESTATICO
        TypedQuery<Departments> query = entityManager.createNamedQuery("Departments.findByDepartmentName", Departments.class);
        query.setParameter("departmentName", name);
        Departments department = query.getSingleResult();
        return department;*/
        
      /* //JPQL DINAMICO
        TypedQuery<Departments> query=entityManager.createQuery("SELECT d FROM Departments d WHERE d.departmentName LIKE :name", Departments.class);
        query.setParameter("name", name);
        Departments department = query.getSingleResult();
        return department;
    }*/
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Departments findById(@PathParam("id") Integer id){
        /*TypedQuery query = entityManager.createNamedQuery("Departments.findByDepartmentId", Departments.class);
        query.setParameter("departmentId", id);
        Departments department = (Departments) query.getSingleResult();*/
        return entityManager.find(Departments.class, id);
      }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createDepartment(Departments entity){
        entityManager.persist(entity);
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void editDepartment(@PathParam("id") Integer id, Departments entity){
        entity.setDepartmentId(id);
        entityManager.merge(entity);
    }
    
    @DELETE
    @Path("{id}")
    public void deleteDepartment(@PathParam("id") Integer id){
        Departments entity = entityManager.find(Departments.class, id);
        entityManager.remove(entity);
    }
}



