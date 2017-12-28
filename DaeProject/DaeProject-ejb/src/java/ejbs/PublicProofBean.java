package ejbs;

import dtos.PublicProofDTO;
import entities.PublicProof;
import entities.Student;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/publicProofs")
public class PublicProofBean extends Bean<PublicProof>{

    @POST
    @Path("/createREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(PublicProofDTO publicProofDTO)
            throws EntityAlreadyExistsException, EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            PublicProof publicProof = em.find(PublicProof.class, publicProofDTO.getId());
            if (publicProof != null) {
                throw new EntityAlreadyExistsException("Já existe uma prova pública com esse Id.");
            }
            
            Student student = em.find(Student.class, publicProofDTO.getStudentId());
            
            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe um estudante com esse Id.");
            }
            
            publicProof = new PublicProof(
                    publicProofDTO.getId(),
                    publicProofDTO.getProofDate(),
                    publicProofDTO.getProofTime(),
                    publicProofDTO.getLocation(),
                    publicProofDTO.getCcpMember(),
                    publicProofDTO.getCcpMemberEmail(),
                    publicProofDTO.getWorkGuider(),
                    publicProofDTO.getWorkGuiderEmail(),
                    publicProofDTO.getTeacher(),
                    publicProofDTO.getTeacherEmail(),
                    student,
                    publicProofDTO.getWorkTitle()
            );
            
            student.setPublicProof(publicProof);
            em.persist(publicProof);

        } catch (EntityAlreadyExistsException | EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @Override
    protected Collection<PublicProof> getAll() {
        return em.createNamedQuery("getAllPublicProofs").getResultList();
    }
    
    @GET
    @Path("/all")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<PublicProofDTO> getAllPublicProofs() {
        try {
            return getAll(PublicProofDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @Path("/updateREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(PublicProofDTO publicProofDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            PublicProof publicProof = em.find(PublicProof.class, publicProofDTO.getId());
            if (publicProof == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma Prova Pública com esse id.");
            }
            
            Student student = em.find(Student.class, publicProofDTO.getStudentId());
            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe um estudante com esse Id.");
            }
            
            publicProof.setProofDate(publicProofDTO.getProofDate());            
            publicProof.setProofTime(publicProofDTO.getProofTime());
            publicProof.setLocation(publicProofDTO.getLocation());
            publicProof.setCcpMember(publicProofDTO.getCcpMember());
            publicProof.setCcpMemberEmail(publicProofDTO.getCcpMemberEmail());
            publicProof.setWorkGuider(publicProofDTO.getWorkGuider());
            publicProof.setWorkGuiderEmail(publicProofDTO.getWorkGuiderEmail());
            publicProof.setTeacher(publicProofDTO.getTeacher());
            publicProof.setTeacherEmail(publicProofDTO.getTeacherEmail());
            publicProof.setWorkTitle(publicProofDTO.getWorkTitle());
            publicProof.setStudent(student);

            em.merge(publicProof);

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
            PublicProof publicProof = em.find(PublicProof.class, Integer.parseInt(id));
            if (publicProof == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma Prova Pública com esse id.");
            }

            em.remove(publicProof);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
