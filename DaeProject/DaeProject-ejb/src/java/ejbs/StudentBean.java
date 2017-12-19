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
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
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

    public void create(int id, String password, String name, String email, String studentNumber)
            throws EntityAlreadyExistsException, EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            if (em.find(Student.class, id) != null) {
                throw new EntityAlreadyExistsException("Student already exists.");
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
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("findStudent/{id}")
    public StudentDTO findStudent(@PathParam("id") String id)
            throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, id);

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
    public void updateREST(StudentDTO studentDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            Student student = em.find(Student.class, studentDTO.getId());
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no Student with that name.");
            }

            student.getPassword();
            student.getName();
            student.getEmail();
            student.getStudentNumber();
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
    @Path("/addDocument/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void addDocument(
            @PathParam("id") int id,
            DocumentDTO doc)
            throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, id);
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

    public DocumentDTO getDocument(int id) throws EntityDoesNotExistsException {
        Document doc = em.find(Document.class, id);

        if (doc == null) {
            throw new EntityDoesNotExistsException();
        }

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

    public boolean hasDocuments(String username)
            throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, username);

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

    public void remove(int id)
            throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, id);
            if (student == null) {
                throw new EntityDoesNotExistsException("There is no Student with that username.");
            }

            em.remove(student);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void sendEmailToStudent(String username) throws MessagingException, EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, username);
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

    public void sendEmailToStudent(int id) throws MessagingException, EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, id);
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
