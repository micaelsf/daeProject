/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.InstituitionDTO;
import entities.Instituition;
import entities.UserGroup.GROUP;
import exceptions.EntityAlreadyExistsException;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/instituitions")
public class InstituitionBean extends Bean<Instituition> implements Serializable  {

    @EJB
    EmailBean emailBean;

    @PersistenceContext
    private EntityManager em;

    public void create(int id, String password, String name, String email)
            throws EntityAlreadyExistsException, EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            if (em.find(Instituition.class, id) != null) {
                throw new EntityAlreadyExistsException("Instituition already exists.");
            }

            em.persist(new Instituition(password, GROUP.Instituition, name, email));

        } catch (EntityAlreadyExistsException e) {
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
    public Collection<InstituitionDTO> getAllInstituitions() {
        try {
            return getAll(InstituitionDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @Override
    protected Collection<Instituition> getAll() {
        return em.createNamedQuery("getAllInstituitions").getResultList();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("findInstituition/{id}")
    public InstituitionDTO findInstituition(@PathParam("id") String id)
            throws EntityDoesNotExistsException {
        try {
            Instituition instituition = em.find(Instituition.class, id);

            if (instituition == null) {
                throw new EntityDoesNotExistsException("There is no Instituition with such name.");
            }
            
            return toDTO(instituition, InstituitionDTO.class);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @Path("/updateREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(InstituitionDTO instituitionDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            Instituition instituition = em.find(Instituition.class, instituitionDTO.getId());
            if (instituition == null) {
                throw new EntityDoesNotExistsException("There is no Instituition with that name.");
            }

            instituition.getPassword();
            instituition.getName();
            instituition.getEmail();
            em.merge(instituition);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int id)
            throws EntityDoesNotExistsException {
        try {
            Instituition instituition = em.find(Instituition.class, id);
            if (instituition == null) {
                throw new EntityDoesNotExistsException("There is no Instituition with that name.");
            }

            em.remove(instituition);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void sendEmailToInstituition(int id) throws MessagingException, EntityDoesNotExistsException {
        try {
            Instituition instituition = em.find(Instituition.class, id);
            if (instituition == null) {
                throw new EntityDoesNotExistsException("There is no instituition with that name.");
            }

            emailBean.send(
                    instituition.getEmail(),
                    "Subject",
                    "Hello " + instituition.getName());

        } catch (MessagingException | EntityDoesNotExistsException e) {
            throw e;
        }
    }

}
