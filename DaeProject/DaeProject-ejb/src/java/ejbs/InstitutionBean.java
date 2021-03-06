/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.InstitutionDTO;
import entities.Institution;
import entities.PublicProof;
import entities.WorkProposal;
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
@Path("/institutions")
public class InstitutionBean extends Bean<Institution> {

    @EJB
    EmailBean emailBean;

    @POST
    @Path("/createREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(InstitutionDTO institutionDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException, EntityAlreadyExistsException {
        try {
            Institution institution = em.find(Institution.class, institutionDTO.getUsername());

            if (institution != null) {
                throw new EntityAlreadyExistsException("Já existe uma instituição com esse nome.");
            }

            institution = new Institution(
                    institutionDTO.getUsername(),
                    institutionDTO.getPassword(),
                    institutionDTO.getName(),
                    institutionDTO.getEmail(),
                    institutionDTO.getCity(),
                    institutionDTO.getAddress(),
                    institutionDTO.getEnterprise()
            );

            em.persist(institution);

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
    @Path("findInstitutionByEmail/{email}")
    public InstitutionDTO findInstituition(@PathParam("email") String email)
            throws EntityDoesNotExistsException {
        try {

            return toDTO(getInstitutionByEmail(email), InstitutionDTO.class);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("findInstitutionByUsername/{username}")
    public InstitutionDTO findInstitutionByUsername(@PathParam("username") String username)
            throws EntityDoesNotExistsException {
        try {
            Institution institution = (Institution) em.createNamedQuery("getInstitutionByUsername")
                    .setParameter("username", username)
                    .getSingleResult();

            if (institution == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma instituição com esse username.");
            }
            
            return toDTO(institution, InstitutionDTO.class);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Path("/searchByName/{name}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public InstitutionDTO getInstitutionByName(@PathParam("name") String name) {
        try {
            Institution institution = (Institution) em.createNamedQuery("getInstitutionByName")
                    .setParameter("name", name)
                    .getSingleResult();

            if (institution == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma instituição com esse nome.");
            }
            return toDTO(institution, InstitutionDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @Path("/updateREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(InstitutionDTO institutionDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            Institution institution = em.find(Institution.class, institutionDTO.getUsername());
            if (institution == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma instituição com esse nome.");
            }

            institution.setUsername(institutionDTO.getUsername());
            institution.setPassword(institutionDTO.getPassword());
            institution.setName(institutionDTO.getName());
            institution.setEmail(institutionDTO.getEmail());
            institution.setCity(institutionDTO.getCity());
            institution.setAddress(institutionDTO.getAddress());
            institution.setEnterprise(institutionDTO.getEnterprise());
            
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
    @Path("/removeREST/{username}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void remove(@PathParam("username") String username) 
            throws EntityDoesNotExistsException {
        try {
            Institution institution = em.find(Institution.class, username);
            if (institution == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma instituição com esse username.");
            }

            em.remove(institution);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }        
    
    public Institution getInstitutionByEmail(String email)
            throws EntityDoesNotExistsException {
        try {
            Institution instituition = (Institution) em.createNamedQuery("getInstitutionByEmail")
                    .setParameter("email", email)
                    .getSingleResult();

            if (instituition == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma Instituição com esse email.");
            }
            
            return instituition;
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
  
    public void sendEmailAboutProposalTo(int id, WorkProposal proposal) 
            throws MessagingException, EntityDoesNotExistsException {
        try {
            Institution instituition = em.find(Institution.class, id);
            if (instituition == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma instituição com esse nome.");
            }
            
            emailBean.send(
                    instituition.getEmail(),
                    "Assunto",
                    "Olá " + instituition.getName() + ", o estado da sua proposta '"+proposal.getTitle()+"' foi alterado."
            );

        } catch (MessagingException | EntityDoesNotExistsException e) {
            throw e;
        }
    }
    
    public void sendEmailAboutPublicProofTo(int id, PublicProof publicProof) 
            throws MessagingException, EntityDoesNotExistsException {
        try {
            Institution institution = em.find(Institution.class, id);
            if (institution == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma Instituição com esse ID.");
            }
            
            emailBean.send(
                    institution.getEmail(),
                    "Marcação da Prova Pública",
                    "<p>Exmo " + institution.getName() + ", a Prova Pública com Título '"+publicProof.getWorkTitle()+
                    "', está agendada para o dia "+publicProof.getProofDate()+" às "+publicProof.getProofTime()+" horas.</p>" +
                    "<p>Por favor compareça 30 minutos antes de se iniciar a apresentação da mesma.</p>" +
                    "<br/>" +        
                    "<p>Atenciosamente,<br/> Membro da CCP</p>"
            );

        } catch (MessagingException | EntityDoesNotExistsException e) {
            throw e;
        }
    }
}
