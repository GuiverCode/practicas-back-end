package com.guiver.webservice1;

import com.guiver.webservice1.model.Departments;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("departments")
@Stateless
public class DepartmentService {
    //inyecta la instancia entity manager utilizada para las consultas
    @PersistenceContext(unitName = "com.guiver_webservice1_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    
    //retorna una lista de departamentos
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Departments> findAllDepartments(){
        //busca todos los departamentos en la BD
       
        /*CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Departments.class));
        List<Departments> deparments = entityManager.createQuery(cq).getResultList();*/
        
        TypedQuery<Departments> query = entityManager.createNamedQuery("Departments.findAll", Departments.class);
        List<Departments> departments = query.getResultList();
        
        return departments;
    }
    
    //agregar una consulta dinamica findWithName
}
