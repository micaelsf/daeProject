/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.InstituitionDTO;
import entities.Instituition;
import entities.Subject;
import entities.UserGroup.GROUP;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/instituitions")
public class InstituitionBean extends Bean<Instituition> {

    @PersistenceContext
    private EntityManager em;

    public void create(int id, String password, String name, String email)
            throws EntityAlreadyExistsException, EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            if (em.find(Instituition.class, id) != null) {
                throw new EntityAlreadyExistsException("Instituition already exists.");
            }

            em.persist(new Instituition(password, GROUP.Instituition ,  name, email));

        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    /*  
    public void update(String password, String name, String email)
        throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            Instituition instituition = em.find(Instituition.class, name);
            if (instituition == null) {
                throw new EntityDoesNotExistsException("There is no instituition with that username.");
            }

            instituition.setPassword(password);
            instituition.setName(name);
            instituition.setEmail(email);
            em.merge(instituition);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
     */

    @PUT
    @Path("/updateREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(InstituitionDTO instituitionDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {

            Instituition instituition = em.find(Instituition.class, instituitionDTO.getId());
            if (instituition == null) {
                throw new EntityDoesNotExistsException("There is no instituition with that name.");
            }

            instituition.setPassword(instituitionDTO.getPassword());
            instituition.setName(instituitionDTO.getName());
            instituition.setEmail(instituitionDTO.getEmail());
            em.merge(instituition);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

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
 

    public void remove(int id)
            throws EntityDoesNotExistsException {
        try {
            Instituition instituition = em.find(Instituition.class, id);
            if (instituition == null) {
                throw new EntityDoesNotExistsException("There is no instituition with that username.");
            }

            em.remove(instituition);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
/*
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
                null,
                instituition.getName(),
                instituition.getEmail()
        );
    }

*/
}
