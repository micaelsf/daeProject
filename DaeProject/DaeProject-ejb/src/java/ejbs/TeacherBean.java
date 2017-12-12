/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.TeacherDTO;
import entities.Teacher;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/teachers")
public class TeacherBean {

    @PersistenceContext
    private EntityManager em;

    public void create(int id, String password, String name, String email, String teacherNumber)
         throws EntityAlreadyExistsException, EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            if (em.find(Teacher.class, id) != null) {
                throw new EntityAlreadyExistsException("Teacher already exists.");
            }

            Teacher teacher = new Teacher(password, name, email, teacherNumber);
            em.persist(teacher);
        } catch (EntityAlreadyExistsException e) {
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
    public List<TeacherDTO> getAll() {
        try {
            List<Teacher> teachers = (List<Teacher>) em.createNamedQuery("getAllTeachers").getResultList();
            return teachersToDTOs(teachers);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    List<TeacherDTO> teachersToDTOs(List<Teacher> teachers) {
        List<TeacherDTO> dtos = new ArrayList<>();
        for (Teacher t : teachers) {
            dtos.add(teacherToDTO(t));
        }
        return dtos;
    }
    
    TeacherDTO teacherToDTO(Teacher teacher) {
        return new TeacherDTO(
                teacher.getId(),
                teacher.getTeacherNumber(),
                null,
                teacher.getName(),
                teacher.getEmail());
    }
}
