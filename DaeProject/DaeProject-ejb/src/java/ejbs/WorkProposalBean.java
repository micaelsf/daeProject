/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.StudentDTO;
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
import javax.mail.MessagingException;
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

    @EJB
    EmailBean emailBean;

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
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("allStudentInProposalRest/{id}")
    public Collection<StudentDTO> getAllStudentInWorkProposalREST(
            @PathParam("id") String id)
            throws EntityDoesNotExistsException {
        try {
            WorkProposal proposal = em.find(WorkProposal.class, Integer.parseInt(id));

            if (proposal == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma proposta com esse id.");
            }
            return toDTOs(proposal.getStudentsApply(), StudentDTO.class);
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

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("workProposalAcceptApplianceRest/{username}/{workProposalId}")
    public void workProposalAcceptAppliance(
            @PathParam("username") String username,
            @PathParam("workProposalId") String idWp)
            throws EntityDoesNotExistsException,
            MyConstraintViolationException {
        System.out.println("WorkProposalBean with username: " + username + " and workporposal id: " + idWp);
        try {
            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistsException("Não exite nenhum estudante com esse username!");
            }

            WorkProposal workProposal = em.find(WorkProposal.class, Integer.parseInt(idWp));
            if (workProposal == null) {
                throw new EntityDoesNotExistsException("Não exite nenhum Proposta de trabalho com esse ID!");
            }

            if (!workProposal.getStudentsApply().contains(student)) {
                throw new StudentEnrolledException("O estudante não está está inscrito nesta Proposta de Trabalho!");
            }

            if (!student.getWorkProposalsApply().contains(workProposal)) {
                throw new StudentNotEnrolledException("A Proposta de Trabalho não tem este estudante inscrito!");
            }

            String studentRefused = "";

            if (workProposal.getStudent() != null && !(workProposal.getStudent().equals(student))) {
                studentRefused = workProposal.getStudent().getUsername();
                workProposal.getStudent().setWorkProposal(null);
                //mail
                sendEmailAcceptedProposalChangedTo(student.getUsername(), workProposal);
            }

            if (workProposal.getStudent().equals(student)) {
                student.setWorkProposal(workProposal);
                workProposal.setStudent(student);
                System.out.println("workProposal.setStudent(student): " + workProposal.getStudent().getUsername());
                //send e-mail ao estudante aceite para a execução do trabalho
                sendEmailAcceptProposalTo(student.getUsername(), workProposal);

                if (workProposal.getStudentsApply().size() > 1) {
                    for (Student std : workProposal.getStudentsApply()) {
                        //verifica se o estudante não é o estudante aceite
                        // e verifica se o estudante não foi o estudante anteriormente revogado que assim iria receber 2 e-mails
                        if (!student.equals(std) && !std.getUsername().equals(studentRefused)) {
                            //send e-mail aos estudantes candidatos não aceites para a execução do trabalho
                            sendEmailNotAcceptedProposalTo(std.getUsername(), workProposal);
                        }
                    }
                }

            }

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Path("getAcceptedStudentRest/{workProposalId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getAcceptedStudent(
            @PathParam("workProposalId") String idWp)
            throws EntityDoesNotExistsException,
            MyConstraintViolationException {

        try {
            WorkProposal workProposal = em.find(WorkProposal.class, Integer.parseInt(idWp));
            if (workProposal == null) {
                throw new EntityDoesNotExistsException("Não exite nenhum Proposta de trabalho com esse ID.");
            }

            if (workProposal.getStudent() == null) {
                return "noStudent";
            }

            return workProposal.getStudent().getUsername();

        } catch (EntityDoesNotExistsException e) {
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
                throw new StudentNotEnrolledException("O estudante já está inscrito nesta Proposta de Trabalho.");
            }

            if (workProposal.getStudentsApply().contains(student)) {
                throw new StudentEnrolledException("O estudante já está proposto a esta Proposta de trabalho.");
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

    public void sendEmailAcceptProposalTo(String username, WorkProposal proposal)
            throws MessagingException, EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma instituição com esse nome.");
            }

            emailBean.send(
                    student.getEmail(),
                    "Candidatura aceite!",
                    "Olá " + student.getName() + ", <br/> <br/> A sua candidatura à '" + proposal.getTitle() + "' foi aceite. </p> <br/>"
                    + "<p>Atenciosamente,<br/> Membro da CCP</p>"
            );

        } catch (MessagingException | EntityDoesNotExistsException e) {
            throw e;
        }
    }

    public void sendEmailNotAcceptedProposalTo(String username, WorkProposal proposal)
            throws MessagingException, EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma instituição com esse nome.");
            }

            emailBean.send(
                    student.getEmail(),
                    "Candidatura à " + proposal.getTitle(),
                    "Olá " + student.getName() + ", <br/> <br/> A sua candidatura à '" + proposal.getTitle() + "' não foi aceite. <br/>"
                    + "Caso pretenda esclarecimentos adicionais contacte a equipa docente! </p> <br/>"
                    + "<p>Atenciosamente,<br/> Membro da CCP</p>"
            );

        } catch (MessagingException | EntityDoesNotExistsException e) {
            throw e;
        }
    }

    public void sendEmailAcceptedProposalChangedTo(String username, WorkProposal proposal)
            throws MessagingException, EntityDoesNotExistsException {
        try {
            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistsException("Não existe nenhuma instituição com esse nome.");
            }

            emailBean.send(
                    student.getEmail(),
                    "Candidatura à " + proposal.getTitle(),
                    "Olá " + student.getName() + ", <br/> <br/> A sua candidatura à '" + proposal.getTitle() + "' foi revogada. <br/>"
                    + "Caso pretenda esclarecimentos adicionais contacte a equipa docente! </p> <br/>"
                    + "<p>Atenciosamente,<br/> Membro da CCP</p>"
            );

        } catch (MessagingException | EntityDoesNotExistsException e) {
            throw e;
        }
    }
}
