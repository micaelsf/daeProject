package ejbs;

import dtos.CourseDTO;
import entities.Course;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.Collection;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/courses")
public class CourseBean extends Bean<Course>{

    @POST
    @Path("/createREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(CourseDTO courseDTO)
            throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            Course course = em.find(Course.class, courseDTO.getId());

            if (course != null) {
                throw new EntityAlreadyExistsException("Já existe um curso com esse ID.");
            }
            
            course = new Course(
                    courseDTO.getId(),
                    courseDTO.getName()
            );
            
            em.persist(course);

        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @Override
    protected Collection<Course> getAll() {
        return em.createNamedQuery("getAllCourses").getResultList();
    }
    
    @GET
    @Path("/all")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<CourseDTO> getAllCourses() {
        try {
            return getAll(CourseDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @Path("/updateREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(CourseDTO courseDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            Course course = em.find(Course.class, courseDTO.getId());
            if (course == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum curso com esse id.");
            }

            course.setName(courseDTO.getName());
            em.merge(course);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @POST
    @Path("/removeREST/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void remove(@PathParam("id") String id) 
            throws EntityDoesNotExistsException {
        try {
            Course course = em.find(Course.class, Integer.parseInt(id));
            if (course == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum curso com esse id.");
            }

            em.remove(course);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
