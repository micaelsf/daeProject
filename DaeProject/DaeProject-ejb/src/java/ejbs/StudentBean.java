/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.DocumentDTO;
import dtos.StudentDTO;
import entities.Course;
import entities.Document;
import entities.PublicProof;
import entities.Student;
import entities.WorkProposal;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.Collection;
import java.util.List;
import javax.annotation.security.RolesAllowed;
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
@Path("/students")
public class StudentBean extends Bean<Student> {

    @EJB
    EmailBean emailBean;

    @POST
    @Path("/createREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(StudentDTO studentDTO)
            throws EntityAlreadyExistsException, MyConstraintViolationException, EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, studentDTO.getUsername());
            if (student != null) {
                throw new EntityAlreadyExistsException("Já existe um estudante com esse id.");
            }

            Course course = em.find(Course.class, studentDTO.getCourseId());
            if (course == null) {
                throw new EntityDoesNotExistsException("Não existe um curso com esse id.");
            }

            student = new Student(
                    studentDTO.getUsername(),
                    studentDTO.getPassword(),
                    studentDTO.getName(),
                    studentDTO.getEmail(),
                    studentDTO.getStudentNumber(),
                    studentDTO.getCity(),
                    studentDTO.getAddress(),
                    course
            );

            course.addStudent(student);
            em.persist(student);

        } catch (EntityAlreadyExistsException | EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @RolesAllowed({"Administrator"})
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
    @Path("allStudentsFromCourse/{id}")
    public Collection<StudentDTO> getAllStudentsFromCourse(@PathParam("id") String id) {
        try {
            Collection<Student> students = em.createNamedQuery("getAllStudentsCourse")
                    .setParameter("courseId", Integer.parseInt(id))
                    .getResultList();

            return toDTOs(students, StudentDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("findStudentByEmail/{email}")
    public StudentDTO findStudent(@PathParam("email") String email)
            throws EntityDoesNotExistsException {
        try {
            
            return toDTO(getStudentByEmail(email),StudentDTO.class);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("findStudentByUsername/{username}")
    public StudentDTO findStudentByUsername(@PathParam("username") String username)
            throws EntityDoesNotExistsException {
        try {
            Student student = (Student) em.createNamedQuery("getStudentByUsername")
                    .setParameter("username", username)
                    .getSingleResult();

            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum estudante com esse username.");
            }
            
            return toDTO(student, StudentDTO.class);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Path("/searchByName/{name}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public StudentDTO getStudentByName(@PathParam("name") String name) {
        try {
            Student student = (Student) em.createNamedQuery("getStudentByName")
                    .setParameter("name", name)
                    .getSingleResult();

            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum estudante com esse nome.");
            }
            return toDTO(student, StudentDTO.class);
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
            Student student = em.find(Student.class, studentDTO.getUsername());
            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum estudante com esse id.");
            }

            Course course = em.find(Course.class, studentDTO.getCourseId());
            if (course == null) {
                throw new EntityDoesNotExistsException("Não existe um Curso com esse id.");
            }

            student.setUsername(studentDTO.getUsername());
            student.setPassword(studentDTO.getPassword());
            student.setName(studentDTO.getName());
            student.setEmail(studentDTO.getEmail());
            student.setStudentNumber(studentDTO.getStudentNumber());
            student.setCity(studentDTO.getCity());
            student.setAddress(studentDTO.getAddress());

            student.getCourse().removeStudent(student);
            student.setCourse(course);

            course.addStudent(student);

            em.merge(student);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @Path("/removeREST/{username}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void remove(@PathParam("username") String username) 
            throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum estudante com esse username.");
            }

            student.getCourse().removeStudent(student);

            em.remove(student);

        } catch (EntityDoesNotExistsException e) {
            throw e;
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
                throw new EntityDoesNotExistsException("Não existe nenhum estudante com esse id.");
            }

            Document document = new Document(doc.getFilepath(), doc.getDesiredName(), doc.getMimeType());
            em.persist(document);
            document.setStudent(student);
            em.merge(document);
            student.addDocument(document);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("isStudentAppliedToProposal/{username}/{proposalId}")
    public String isStudentAppliedToProposal(
            @PathParam("username") String username,
            @PathParam("proposalId") String proposalId)
            throws EntityDoesNotExistsException {
        try {
            Student student = (Student) em.createNamedQuery("getStudentByUsername")
                    .setParameter("username", username)
                    .getSingleResult();

            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum estudante com esse username.");
            }
            
            WorkProposal proposal = (WorkProposal) em.createNamedQuery("getProposalById")
                    .setParameter("proposalId", Integer.parseInt(proposalId))
                    .getSingleResult();

            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma Proposta de trabalho com esse Id.");
            }
            return student.getWorkProposalsApply().contains(proposal) ? "YES" : "NO";
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("isStudentMaxProposalReached/{username}/{proposalId}")
    public String isStudentMaxProposalReached(
            @PathParam("username") String username,
            @PathParam("proposalId") String proposalId)
            throws EntityDoesNotExistsException {
        try {
            Student student = (Student) em.createNamedQuery("getStudentByUsername")
                    .setParameter("username", username)
                    .getSingleResult();

            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum estudante com esse username.");
            }
            
            WorkProposal proposal = (WorkProposal) em.createNamedQuery("getProposalById")
                    .setParameter("proposalId", Integer.parseInt(proposalId))
                    .getSingleResult();

            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma Proposta de trabalho com esse Id.");
            }
            
            return student.getWorkProposalsApply().size() + "";
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public Student getStudentByEmail(String email)
            throws EntityDoesNotExistsException {
        try {
            Student student = (Student) em.createNamedQuery("getStudentByEmail")
                    .setParameter("email", email)
                    .getSingleResult();

            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum estudante com esse email.");
            }
            
            return student;
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

    public Collection<DocumentDTO> getDocuments(int id) throws EntityDoesNotExistsException {
        try {
            List<Document> docs = em.createNamedQuery("getDocumentsOfStudent", Document.class).setParameter("id", id).getResultList();
            return toDTOs(docs, DocumentDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public boolean hasDocuments(int id)
            throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, id);

            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum estudante com esse id.");
            }
            return !student.getDocuments().isEmpty();
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void sendEmailAboutPublicProofTo(String email, String name, PublicProof publicProof) 
            throws MessagingException {
        try {
            emailBean.send(
                    email,
                    "Marcação da Prova Pública",
                    "<p>Exmo " + name + ", a sua Prova Pública com Título '"+publicProof.getWorkTitle()+
                    "', está agendada para o dia "+publicProof.getProofDate()+" às "+publicProof.getProofTime()+" horas.</p>" +
                    "<p>Por favor compareça 30 minutos antes de se iniciar a apresentação da mesma.</p>" +
                    "<br/>" +        
                    "<p>Atenciosamente,<br/> Membro da CCP</p>"
            );

        } catch (MessagingException e) {
            throw e;
        }
    }
    
    public void sendEmailToStudent(int id) throws MessagingException, EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, id);
            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum estudante com esse id.");
            }

            emailBean.send(
                    student.getEmail(),
                    "Assunto",
                    "Olá " + student.getName());

        } catch (MessagingException | EntityDoesNotExistsException e) {
            throw e;
        }
    }
    
}
