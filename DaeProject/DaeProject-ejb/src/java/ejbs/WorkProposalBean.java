/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.WorkProposalDTO;
import entities.Student;
import exceptions.EntityAlreadyExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.StudentEnrolledException;
import exceptions.StudentNotEnrolledException;
import exceptions.Utils;
import entities.InstitutionProposal;
import entities.TeacherProposal;
import entities.WorkProposal;
import entities.WorkProposal.ProposalStatus;
import exceptions.EntityDoesNotExistsException;
import exceptions.ProposalNotInStudentException;
import exceptions.StudentNotInProposalException;
import java.util.Collection;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
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
    @RolesAllowed({"Administrator", "Teacher", "Institution", "Student"})
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
            return toDTOs(em.createNamedQuery("getAllEndedProposals").getResultList(), WorkProposalDTO.class);
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
                //institutionBean.sendEmailAboutProposalTo(institutionProposal.getInstitution().getId(), proposal);
            } else {
                TeacherProposal teacherProposal = (TeacherProposal) proposal;
                //teacherBean.sendEmailAboutProposalTo(teacherProposal.getTeacher().getId(), proposal);
            }

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @Path("/createREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(WorkProposalDTO proposalDTO)
            throws EntityAlreadyExistsException, MyConstraintViolationException {
        System.out.println("ejbs.WorkProposalBean.create() IN");
        try {
            if (em.find(WorkProposal.class, proposalDTO.getId()) != null) {
                throw new EntityAlreadyExistsException("A proposta já existe.");
            }
 
            WorkProposal proposal = new WorkProposal(
                    proposalDTO.getTitle(),
                    proposalDTO.getScientificAreas(),
                    proposalDTO.getObjectives(),
                    proposalDTO.getWorkResume(),
                    proposalDTO.getBibliography1(),
                    proposalDTO.getBibliography2(),
                    proposalDTO.getBibliography3(),
                    proposalDTO.getBibliography4(),
                    proposalDTO.getBibliography5(),
                    proposalDTO.getWorkPlan(),
                    proposalDTO.getWorkLocality(),
                    proposalDTO.getSuccessRequirements(),
                    proposalDTO.getBudget(),
                    proposalDTO.getSupport()
            );

            em.persist(proposal);
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
    @Path("allStudentProposalsRest/{username}")
    public Collection<WorkProposalDTO> getAllStudentWorkProposalsREST(
            @PathParam("username") String username)
            throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, username);

            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum estudante com esse username.");
            }

            return toDTOs(student.getWorkProposalsApply(), WorkProposalDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Path("/searchByTitle/{title}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public WorkProposalDTO getWorkProposalByTitle(@PathParam("title") String title) {
        try {
            WorkProposal proposal = (WorkProposal) em.createNamedQuery("getWorkProposalByTitle")
                    .setParameter("title", title)
                    .getSingleResult();

            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma proposta com esse titulo.");
            }
            return toDTO(proposal, WorkProposalDTO.class);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("enrollStudentRest/{username}/{workProposalId}")
    public void enrollStudent(
            @PathParam("username") String username,
            @PathParam("workProposalId") String workProposalId)
            throws EntityDoesNotExistsException,
            MyConstraintViolationException {
        System.out.println("ejbs.WorkProposalBean.enrollStudent()");
        try {
            enrollStudentToWorkProposal(
                    username,
                    workProposalId
            );

        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("unrollStudentRest/{username}/{workProposalId}")
    public void unrollStudent(
            @PathParam("username") String username,
            @PathParam("workProposalId") String workProposalId)
            throws EntityDoesNotExistsException,
            MyConstraintViolationException {

        try {
            unrollStudentFromWorkProposal(
                    username,
                    workProposalId
            );

        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void enrollStudentToWorkProposal(String username, String idWp)
            throws EntityDoesNotExistsException, MyConstraintViolationException, StudentNotEnrolledException, StudentEnrolledException {
        try {

            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistsException("Não exite nenhum estudante com esse username.");
            }

            WorkProposal workProposal = em.find(WorkProposal.class, Integer.parseInt(idWp));
            if (workProposal == null) {
                throw new EntityDoesNotExistsException("Não exite nenhum Proposta de trabalho com esse ID.");
            }

            if (student.getWorkProposalsApply().contains(workProposal)) {
                throw new StudentNotEnrolledException("O estudante já está inscrito neste Proposta de trabalho.");
            }

            if (workProposal.getStudentsApply().contains(student)) {
                throw new StudentEnrolledException("O estudante já está proposto a este Proposta de trabalho.");
            }

            student.addProposalApply(workProposal);
            workProposal.addStudentApply(student);

        } catch (EntityDoesNotExistsException | StudentNotEnrolledException | StudentEnrolledException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));

        }
    }

    public void unrollStudentFromWorkProposal(String username, String idWp)
            throws EntityDoesNotExistsException, MyConstraintViolationException, ProposalNotInStudentException, StudentNotInProposalException {
        try {

            Student student = em.find(Student.class, username);

            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum estudante com esse username.");
            }
            
            WorkProposal workProposal = em.find(WorkProposal.class, Integer.parseInt(idWp));

            if (workProposal == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma Proposta de Trabalho com esse Id.");
            }
            
            if (!student.getWorkProposalsApply().contains(workProposal)) {
                throw new ProposalNotInStudentException("O estudante não contem essa Proposta de Trabalho.");
            }
            
            if (!workProposal.getStudentsApply().contains(student)) {
                throw new StudentNotInProposalException("A Proposta de Trabalho não contem esse estudante.");
            }
            
            student.removeProposalApply(workProposal);
            workProposal.removeStudentApply(student);

        } catch (EntityDoesNotExistsException | ProposalNotInStudentException | StudentNotInProposalException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }
    }

}
