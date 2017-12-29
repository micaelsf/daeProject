package ejbs;

import dtos.TeacherDTO;
import entities.Admin;
import entities.PublicProof;
import entities.Teacher;
import entities.WorkProposal;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.Collection;
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
@Path("/teachers")
public class TeacherBean extends Bean<Teacher> {

    @EJB
    EmailBean emailBean;

    @POST
    @Path("/createREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TeacherDTO teacherDTO)
            throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            Teacher teacher = em.find(Teacher.class, teacherDTO.getUsername());
            if (teacher != null) {
                throw new EntityAlreadyExistsException("Já existe um professor com esse nome.");
            }

            teacher = new Teacher(
                    teacherDTO.getUsername(),
                    teacherDTO.getPassword(),
                    teacherDTO.getName(),
                    teacherDTO.getEmail(),
                    teacherDTO.getOffice(),
                    teacherDTO.getCity(),
                    teacherDTO.getAddress()
            );

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
    @RolesAllowed({"Administrator"})
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
    @Path("findTeacherByEmail/{email}")
    public TeacherDTO findTeacherByEmail(@PathParam("email") String email)
            throws EntityDoesNotExistsException {
        try {
            
            return toDTO(getTeacherByEmail(email), TeacherDTO.class);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("findTeacherByUsername/{username}")
    public TeacherDTO findTeacherByUsername(@PathParam("username") String username)
            throws EntityDoesNotExistsException {
        try {
            Teacher teacher = (Teacher) em.createNamedQuery("getTeacherByUsername")
                    .setParameter("username", username)
                    .getSingleResult();

            if (teacher == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum professor com esse username.");
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
            Teacher teacher = em.find(Teacher.class, teacherDTO.getUsername());
            if (teacher == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum professor com esse nome.");
            }

            teacher.setUsername(teacherDTO.getUsername());
            teacher.setPassword(teacherDTO.getPassword());
            teacher.setName(teacherDTO.getName());
            teacher.setEmail(teacherDTO.getEmail());
            teacher.setCity(teacherDTO.getCity());
            teacher.setAddress(teacherDTO.getAddress());
            em.merge(teacher);

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
            Teacher teacher = em.find(Teacher.class, username);
            if (teacher == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum professor com esse username.");
            }

            em.remove(teacher);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public Teacher getTeacherByEmail(String email)
            throws EntityDoesNotExistsException {
        try {
            Teacher teacher = (Teacher) em.createNamedQuery("getTeacherByEmail")
                    .setParameter("email", email)
                    .getSingleResult();

            if (teacher == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum professor com esse email.");
            }
            
            return teacher;
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
 
    public void sendEmailAboutProposalTo(int id, WorkProposal proposal) 
            throws MessagingException, EntityDoesNotExistsException {
        try {
            Teacher teacher = em.find(Teacher.class, id);
            if (teacher == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum professor com esse id.");
            }

            emailBean.send(
                    teacher.getEmail(),
                    "Assunto",
                    "Olá " + teacher.getName() + ", o estado da sua proposta '"+proposal.getTitle()+"' foi alterado."
            );

        } catch (MessagingException | EntityDoesNotExistsException e) {
            throw e;
        }
    }
    
    public void sendEmailAboutPublicProofTo(String email, String name, PublicProof publicProof) 
            throws MessagingException {
        try {
            emailBean.send(
                    email,
                    "Marcação da Prova Pública",
                    "<p>Exmo " + name + ", a Prova Pública com Título '"+publicProof.getWorkTitle()+
                    "', está agendada para o dia "+publicProof.getProofDate()+" às "+publicProof.getProofTime()+" horas.</p>" +
                    "<p>Por favor compareça 30 minutos antes de se iniciar a apresentação da mesma.</p>" +
                    "<br/>" +        
                    "<p>Atenciosamente,<br/> Membro da CCP</p>"
            );

        } catch (MessagingException e) {
            throw e;
        }
    }
}