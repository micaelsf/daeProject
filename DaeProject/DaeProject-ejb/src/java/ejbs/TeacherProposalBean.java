/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.TeacherProposalDTO;
import entities.Teacher;
import entities.TeacherProposal;
import entities.TeacherProposal.TeacherProposalType;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.TeacherEnrolledException;
import exceptions.Utils;
import java.util.Collection;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
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
@Path("/teacherProposals")
public class TeacherProposalBean extends Bean<TeacherProposal> {
    
    @POST
    @Path("/createREST/{username}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(@PathParam("username") String username, TeacherProposalDTO proposalDTO)
         throws EntityAlreadyExistsException, MyConstraintViolationException, EntityDoesNotExistsException {
        try {
            if (em.find(TeacherProposal.class, proposalDTO.getId()) != null) {
                throw new EntityAlreadyExistsException("A proposta já existe.");
            }
            
            Teacher teacher = em.find(Teacher.class, username);
            if (teacher == null) {
                throw new EntityDoesNotExistsException("Não existe um professor com esse username.");
            }
            
            TeacherProposal proposal = new TeacherProposal(
                    proposalDTO.getTitle(), 
                    proposalDTO.getScientificAreas(), 
                    proposalDTO.getObjectives(),
                    proposalDTO.getWorkResume(),
                    proposalDTO.getBibliography1(),
                    proposalDTO.getBibliography2(),
                    proposalDTO.getBibliography3(),
                    proposalDTO.getBibliography4(),
                    proposalDTO.getBibliography5(),
                    proposalDTO.getWorkPlan(),
                    proposalDTO.getWorkLocality(),
                    proposalDTO.getSuccessRequirements(),
                    proposalDTO.getBudget(),
                    proposalDTO.getSupport(),
                    TeacherProposalType.valueOf(proposalDTO.getProposalType()),
                    teacher
            );
            
            teacher.addProposal(proposal);
            em.persist(proposal);
            
        } catch (EntityAlreadyExistsException | EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
                  
    @POST 
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
    @Path("/removeREST/{id}")
    public void remove(@PathParam("id") String id) 
        throws EntityDoesNotExistsException {
        try {
            TeacherProposal proposal = em.find(TeacherProposal.class, Integer.parseInt(id));
            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma proposta com esse ID.");
            }
            
            proposal.getTeacher().removeProposal(proposal);
            
            em.remove(proposal);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @Override
    protected Collection<TeacherProposal> getAll() {
        return em.createNamedQuery("getAllTeacherProposals").getResultList();
    }
    
    @GET 
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
    @RolesAllowed({"Administrator", "Teacher"})
    @Path("all")
    public Collection<TeacherProposalDTO> getAllProposals() {
        try {
            return getAll(TeacherProposalDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET 
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
    @RolesAllowed({"Administrator", "Teacher"})
    @Path("/all/teacher/{username}")
    public Collection<TeacherProposalDTO> getAllTeacherProposals(
            @PathParam("username") String username) {
        try {
            Teacher teacher = em.find(Teacher.class, username);
            
            if (teacher == null) {
                throw new EntityDoesNotExistsException("Não existe um professor com este username");
            }
            
            return toDTOs(teacher.getProposals(), TeacherProposalDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Path("/searchByTitle/{title}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public TeacherProposalDTO getTeacherProposalByTitle(@PathParam("title") String title) {
        try {
            TeacherProposal proposal = (TeacherProposal) em.createNamedQuery("getTeacherProposalByTitle")
                    .setParameter("title", title)
                    .getSingleResult();

            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma proposta com esse titulo.");
            }
            return toDTO(proposal, TeacherProposalDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @Path("/updateREST/{username}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(@PathParam("username") String username, TeacherProposalDTO proposalDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            TeacherProposal proposal = em.find(TeacherProposal.class, proposalDTO.getId());
            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma proposta com esse ID");
            }

            Teacher teacher = em.find(Teacher.class, username);
            if (teacher == null) {
                throw new EntityDoesNotExistsException("Não existe um professor com esse ID.");
            }
            
            proposal.setTitle(proposalDTO.getTitle());
            proposal.setScientificAreas(proposalDTO.getScientificAreas());
            proposal.setObjectives(proposalDTO.getObjectives());
            proposal.setWorkResume(proposalDTO.getWorkResume());
            proposal.setBibliography1(proposalDTO.getBibliography1());
            proposal.setBibliography2(proposalDTO.getBibliography2());
            proposal.setBibliography3(proposalDTO.getBibliography3());
            proposal.setBibliography4(proposalDTO.getBibliography4());
            proposal.setBibliography5(proposalDTO.getBibliography5());
            proposal.setWorkPlan(proposalDTO.getWorkPlan());
            proposal.setWorkLocality(proposalDTO.getWorkLocality());
            proposal.setSuccessRequirements(proposalDTO.getSuccessRequirements());
            proposal.setBudget(proposalDTO.getBudget());
            proposal.setSupport(proposalDTO.getSupport());
            proposal.setEnumProposalType(TeacherProposalType.valueOf(proposalDTO.getProposalType()));
            
            proposal.getTeacher().removeProposal(proposal);
            proposal.setTeacher(teacher);
                  
            teacher.addProposal(proposal);
            
            em.merge(proposal);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
