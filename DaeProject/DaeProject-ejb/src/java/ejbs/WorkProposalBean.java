/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.StudentDTO;
import dtos.WorkProposalDTO;
import entities.Student;
import entities.WorkProposal;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.StudentEnrolledException;
import exceptions.StudentNotEnrolledException;
import exceptions.Utils;
import java.util.Collection;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/proposals")
public class WorkProposalBean extends Bean<WorkProposal> {

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

    @PUT
    @Path("enrollStudent/{studentId}/{workProposalId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void ennrollStudentREST(
            @PathParam("studentId") String studentId,
            @PathParam("workProposalId") String workProposalId)
            throws EntityDoesNotExistsException,
            MyConstraintViolationException {
        System.out.println("enrollStudentREST studentId:" + studentId + " workProposalId: " + workProposalId);
        
        try {
            updateEnrollement(
                    Integer.valueOf(studentId),
                    Integer.valueOf(workProposalId)
            );
        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @Path("unrollStudent/{studentId}/{workProposalId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void unrollStudentREST(
            @PathParam("studentId") String studentId,
            @PathParam("workProposalId") String workProposalId)
            throws EntityDoesNotExistsException,
            MyConstraintViolationException {
        try {
            updateUnrollement(
                    Integer.valueOf(studentId),
                    Integer.valueOf(workProposalId)
            );
        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void updateEnrollement(int idStudent, int idWorkProposal)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
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
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void updateUnrollement(int idStudent, int idWorkProposal)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            Student student = em.find(Student.class, idStudent);
            if (student == null) {
                throw new EntityDoesNotExistsException("Não exite nenhum estudante com esse ID.");
            }

            WorkProposal workProposal = em.find(WorkProposal.class, idWorkProposal);
            if (workProposal == null) {
                throw new EntityDoesNotExistsException("Não exite nenhum estudante com esse ID.");
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
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}
