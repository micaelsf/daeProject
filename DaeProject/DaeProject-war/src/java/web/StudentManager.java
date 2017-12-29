package web;

import static com.sun.xml.ws.security.impl.policy.Constants.logger;
import dtos.DocumentDTO;
import dtos.StudentDTO;
import dtos.TeacherProposalDTO;
import dtos.WorkProposalDTO;
import ejbs.StudentBean;
import entities.Student;
import entities.WorkProposal;
import exceptions.EntityDoesNotExistsException;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import utils.URILookup;

@ManagedBean
@SessionScoped
public class StudentManager implements Serializable {

    private Client client;
    private HttpAuthenticationFeature feature;

    // private Student currentStudent;
    @ManagedProperty(value = "#{userManager}")
    private UserManager userManager;

    @ManagedProperty(value = "#{uploadManager}")
    private UploadManager uploadManager;

    private WorkProposalDTO currentProposal;

    private List<DocumentDTO> documents;
    private DocumentDTO document;

    private String filePath;
    private UIComponent component;

    public StudentManager() {
        client = ClientBuilder.newClient();
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
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedProposals;
    }

    public List<WorkProposal> getAllStudentWorkProposalsREST() {
        List<WorkProposal> returnedProposals;
        try {
            int hardCodeCurrentStudent = 3;

            returnedProposals = client.target(URILookup.getBaseAPI())
                    .path("/proposals/allStudentProposalsRest")
                    //.path(Integer.toString(currentStudent.getId()))
                    .path(hardCodeCurrentStudent + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<WorkProposal>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedProposals;
    }

    public void enrollStudent(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("id");
            int id = Integer.parseInt(param.getValue().toString());

            System.out.println("StudentManager -> EnrollStudent -> currentProposal: " + id);
            int hardCodeCurrentStudent = 3;

            client.target(URILookup.getBaseAPI())
                    .path("/proposals/enrollStudentRest")
                    .path(hardCodeCurrentStudent + "")
                    .path(currentProposal.getId() + "")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
        }
    }

    public void unrollStudent(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("id");
            int id = Integer.parseInt(param.getValue().toString());
            
            int hardCodeCurrentStudent = 3;
            
            client.target(URILookup.getBaseAPI())
                    .path("/proposals/unrollStudentRest")
                    .path(Integer.toString(hardCodeCurrentStudent))
                    .path(Integer.toString(id))
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
        }
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public UploadManager getUploadManager() {
        return uploadManager;
    }

    public void setUploadManager(UploadManager uploadManager) {
        this.uploadManager = uploadManager;
    }
/*
    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }
 */
    public WorkProposalDTO getCurrentProposal() {
        return currentProposal;
    }

    public void setCurrentProposal(WorkProposalDTO currentProposal) {
        this.currentProposal = currentProposal;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }
}
