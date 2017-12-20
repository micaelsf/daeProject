/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.InstituitionDTO;
import dtos.StudentDTO;
import dtos.TeacherDTO;
import dtos.WorkProposalDTO;
import ejbs.InstituitionBean;
import ejbs.StudentBean;
import ejbs.TeacherBean;
import ejbs.WorkProposalBean;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
public class AdministratorManager {

    private static final Logger logger = Logger.getLogger("web.AdministratorManager");

    @EJB
    private StudentBean studentBean;
    private StudentDTO newStudent;
    private StudentDTO currentStudent;

    @EJB
    private InstituitionBean instituitionBean;
    private InstituitionDTO newInstituition;
    private InstituitionDTO currentInstituition;

    @EJB
    private TeacherBean teacherBean;

    private TeacherDTO newTeacher;
    private TeacherDTO currentTeacher;

    @EJB
    private WorkProposalBean proposalBean;
    private WorkProposalDTO newProposal;    
    private WorkProposalDTO currentProposal;    
    
    private UIComponent component;
    private Client client;

    private final String baseUri = "http://localhost:8080/DaeProject-war/webapi";
    
    private int selectOption;
    
    //baseUri is getting called by "URILookup.getBaseAPI()" -> package utils
    //private final String baseUri = "http://localhost:38105/DaeProject-war/webapi";

    public AdministratorManager() {
        newStudent = new StudentDTO();
        newInstituition = new InstituitionDTO();
        newTeacher = new TeacherDTO();
        newProposal = new WorkProposalDTO();
        client = ClientBuilder.newClient();
    }

    public String createStudent() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/students/createREST")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(newStudent));
            newStudent.reset();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public String updateStudentREST() {
        try {
            client.target(baseUri)
                    .path("/students/updateREST")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentStudent));
            System.out.println("Erro #11111111111 updateRest AdminManager");

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        System.out.println("Erro #123455 updateRest AdminManager");
        return "index?faces-redirect=true";
    }

    public List<StudentDTO> getAllStudentsREST() {
        List<StudentDTO> returnedStudents;
        try {
            returnedStudents = client.target(URILookup.getBaseAPI())
                    .path("/students/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<StudentDTO>>() {
                    });
            System.out.println(returnedStudents);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        return returnedStudents;
    }

    public String updateStudentsREST() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/students/updateREST")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentStudent));
            
         } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
       
        return "index?faces-redirect=true";
    }
    
    public void removeStudent(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("id");
            int id = Integer.parseInt(param.getValue().toString());
            
            studentBean.remove(id);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }

    //Instituition
    public String createInstituition() {
        try {
            instituitionBean.create(
                    newInstituition.getId(),
                    newInstituition.getPassword(),
                    newInstituition.getName(),
                    newInstituition.getEmail());
            newInstituition.reset();
        } catch (EntityAlreadyExistsException | EntityDoesNotExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            return null;
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }
    
    public String updateInstituitionREST() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/instituitions/updateREST")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentInstituition));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<InstituitionDTO> getAllInstituitionsREST() {
        List<InstituitionDTO> returnedInstituitions;
        try {
            returnedInstituitions = client.target(URILookup.getBaseAPI())
                    .path("/instituitions/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<InstituitionDTO>>() {
                    });
            System.out.println(returnedInstituitions);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        return returnedInstituitions;
    }

    public void removeInstituition(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("id");
            int id = Integer.parseInt(param.getValue().toString());
            instituitionBean.remove(id);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }

    /* TEACHERS  */
    public String createTeacher() {
        try {
            teacherBean.create(
                    newTeacher.getId(),
                    newTeacher.getPassword(),
                    newTeacher.getName(),
                    newTeacher.getEmail());
            newTeacher.reset();
        } catch (EntityAlreadyExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            return null;
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter! RIGHT", component, logger);
            return null;
        }

        return "/admin/index?faces-redirect=true";
    }

     public String updateTeacherREST() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/teachers/updateREST")
                    .path(currentTeacher.getId() + "")
                    .path(currentTeacher.getName())
                    .path(currentTeacher.getEmail())
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(this));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!er", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public void removeTeacher(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("id");
            int id = Integer.parseInt(param.getValue().toString());

            teacherBean.remove(id);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (NumberFormatException e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
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
             FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
             return null;
         }
         return returnedTeachers;
     }
    
    public String createWorkProposal() {
        try {
            proposalBean.create(
                    newProposal.getId(),
                    newProposal.getTitle(),
                    newProposal.getScientificAreas(),                    
                    newProposal.getObjectives(),
                    newProposal.getStatus());
            newProposal.reset();
        } catch (EntityAlreadyExistsException | EntityDoesNotExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            return null;
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
            return null;
        }

        return "admin/proposal/index?faces-redirect=true";
    }
    
    public List<WorkProposalDTO> getAllWorkProposalsREST() {
        List<WorkProposalDTO> returnedProposals;
        try {
            returnedProposals = client.target(URILookup.getBaseAPI())
                    .path("/proposals/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<WorkProposalDTO>>() {
            });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        return returnedProposals;
    }
    
    public String updateWorkProposalREST() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/proposals/updateREST")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentProposal));
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }
    
    public void updateStatusWorkProposalREST(ActionEvent event) {
        try {
            UIParameter paramAccept = (UIParameter) event.getComponent().findComponent("acceptProposalID");            
            UIParameter paramReject = (UIParameter) event.getComponent().findComponent("rejectProposalID");
            int id = 0, status = 3;
            
            if (paramAccept != null) {
                id = Integer.parseInt(paramAccept.getValue().toString());
                status = 1;
            }
            
            if (paramReject != null) {
                id = Integer.parseInt(paramReject.getValue().toString());
                status = 2;
            }
            
            client.target(URILookup.getBaseAPI())
                    .path("/proposals/updateREST")
                    .path(id + "")
                    .path(status + "")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(""));
            
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        } 
    }
    
    public void removeProposal(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("id");
            int id = Integer.parseInt(param.getValue().toString());
            
            client.target(URILookup.getBaseAPI())
                    .path("/proposals/updateREST")
                    .path(id + "")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(""));
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    public int getSelectOption() {
        return selectOption;
    }

    public void setSelectOption(int selectOption) {
        this.selectOption = selectOption;
    }
    
    /////////////// GETTERS & SETTERS ///////////////// 
    //instituition
    public InstituitionDTO getCurrentInstituition() {
        return currentInstituition;
    }

    public void setCurrentInstituition(InstituitionDTO currentInstituition) {
        this.currentInstituition = currentInstituition;
    }

    public InstituitionDTO getNewInstituition() {
        return newInstituition;
    }

    public void setNewInstituition(InstituitionDTO newInstituition) {
        this.newInstituition = newInstituition;
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
