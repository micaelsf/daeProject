package ejbs;

import dtos.TeacherDTO;
import entities.Teacher;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.io.Serializable;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/teachers")
public class TeacherBean extends Bean<Teacher> implements Serializable{

    @EJB
    EmailBean emailBean;

    @POST
    @Path("/createREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TeacherDTO teacherDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            Teacher teacher = em.find(Teacher.class, teacherDTO.getId());
            if (teacher != null) {
                throw new EntityDoesNotExistsException("Não existe nenhum professor com esse nome.");
            }

            teacher = new Teacher(
                    teacherDTO.getPassword(),
                    teacherDTO.getName(),
                    teacherDTO.getEmail()
            );

            em.merge(teacher);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public Collection<TeacherDTO> getAllTeachers() {
        try {
            return getAll(TeacherDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
     @Override
    protected Collection<Teacher> getAll() {
        return em.createNamedQuery("getAllTeachers").getResultList();
    }
    

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("findTeacher/{id}")
    public TeacherDTO findTeacher(@PathParam("id") String id)
            throws EntityDoesNotExistsException {
        try {
            Teacher teacher = em.find(Teacher.class, id);

            if (teacher == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum professor com esse nome.");
            }
            return toDTO(teacher, TeacherDTO.class);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @Path("/updateREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(TeacherDTO teacherDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            Teacher teacher = em.find(Teacher.class, teacherDTO.getId());
            if (teacher == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum professor com esse nome.");
            }

            teacher.getPassword();
            teacher.getName();
            teacher.getEmail();
            em.merge(teacher);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int id) throws EntityDoesNotExistsException {
        try {
            Teacher teacher = em.find(Teacher.class, id);
            if (teacher == null) {
                throw new EntityDoesNotExistsException("There is no teacher with that username.");
            }

            em.remove(teacher);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void sendEmailToTeacher(int id) throws MessagingException, EntityDoesNotExistsException {
        try {
            Teacher teacher = em.find(Teacher.class, id);
            if (teacher == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum professor com esse id.");
            }

            emailBean.send(
                    teacher.getEmail(),
                    "Assunto",
                    "Olá " + teacher.getName());

        } catch (MessagingException | EntityDoesNotExistsException e) {
            throw e;
        }
    }

   

}