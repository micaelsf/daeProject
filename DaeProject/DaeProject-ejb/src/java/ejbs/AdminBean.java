package ejbs;

import dtos.AdminDTO;
import dtos.TeacherDTO;
import entities.Admin;
import entities.Teacher;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
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
@Path("/admins")
public class AdminBean extends Bean<Admin> {

    @EJB
    EmailBean emailBean;

    @POST
    @Path("/createREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(AdminDTO adminDTO)
            throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            Admin admin = em.find(Admin.class, adminDTO.getId());
            if (admin != null) {
                throw new EntityAlreadyExistsException("Já existe um admin com esse nome.");
            }

            admin = new Admin(
                    adminDTO.getPassword(),
                    adminDTO.getName(),
                    adminDTO.getEmail(),
                    adminDTO.getCity(),
                    adminDTO.getAddress()
            );

            em.persist(admin);

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
    public Collection<AdminDTO> getAllAdmins() {
        try {
            return getAll(AdminDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
     @Override
    protected Collection<Admin> getAll() {
        return em.createNamedQuery("getAllAdmins").getResultList();
    }
    

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("findAdmin/{id}")
    public AdminDTO findAdmin(@PathParam("id") String id)
            throws EntityDoesNotExistsException {
        try {
            Admin admin = em.find(Admin.class, id);

            if (admin == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum admin com esse nome.");
            }
            return toDTO(admin, AdminDTO.class);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @Path("/updateREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(AdminDTO adminDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            Admin admin = em.find(Admin.class, adminDTO.getId());
            if (admin == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum admin com esse nome.");
            }

            admin.setPassword(adminDTO.getPassword());
            admin.setName(adminDTO.getName());
            admin.setEmail(adminDTO.getEmail());
            admin.setCity(adminDTO.getCity());
            admin.setAddress(adminDTO.getAddress());
            em.merge(admin);

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
            Admin admin = em.find(Admin.class, Integer.parseInt(id));
            if (admin == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum admin com esse id.");
            }

            em.remove(admin);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}