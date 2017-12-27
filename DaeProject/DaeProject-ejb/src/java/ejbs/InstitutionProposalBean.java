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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/institutionProposals")
public class InstitutionProposalBean extends Bean<InstitutionProposal> {
    
    @POST
    @Path("/createREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(InstitutionProposalDTO proposalDTO)
         throws EntityAlreadyExistsException, MyConstraintViolationException, EntityDoesNotExistsException {
        try {
            if (em.find(InstitutionProposal.class, proposalDTO.getId()) != null) {
                throw new EntityAlreadyExistsException("A proposta já existe.");
            }
            
            // just to test create new proposal until authentication is not implemented
            if (proposalDTO.getProponentID()== 0) {
                proposalDTO.setProponentID(10);
            }
            
            Institution institution = em.find(Institution.class, proposalDTO.getProponentID());
            if (institution == null) {
                throw new EntityDoesNotExistsException("Não existe uma Instituição com esse ID.");
            }
            /*
            List<String> bibliography = new LinkedList<>();
            bibliography.add(proposalDTO.getBibliography1());
            bibliography.add(proposalDTO.getBibliography2());
            bibliography.add(proposalDTO.getBibliography3());
            bibliography.add(proposalDTO.getBibliography4());
            bibliography.add(proposalDTO.getBibliography5());
            */
            InstitutionProposal proposal = new InstitutionProposal(
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
                    proposalDTO.getSupervisor(),
                    InstitutionProposalType.valueOf(proposalDTO.getProposalType()),
                    institution
            );
            
            em.persist(proposal);
            
            //enroll this new proposal created to proposals list at Institution
            enrollProposal(proposalDTO.getProponentID(), proposal.getId());
            
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
    @Path("/all/institution/{id}")
    public Collection<InstitutionProposalDTO> getAllInstitutionProposalsFrom(
            @PathParam("id") String idStr) {
        try {
            Institution institution = em.find(Institution.class, Integer.parseInt(idStr));
            
            if (institution == null) {
                throw new EntityDoesNotExistsException("Não existe uma instituição com este ID");
            }
            
            return toDTOs(institution.getProposals(), InstitutionProposalDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
  
    @PUT
    @Path("/updateREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(InstitutionProposalDTO proposalDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            InstitutionProposal proposal = em.find(InstitutionProposal.class, proposalDTO.getId());
            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma proposta com esse ID");
            }
            
            /*List<String> bibliography = new LinkedList<>();
            bibliography.add(proposalDTO.getBibliography1());
            bibliography.add(proposalDTO.getBibliography2());
            bibliography.add(proposalDTO.getBibliography3());
            bibliography.add(proposalDTO.getBibliography4());
            bibliography.add(proposalDTO.getBibliography5());
            */
            proposal.setTitle(proposalDTO.getTitle());
            proposal.setScientificAreas(proposalDTO.getScientificAreas());
            proposal.setObjectives(proposalDTO.getObjectives());
            proposal.setWorkResume(proposalDTO.getWorkResume());
            //bibliography;
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
