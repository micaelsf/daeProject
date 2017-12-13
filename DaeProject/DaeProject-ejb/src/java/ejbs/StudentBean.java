/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.DocumentDTO;
import dtos.StudentDTO;
import entities.Document;
import entities.Student;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
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
@Path("/students")
public class StudentBean extends Bean<Student> {

    @EJB
    EmailBean emailBean;

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
    public Collection<StudentDTO> getAllStudents() {
        try {
            return getAll(StudentDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @Override
    protected Collection<Student> getAll() {
        return em.createNamedQuery("getAllStudents").getResultList();
    }
    
    @GET
    @RolesAllowed({"Student"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("findStudent/{username}")
    public StudentDTO findStudent(@PathParam("studentNumber") String studentNumber)
            throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, studentNumber);
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no user with such username.");
            }
            return toDTO(student, StudentDTO.class);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @Path("/updateREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(StudentDTO student)
            throws EntityDoesNotExistsException,
            MyConstraintViolationException {
        try {
            update(
                    student.getPassword(),
                    student.getName(),
                    student.getEmail(),
                    student.getStudentNumber());
        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
        public void update(String password, String name, String email, String studentNumber)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            Student student = em.find(Student.class, studentNumber);
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no student with that username.");
            }
            student.setPassword(password);
            student.setName(name);
            student.setEmail(email);
            em.merge(student);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
        
    @PUT
    @RolesAllowed({"Student"})
    @Path("/addDocument/{username}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void addDocument(
            @PathParam("studentNumber") String studentNumber,
            DocumentDTO doc)
            throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, studentNumber);
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no student with such username.");
            }

            Document document = new Document(doc.getFilepath(), doc.getDesiredName(), doc.getMimeType(), student);
            em.persist(document);
            student.addDocument(document);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public DocumentDTO getDocument(String studentNumber) throws EntityDoesNotExistsException {
        Document doc = em.find(Document.class, studentNumber);
            
        if (doc == null)
            throw new EntityDoesNotExistsException();

        return toDTO(doc, DocumentDTO.class);
    }
    
    public Collection<DocumentDTO> getDocuments(String username) throws EntityDoesNotExistsException {
        try {
            List<Document> docs = em.createNamedQuery("getDocumentsOfStudent", Document.class).setParameter("username", username).getResultList();
            return toDTOs(docs, DocumentDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public boolean hasDocuments(String studentNumber)
            throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, studentNumber);
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no user with such username.");
            }
            return !student.getDocuments().isEmpty();
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void remove(String studentNumber) throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, studentNumber);
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
    
        public void sendEmailToStudent(String studentNumber) throws MessagingException, EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, studentNumber);
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no student with that username.");
            }

            emailBean.send(
                    student.getEmail(),
                    "Subject",
                    "Hello " + student.getName());
        
        } catch (MessagingException | EntityDoesNotExistsException e) {
            throw e;
        }
    }
}
