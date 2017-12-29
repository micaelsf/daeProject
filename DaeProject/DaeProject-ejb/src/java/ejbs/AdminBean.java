package ejbs;

import dtos.AdminDTO;
import entities.Admin;
import entities.PublicProof;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.Collection;
import javax.annotation.security.RolesAllowed;
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
            Admin admin = em.find(Admin.class, adminDTO.getUsername());
            if (admin != null) {
                throw new EntityAlreadyExistsException("Já existe um admin com esse nome.");
            }

            admin = new Admin(
                    adminDTO.getUsername(),
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
    @RolesAllowed({"Administrator"})
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
    @Path("findAdminByEmail/{email}")
    public AdminDTO findAdmin(@PathParam("email") String email)
            throws EntityDoesNotExistsException {
        try {
            return toDTO(getAdminByEmail(email), AdminDTO.class);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("findAdminByUsername/{username}")
    public AdminDTO findAdminByUsername(@PathParam("username") String username)
            throws EntityDoesNotExistsException {
        try {
            Admin admin = (Admin) em.createNamedQuery("getAdminByUsername")
                    .setParameter("username", username)
                    .getSingleResult();

            if (admin == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum administrador com esse username.");
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
            Admin admin = em.find(Admin.class, adminDTO.getUsername());
            if (admin == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum admin com esse nome.");
            }

            admin.setUsername(adminDTO.getUsername());
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
    @Path("/removeREST/{username}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void remove(@PathParam("username") String username) 
            throws EntityDoesNotExistsException {
        try {
            Admin admin = em.find(Admin.class, username);
            if (admin == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum admin com esse username.");
            }

            em.remove(admin);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
        
    public Admin getAdminByEmail(String email)
            throws EntityDoesNotExistsException {
        try {
            Admin admin = (Admin) em.createNamedQuery("getAdminByEmail")
                    .setParameter("email", email)
                    .getSingleResult();

            if (admin == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum admin com esse email.");
            }
            
            return admin;
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void sendEmailAboutPublicProofTo(String email, String name, PublicProof publicProof) 
            throws MessagingException {
        try {

            emailBean.send(
                    email,
                    "Marcação da Prova Pública",
                    "<p>Exmo " + name + ", a Prova Pública com Título '"+publicProof.getWorkTitle()+
                    "', está agendada para o dia "+publicProof.getProofDate()+" às "+publicProof.getProofTime()+" horas.</p>" +
                    "<p>Por favor compareça 30 minutos antes de se iniciar a apresentação da mesma.</p>" +
                    "<br/>" +        
                    "<p>Atenciosamente,<br/> Membro da CCP</p>"
            );

        } catch (MessagingException e) {
            throw e;
        }
    }
}
