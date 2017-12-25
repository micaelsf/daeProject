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
import java.io.Serializable;
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
@Path("/proposals")
public class WorkProposalBean extends Bean<WorkProposal> {
    
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
}
