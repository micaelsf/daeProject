/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.InstitutionDTO;
import entities.Institution;
import entities.Teacher;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.io.Serializable;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@Path("/institutions")
public class InstitutionBean extends Bean<Institution> {

    @EJB
    EmailBean emailBean;

    @POST
    @Path("/createREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(InstitutionDTO instituitionDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            Institution institution = em.find(Institution.class, instituitionDTO.getId());
            if (institution != null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma instituição com esse nome.");
            }

            institution = new Institution(
                    instituitionDTO.getPassword(),
                    instituitionDTO.getName(),
                    instituitionDTO.getEmail()
            );

            em.persist(institution);

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
    public Collection<InstitutionDTO> getAllInstitutions() {
        try {
            return getAll(InstitutionDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @Override
    protected Collection<Institution> getAll() {
        return em.createNamedQuery("getAllInstitutions").getResultList();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("findInstitution/{id}")
    public InstitutionDTO findInstituition(@PathParam("id") String id)
            throws EntityDoesNotExistsException {
        try {
            Institution institution = em.find(Institution.class, id);

            if (institution == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma instituição com esse nome.");
            }
            
            return toDTO(institution, InstitutionDTO.class);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @Path("/updateREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(InstitutionDTO instituitionDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            Institution institution = em.find(Institution.class, instituitionDTO.getId());
            if (institution == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma instituição com esse nome.");
            }

            institution.getPassword();
            institution.getName();
            institution.getEmail();
            em.merge(institution);

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
            Institution institution = em.find(Institution.class, Integer.parseInt(id));
            if (institution == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma instituição com esse id.");
            }

            em.remove(institution);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void sendEmailToInstitution(int id) throws MessagingException, EntityDoesNotExistsException {
        try {
            Institution instituition = em.find(Institution.class, id);
            if (instituition == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma instituição com esse nome.");
            }

            emailBean.send(
                    instituition.getEmail(),
                    "Assunto",
                    "Olá " + instituition.getName());

        } catch (MessagingException | EntityDoesNotExistsException e) {
            throw e;
        }
    }

}