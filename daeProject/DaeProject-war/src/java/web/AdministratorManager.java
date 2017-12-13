/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.InstituitionDTO;
import dtos.StudentDTO;
import ejbs.InstituitionBean;
import ejbs.StudentBean;
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

    private UIComponent component;

    private Client client;
    private final String baseUri = "http://localhost:8080/DaeProject-war/webapi";    
    //private final String baseUri = "http://localhost:38105/DaeProject-war/webapi";

    
    public AdministratorManager() {
        newStudent = new StudentDTO();
        newInstituition = new InstituitionDTO();
        client = ClientBuilder.newClient();
    }
    
    public String createStudent() {
        try {
            studentBean.create(
                    newStudent.getId(),
                    newStudent.getPassword(),
                    newStudent.getName(),
                    newStudent.getEmail(),
                    newStudent.getStudentNumber());
            newStudent.reset();
        } catch (EntityAlreadyExistsException | EntityDoesNotExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            return null;
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
            return null;
        }

        return "admin/student/index?faces-redirect=true";
    }
    
    public List<StudentDTO> getAllStudentsREST() {
        List<StudentDTO> returnedStudents;
        try {
            returnedStudents = client.target(baseUri)
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
     
    public String updateInstituition() {
        try {
            instituitionBean.update(
                    currentInstituition.getPassword(),
                    currentInstituition.getName(),
                    currentInstituition.getEmail());

        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
            return null;
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }
    
        public String updateInstituitionREST() {
        try {
            client.target(baseUri)
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
            returnedInstituitions = client.target(baseUri)
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
    
    
    /////////////// GETTERS & SETTERS ///////////////// 
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
    
    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }
}
