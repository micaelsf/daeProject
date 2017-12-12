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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    
    
    @PUT
    @Path("updateREST/{id}/{password}/{name}/{email}/{teacherNumber}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(
            @PathParam("id") String id,
            @PathParam("name") String name,
            @PathParam("email") String email,            
            @PathParam("teacherNumber") String teacherNumber)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            Teacher teacher = em.find(Teacher.class, id);
            if (teacher == null) {
                throw new EntityDoesNotExistsException("There is no student with that id.");
            }
 
            teacher.setName(name);
            teacher.setEmail(email);            
            teacher.setTeacherNumber(teacherNumber);
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
                throw new EntityDoesNotExistsException("There is no student with that id.");
            }
            
            em.remove(teacher);
        
        } catch (EntityDoesNotExistsException e) {
            throw e;
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
