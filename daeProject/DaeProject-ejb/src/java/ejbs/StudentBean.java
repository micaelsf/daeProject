/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.InstituitionDTO;
import dtos.StudentDTO;
import entities.Instituition;
import entities.Student;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/students")
public class StudentBean extends Bean<Student>{

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
    
    public void remove(int id) 
        throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, id);
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no student with that username.");
            }

            em.remove(student);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
        
    @Override
    protected Collection<Student> getAll() {
        return em.createNamedQuery("getAllStudents").getResultList();
    }
    
    @GET 
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
    @Path("all")
    public Collection<StudentDTO> getAllStudents() {
        try {
            return getAll(StudentDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @Path("/updateREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(StudentDTO studentDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
           
            System.out.println("ejbs.StudentBean.updateREST(), studentDTo is null? " + studentDTO);            

            Student student = em.find(Student.class, studentDTO.getId());
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no student with that name.");
            }

            student.setPassword(studentDTO.getPassword());
            student.setName(studentDTO.getName());
            student.setEmail(studentDTO.getEmail());
            student.setStudentNumber(studentDTO.getStudentNumber());
            
            em.merge(student);
            System.out.println("ejbs.StudentBean.updateREST() #3");
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
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
