/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.InstituitionDTO;
import entities.Instituition;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/instituition")
public class InstituitionBean {

    @PersistenceContext
    private EntityManager em;

    public void create(int id, String password, String name, String email, String instituitionNumber)
         throws EntityAlreadyExistsException, EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            if (em.find(Instituition.class, id) != null) {
                throw new EntityAlreadyExistsException("Instituition already exists.");
            }

            Instituition instituition = new Instituition(password, name, email, instituitionNumber);
            em.persist(instituition);
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
    public List<InstituitionDTO> getAll() {
        try {
            List<Instituition> instituitions = (List<Instituition>) em.createNamedQuery("getAllInstituitions").getResultList();
            return instituitionsToDTOs(instituitions);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    List<InstituitionDTO> instituitionsToDTOs(List<Instituition> instituitions) {
        List<InstituitionDTO> dtos = new ArrayList<>();
        for (Instituition s : instituitions) {
            dtos.add(instituitionToDTO(s));
        }
        return dtos;
    }
    
    InstituitionDTO instituitionToDTO(Instituition instituition) {
        return new InstituitionDTO(
                instituition.getId(),
                instituition.getInstituitionNumber(),
                null,
                instituition.getName(),
                instituition.getEmail());
    }
}
