/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.WorkProposalDTO;
import entities.InstitutionProposal;
import entities.TeacherProposal;
import entities.WorkProposal;
import entities.WorkProposal.ProposalStatus;
import exceptions.EntityDoesNotExistsException;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/proposals")
public class WorkProposalBean extends Bean<WorkProposal> {
    
    @EJB
    InstitutionBean institutionBean;
    
    @EJB
    TeacherBean teacherBean;
    
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
    
    @GET 
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
    @Path("allEndedProposals")
    public Collection<WorkProposalDTO> getAllEndedProposals() {
        try {
            return em.createNamedQuery("getAllEndedProposals").getResultList();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @POST 
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
    @Path("updateProposalStatusREST/{id}/{rejectReason}/{comments}/{status}")
    public void acceptProposal(
            @PathParam("id") String id,
            @PathParam("rejectReason") String rejectReason,
            @PathParam("comments") String comments,
            @PathParam("status") String status
    ) 
        throws EntityDoesNotExistsException {
        try {

            WorkProposal proposal = em.find(WorkProposal.class, Integer.parseInt(id));
            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma proposta com esse ID.");
            }
            
            proposal.setRejectReason(rejectReason);
            proposal.setComments(comments);
            proposal.setStatus(ProposalStatus.valueOf(status));
            em.merge(proposal);
            
            // send notification email
            if (proposal instanceof InstitutionProposal) {
                InstitutionProposal institutionProposal = (InstitutionProposal) proposal;
                //institutionBean.sendEmailToInstitution(institutionProposal.getInstitution().getId());
            } else {
                TeacherProposal teacherProposal = (TeacherProposal) proposal;
                //teacherBean.sendEmailToTeacher(teacherProposal.getTeacher().getId());
            }
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
