/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.InstitutionProposalDTO;
import entities.Institution;
import entities.InstitutionProposal;
import entities.InstitutionProposal.InstitutionProposalType;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.InstitutionEnrolledException;
import exceptions.MyConstraintViolationException;
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
@Path("/institutionProposals")
public class InstitutionProposalBean extends Bean<InstitutionProposal> {
    
    @POST
    @Path("/createREST/{username}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(@PathParam("username") String username, InstitutionProposalDTO proposalDTO)
         throws EntityAlreadyExistsException, MyConstraintViolationException, EntityDoesNotExistsException {
        try {
            if (em.find(InstitutionProposal.class, proposalDTO.getId()) != null) {
                throw new EntityAlreadyExistsException("A proposta já existe.");
            }
            
            Institution institution = em.find(Institution.class, username);
            if (institution == null) {
                throw new EntityDoesNotExistsException("Não existe uma Instituição com esse ID.");
            }
            
            InstitutionProposal proposal = new InstitutionProposal(
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
                    proposalDTO.getSupervisor(),
                    InstitutionProposalType.valueOf(proposalDTO.getProposalType()),
                    institution
            );
            
            institution.addProposal(proposal);
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
            InstitutionProposal proposal = em.find(InstitutionProposal.class, Integer.parseInt(id));
            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma proposta com esse ID.");
            }
            
            proposal.getInstitution().removeProposal(proposal);
            
            em.remove(proposal);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @Override
    protected Collection<InstitutionProposal> getAll() {
        return em.createNamedQuery("getAllInstitutionProposals").getResultList();
    }
    
    @GET 
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
    @RolesAllowed({"Administrator", "Institution"})
    @Path("all")
    public Collection<InstitutionProposalDTO> getAllProposals() {
        try {
            return getAll(InstitutionProposalDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }  
    
    @GET 
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
    @RolesAllowed({"Administrator", "Institution"})
    @Path("/all/institution/{username}")
    public Collection<InstitutionProposalDTO> getAllInstitutionProposals(
            @PathParam("username") String username) {
        try {
            Institution institution = em.find(Institution.class, username);
            
            if (institution == null) {
                throw new EntityDoesNotExistsException("Não existe uma instituição com este username");
            }
            
            return toDTOs(institution.getProposals(), InstitutionProposalDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Path("/searchByTitle/{title}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public InstitutionProposalDTO getInstitutionProposalByTitle(@PathParam("title") String title) {
        try {
            InstitutionProposal proposal = (InstitutionProposal) em.createNamedQuery("getInstitutionProposalByTitle")
                    .setParameter("title", title)
                    .getSingleResult();

            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma proposta com esse titulo.");
            }
            return toDTO(proposal, InstitutionProposalDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
  
    @PUT
    @Path("/updateREST/{username}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(@PathParam("username") String username, InstitutionProposalDTO proposalDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            InstitutionProposal proposal = em.find(InstitutionProposal.class, proposalDTO.getId());
            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma proposta com esse ID");
            }
            
            Institution institution = em.find(Institution.class, username);
            if (institution == null) {
                throw new EntityDoesNotExistsException("Não existe uma Instituição com esse ID.");
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
            proposal.setSupervisor(proposalDTO.getSupervisor());
            proposal.setEnumProposalType(InstitutionProposalType.valueOf(proposalDTO.getProposalType()));
            
            proposal.getInstitution().removeProposal(proposal);
            proposal.setInstitution(institution);
                  
            institution.addProposal(proposal);
            em.merge(proposal);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
      
    public void enrollProposal(int intitutionId, int proposalId)
            throws EntityDoesNotExistsException, InstitutionEnrolledException {
        try {

            Institution institution = em.find(Institution.class, intitutionId);
            if (institution == null) {
                throw new EntityDoesNotExistsException("Não existe um professor com esse ID.");
            }

            InstitutionProposal proposal = em.find(InstitutionProposal.class, proposalId);
            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe uma proposta com esse ID.");
            }

            if (institution.getProposals().contains(proposal)) {
                throw new InstitutionEnrolledException("A Instituição já contém essa Proposta.");
            } 
            
            institution.addProposal(proposal);

        } catch (EntityDoesNotExistsException | InstitutionEnrolledException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void unrollProposal(int intitutionId, int proposalId)
            throws EntityDoesNotExistsException, InstitutionEnrolledException {
        try {

            Institution institution = em.find(Institution.class, intitutionId);
            if (institution == null) {
                throw new EntityDoesNotExistsException("Não existe um professor com esse ID.");
            }

            InstitutionProposal proposal = em.find(InstitutionProposal.class, proposalId);
            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe uma proposta com esse ID.");
            }
            
            if (institution.getProposals().contains(proposal)) {
                throw new InstitutionEnrolledException("A Instituição já contém essa Proposta.");
            } 
            
            institution.removeProposal(proposal);

        } catch (EntityDoesNotExistsException | InstitutionEnrolledException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}
