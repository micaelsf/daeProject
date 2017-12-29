/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.StudentDTO;
import dtos.TeacherProposalDTO;
import dtos.WorkProposalDTO;
import entities.Student;
import entities.TeacherProposal;
import entities.WorkProposal;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.StudentEnrolledException;
import exceptions.StudentNotEnrolledException;
import exceptions.Utils;
import entities.InstitutionProposal;
import entities.TeacherProposal;
import entities.WorkProposal;
import entities.WorkProposal.ProposalStatus;
import exceptions.EntityDoesNotExistsException;
import java.util.Collection;
import javax.ejb.EJB;
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
            /*
            List<String> bibliography = new LinkedList<>();
            bibliography.add(proposalDTO.getBibliography1());
            bibliography.add(proposalDTO.getBibliography2());
            bibliography.add(proposalDTO.getBibliography3());
            bibliography.add(proposalDTO.getBibliography4());
            bibliography.add(proposalDTO.getBibliography5());
             */
            WorkProposal proposal = new WorkProposal(
                    proposalDTO.getTitle(),
                    proposalDTO.getScientificAreas(),
                    proposalDTO.getObjectives(),
                    proposalDTO.getWorkResume(),
                    //bibliography,
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
    @Path("allStudentProposals/{id}")
    public Collection<WorkProposal> getAllStudentWorkProposalsREST(
            @PathParam("studentId") String studentId)
            throws EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, Integer.valueOf(studentId));

            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe nenhum estudante com esse id.");
            }

            return student.getWorkProposalsApply();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @Path("enrollStudentRest/{studentId}/{workProposalId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void enrollStudent(
            @PathParam("studentId") String studentId,
            @PathParam("workProposalId") String workProposalId)
            throws EntityDoesNotExistsException,
            MyConstraintViolationException {
        System.out.println("enrollStudentREST studentId:" + studentId + " workProposalId: " + workProposalId);

        try {
            enrollStudentToWorkProposal(
                    Integer.valueOf(studentId),
                    Integer.valueOf(workProposalId)
            );
        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @Path("unrollStudentRest/{studentId}/{workProposalId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void unrollStudent(
            @PathParam("studentId") String studentId,
            @PathParam("workProposalId") String workProposalId)
            throws EntityDoesNotExistsException,
            MyConstraintViolationException {
        try {
            unrollStudentFromWorkProposal(
                    Integer.valueOf(studentId),
                    Integer.valueOf(workProposalId)
            );
        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void enrollStudentToWorkProposal(int idStudent, int idWorkProposal)
            throws EntityDoesNotExistsException, MyConstraintViolationException, StudentNotEnrolledException, StudentEnrolledException {
        try {

            System.out.println("enrollStudentREST studentId:" + idStudent + " workProposalId: " + idWorkProposal);

            Student student = em.find(Student.class, idStudent);
            if (student == null) {
                throw new EntityDoesNotExistsException("Não exite nenhum estudante com esse ID.");
            }

            WorkProposal workProposal = em.find(WorkProposal.class, idWorkProposal);
            if (workProposal == null) {
                throw new EntityDoesNotExistsException("Não exite nenhum estudante com esse ID.");
            }

            if (!student.getWorkProposalsApply().contains(workProposal)) {
                throw new StudentNotEnrolledException("O estudante não tem essa Proposta.");
            }

            if (workProposal.getStudentsApply().contains(student)) {
                throw new StudentEnrolledException("Student is already enrolled in that subject.");
            }

            student.addProposalApply(workProposal);
            workProposal.addStudentApply(student);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));

        }
    }

    public void unrollStudentFromWorkProposal(int idStudent, int idWorkProposal)
            throws EntityDoesNotExistsException, MyConstraintViolationException, StudentNotEnrolledException {
        try {
            Student student = em.find(Student.class, idStudent);
            if (student == null) {
                throw new EntityDoesNotExistsException("Não exite nenhum estudante com esse ID.");
            }

            WorkProposal workProposal = em.find(WorkProposal.class, idWorkProposal);
            if (workProposal == null) {
                throw new EntityDoesNotExistsException("Não exite nenhuma proposta com esse ID.");
            }

            if (!workProposal.getStudentsApply().contains(student)) {
                throw new StudentNotEnrolledException();
            }

            student.removeProposalApply(workProposal);
            workProposal.removeStudentApply(student);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }
    }

}
