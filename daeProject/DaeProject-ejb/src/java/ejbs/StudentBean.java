/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.StudentDTO;
import entities.Student;
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
@Path("/students")
public class StudentBean {

    @PersistenceContext
    private EntityManager em;

    public void create(int id, String password, String name, String email, String studentNumber)
         throws EntityAlreadyExistsException, EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            if (em.find(Student.class, id) != null) {
                throw new EntityAlreadyExistsException("User already exists.");
            }

            Student student = new Student(password, name, email, studentNumber);
            em.persist(student);
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
    public List<StudentDTO> getAll() {
        try {
            List<Student> students = (List<Student>) em.createNamedQuery("getAllStudents").getResultList();
            return studentsToDTOs(students);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    List<StudentDTO> studentsToDTOs(List<Student> students) {
        List<StudentDTO> dtos = new ArrayList<>();
        for (Student s : students) {
            dtos.add(studentToDTO(s));
        }
        return dtos;
    }
    
    StudentDTO studentToDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getStudentNumber(),
                null,
                student.getName(),
                student.getEmail());
    }
}