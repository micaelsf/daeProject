/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.TeacherProposalDTO;
import entities.TeacherProposal;
import entities.TeacherProposal.TeacherProposalType;
import entities.WorkProposal.ProposalStatus;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.Collection;
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
    @Path("/createREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TeacherProposalDTO proposalDTO)
         throws EntityAlreadyExistsException, MyConstraintViolationException {
        System.out.println("ejbs.TeacherProposalBean.create() IN");
        try {
            if (em.find(TeacherProposal.class, proposalDTO.getId()) != null) {
                throw new EntityAlreadyExistsException("A proposta já existe.");
            }
            /*
            List<String> bibliography = new LinkedList<>();
            bibliography.add(proposalDTO.getBibliography1());
            bibliography.add(proposalDTO.getBibliography2());
            bibliography.add(proposalDTO.getBibliography3());
            bibliography.add(proposalDTO.getBibliography4());
            bibliography.add(proposalDTO.getBibliography5());
            */
            TeacherProposal proposal = new TeacherProposal(
                    proposalDTO.getTitle(), 
                    proposalDTO.getScientificAreas(), 
                    proposalDTO.getObjectives(),
                    proposalDTO.getWorkResume(),
                    //bibliography,
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
                    TeacherProposalType.valueOf(proposalDTO.getProposalType())
            );
            
            em.persist(proposal);
        } catch (EntityAlreadyExistsException e) {
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
            em.remove(proposal);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT 
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
    @Path("/updateStatusREST/{id}/{status}")
    public void updateStatusWorkProposalREST(
            @PathParam("id") String idStr,
            @PathParam("status") ProposalStatus status) 
        throws EntityDoesNotExistsException {
        try {
            TeacherProposal proposal = em.find(TeacherProposal.class, Integer.parseInt(idStr));
            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma proposta com esse ID.");
            }
            
            proposal.setStatus(status);
            em.merge(proposal);

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
    @Path("all")
    public Collection<TeacherProposalDTO> getAllProposals() {
        try {
            return getAll(TeacherProposalDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @Path("/updateREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(TeacherProposalDTO proposalDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            TeacherProposal proposal = em.find(TeacherProposal.class, proposalDTO.getId());
            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma proposta com esse ID");
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
