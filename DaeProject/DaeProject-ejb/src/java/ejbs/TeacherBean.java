package ejbs;

import dtos.TeacherDTO;
import entities.Teacher;
import entities.TeacherProposal;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.ProposalNotInTeacherException;
import exceptions.TeacherEnrolledException;
import exceptions.TeacherNotInProposalException;
import exceptions.Utils;
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
public class TeacherBean extends Bean<Teacher> {

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
                    teacherDTO.getEmail(),
                    teacherDTO.getOffice()
            );

            em.persist(teacher);

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

            teacher.setPassword(teacherDTO.getPassword());
            teacher.setName(teacherDTO.getName());
            teacher.setEmail(teacherDTO.getEmail());
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
    @Path("/removeREST/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void remove(@PathParam("id") String id) 
            throws EntityDoesNotExistsException {
        try {
            Teacher teacher = em.find(Teacher.class, Integer.parseInt(id));
            if (teacher == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum professor com esse id.");
            }

            em.remove(teacher);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void enrollTeacher(int id, int proposalId)
            throws EntityDoesNotExistsException, TeacherEnrolledException {
        try {

            Teacher teacher = em.find(Teacher.class, id);
            if (teacher == null) {
                throw new EntityDoesNotExistsException("Não existe um professor com esse ID.");
            }

            TeacherProposal proposal = em.find(TeacherProposal.class, proposalId);
            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe uma proposta com esse ID.");
            }

            if (proposal.getTeachers().contains(teacher)) {
                throw new TeacherEnrolledException("O professor já está ligado a essa proposta.");
            }

            proposal.addTeacher(teacher);
            teacher.addProposal(proposal);

        } catch (EntityDoesNotExistsException | TeacherEnrolledException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void unrollTeacher(int id, int proposalId)
            throws EntityDoesNotExistsException {
        try {

            Teacher teacher = em.find(Teacher.class, id);
            if (teacher == null) {
                throw new EntityDoesNotExistsException("Não existe um professor com esse ID.");
            }

            TeacherProposal proposal = em.find(TeacherProposal.class, proposalId);
            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe uma proposta com esse ID.");
            }

            proposal.removeTeacher(teacher);
            teacher.removeProposal(proposal);

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
                    "Olá " + teacher.getName() + ", o estado da sua proposta foi alterado."
            );

        } catch (MessagingException | EntityDoesNotExistsException e) {
            throw e;
        }
    }
}