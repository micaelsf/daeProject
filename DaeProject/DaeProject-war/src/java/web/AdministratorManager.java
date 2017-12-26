/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.InstitutionDTO;
import dtos.StudentDTO;
import dtos.TeacherDTO;
import dtos.WorkProposalDTO;
import entities.WorkProposal.ProposalStatus;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import utils.URILookup;

@ManagedBean
@SessionScoped
public class AdministratorManager implements Serializable {

    private static final Logger logger = Logger.getLogger("web.AdministratorManager");

    private StudentDTO newStudent;
    private StudentDTO currentStudent;

    private InstitutionDTO newInstitution;
    private InstitutionDTO currentInstitution;

    private TeacherDTO newTeacher;
    private TeacherDTO currentTeacher;

    private WorkProposalDTO newProposal;
    private WorkProposalDTO currentProposal;

    private UIComponent component;
    private Client client;

    private int selectOption;

    //baseUri is getting called by "URILookup.getBaseAPI()" -> package utils
    //private final String baseUri = "http://localhost:38105/DaeProject-war/webapi";
    //private final String baseUri = "http://localhost:8080/DaeProject-war/webapi";
    public AdministratorManager() {
        newStudent = new StudentDTO();
        newInstitution = new InstitutionDTO();
        newTeacher = new TeacherDTO();
        newProposal = new WorkProposalDTO();
        client = ClientBuilder.newClient();
    }

    public String createStudent() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/students/createREST")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newStudent));
            newStudent.reset();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public String updateStudentREST() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/students/updateREST")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentStudent));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<StudentDTO> getAllStudentsREST() {
        List<StudentDTO> returnedStudents;
        try {
            returnedStudents = client.target(URILookup.getBaseAPI())
                    // returnedStudents = client.target(baseUri)
                    .path("/students/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<StudentDTO>>() {
                    });
            System.out.println(returnedStudents);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedStudents;
    }

    public String removeStudent(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("id");
            int id = Integer.parseInt(param.getValue().toString());
 
            client.target(URILookup.getBaseAPI())
                    .path("/students/removeREST")
                    .path(id + "")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    //Institution
    public String createInstitution() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/institutions/createREST")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newInstitution)
                    );

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public String updateInstitutionREST() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/institutions/updateREST")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentInstitution));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<InstitutionDTO> getAllInstitutionsREST() {
        List<InstitutionDTO> returnedInstitutions;
        try {
            returnedInstitutions = client.target(URILookup.getBaseAPI())
                    .path("/institutions/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<InstitutionDTO>>() {
                    });
            System.out.println(returnedInstitutions);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedInstitutions;
    }

    public String removeInstitution(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("id");
            int id = Integer.parseInt(param.getValue().toString());
            client.target(URILookup.getBaseAPI())
                    .path("/institutions/removeREST")
                    .path(id + "")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    /* TEACHERS  */
    public String createTeacher() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/teachers/createREST")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newTeacher)
                    );

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public String updateTeacherREST() {
        try {
            // client.target(baseUri)
            client.target(URILookup.getBaseAPI())
                    .path("/teachers/updateREST")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentTeacher));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public String removeTeacher(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("id");
            int id = Integer.parseInt(param.getValue().toString());

            client.target(URILookup.getBaseAPI())
                    .path("/teachers/removeREST")
                    .path(id + "")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));
        } catch (NumberFormatException e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<TeacherDTO> getAllTeachersREST() {
        List<TeacherDTO> returnedTeachers;
        try {
            returnedTeachers = client.target(URILookup.getBaseAPI())
                    .path("/teachers/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<TeacherDTO>>() {
                    });
            System.out.println(returnedTeachers);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedTeachers;
    }

    /* PROPOSAL */
    public List<WorkProposalDTO> getAllWorkProposalsREST() {
        List<WorkProposalDTO> returnedProposals;
        try {
            returnedProposals = client.target(URILookup.getBaseAPI())
                    .path("/proposals/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<WorkProposalDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedProposals;
    }

    public void updateStatusWorkProposalREST(ActionEvent event) {
        try {
            UIParameter paramAccept = (UIParameter) event.getComponent().findComponent("acceptProposalID");
            UIParameter paramReject = (UIParameter) event.getComponent().findComponent("rejectProposalID");
            int id = 0; 
            ProposalStatus status = ProposalStatus.Pendente;

            if (paramAccept != null) {
                id = Integer.parseInt(paramAccept.getValue().toString());
                status = ProposalStatus.Aceite;;
            }

            if (paramReject != null) {
                id = Integer.parseInt(paramReject.getValue().toString());
                status = ProposalStatus.NãoAceite;
            }

            client.target(URILookup.getBaseAPI())
                    .path("/proposals/updateREST")
                    .path(id + "")
                    .path(status + "")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(""));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
        }
    }

    public int getSelectOption() {
        return selectOption;
    }

    public void setSelectOption(int selectOption) {
        this.selectOption = selectOption;
    }

    /////////////// GETTERS & SETTERS ///////////////// 
    //institution
    public InstitutionDTO getCurrentInstitution() {
        return currentInstitution;
    }

    public void setCurrentInstitution(InstitutionDTO currentInstitution) {
        this.currentInstitution = currentInstitution;
    }

    public InstitutionDTO getNewInstitution() {
        return newInstitution;
    }

    public void setNewInstitution(InstitutionDTO newInstitution) {
        this.newInstitution = newInstitution;
    }

    //student
    public StudentDTO getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(StudentDTO currentStudent) {
        this.currentStudent = currentStudent;
    }

    public StudentDTO getNewStudent() {
        return newStudent;
    }

    public void setNewStudent(StudentDTO newStudent) {
        this.newStudent = newStudent;
    }

    /* TEACHERS GETTER & SETTER */
    public TeacherDTO getCurrentTeacher() {
        return currentTeacher;
    }

    public void setCurrentTeacher(TeacherDTO currentTeacher) {
        this.currentTeacher = currentTeacher;
    }

    public TeacherDTO getNewTeacher() {
        return newTeacher;
    }

    public void setNewTeacher(TeacherDTO newTeacher) {
        this.newTeacher = newTeacher;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public WorkProposalDTO getNewProposal() {
        return newProposal;
    }

    public void setNewProposal(WorkProposalDTO newProposal) {
        this.newProposal = newProposal;
    }

    public WorkProposalDTO getCurrentProposal() {
        return currentProposal;
    }

    public void setCurrentProposal(WorkProposalDTO currentProposal) {
        this.currentProposal = currentProposal;
    }
}
