/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.WorkProposalDTO;
import entities.WorkProposal;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/proposals")
public class WorkProposalBean extends Bean<WorkProposal>{
    
    public void create(int id, String title, String scietificAreas, String objectives, int status)
         throws EntityAlreadyExistsException, EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            if (em.find(WorkProposal.class, id) != null) {
                throw new EntityAlreadyExistsException("A proposta já existe.");
            }
            WorkProposal proposal = new WorkProposal(title, scietificAreas, objectives, status);
            em.persist(proposal);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
            
    @DELETE 
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
    @Path("/deleteREST")
    public void removeREST(WorkProposalDTO proposalDTO) 
        throws EntityDoesNotExistsException {
        try {
            WorkProposal proposal = em.find(WorkProposal.class, proposalDTO.getId());
            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe uma proposta com esse ID.");
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
            @PathParam("status") String statusStr) 
        throws EntityDoesNotExistsException {
        try {
            WorkProposal proposal = em.find(WorkProposal.class, Integer.parseInt(idStr));
            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe uma proposta com esse ID.");
            }
            
            proposal.setStatus(Integer.parseInt(statusStr));
            em.merge(proposal);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @Override
    protected Collection<WorkProposal> getAll() {
        return em.createNamedQuery("getAllProposals").getResultList();
    }
    
    @GET 
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
    @Path("all")
    public Collection<WorkProposalDTO> getAllProposals() {
        try {
            return getAll(WorkProposalDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @Path("/updateREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(WorkProposalDTO proposalDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            WorkProposal proposal = em.find(WorkProposal.class, proposalDTO.getId());
            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe uma proposta com esse ID");
            }

            proposal.setTitle(proposalDTO.getTitle());
            proposal.setScientificAreas(proposalDTO.getScientificAreas());
            proposal.setStatus(proposalDTO.getStatus());
            
            em.merge(proposal);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    List<WorkProposalDTO> proposalsToDTOs(List<WorkProposal> proposals) {
        List<WorkProposalDTO> dtos = new ArrayList<>();
        for (WorkProposal p : proposals) {
            dtos.add(proposalToDTO(p));
        }
        return dtos;
    }
    
    WorkProposalDTO proposalToDTO(WorkProposal proposal) {
        return new WorkProposalDTO(
                proposal.getId(),
                proposal.getTitle(),
                proposal.getScientificAreas(),                
                proposal.getObjectives(),
                proposal.getStatus());
    }
}
