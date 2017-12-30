package web;

import static com.sun.xml.ws.security.impl.policy.Constants.logger;
import dtos.DocumentDTO;
import dtos.StudentDTO;
import dtos.WorkProposalDTO;
import ejbs.StudentBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.LinkedList;
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

    @EJB
    private StudentBean studentBean;
    private StudentDTO student;
    
    private String searchField = "";
    
    private String filePath;
    private UIComponent component;

    public StudentManager() {
        client = ClientBuilder.newClient();
    }

    @PostConstruct
    public void initClient() {
        feature = HttpAuthenticationFeature.basic(userManager.getUsername(), userManager.getPassword());
        client.register(feature);
        getLoggedStudent();
    }

    private void getLoggedStudent() {
        try {

            student = client.target(URILookup.getBaseAPI())
                    .path("/students/findStudentByUsername")
                    .path(userManager.getUsername())
                    .request(MediaType.APPLICATION_XML)
                    .get(StudentDTO.class);

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
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

    public List<WorkProposalDTO> getAllStudentWorkProposalsREST() {
        List<WorkProposalDTO> returnedProposals;
        try {

            returnedProposals = client.target(URILookup.getBaseAPI())
                    .path("/proposals/allStudentProposalsRest")
                    .path(userManager.getUsername() + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<WorkProposalDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedProposals;
    }

    public String enrollStudentInProposal(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("id");
            int id = Integer.parseInt(param.getValue().toString());

            System.out.println("web.StudentManager.enrollStudentInProposal()");
            client.target(URILookup.getBaseAPI())
                    .path("/proposals/enrollStudentRest")
                    .path(userManager.getUsername() + "")
                    .path(id + "")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "indexAppliedProposal?faces-redirect=true";
    }

    public void unrollStudentFromProposal(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("removeId");
            int removeId = Integer.parseInt(param.getValue().toString());
            
            client.target(URILookup.getBaseAPI())
                    .path("/proposals/unrollStudentRest")
                    .path(userManager.getUsername() + "")
                    .path(removeId + "")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
        }
    }
    
    public boolean isProposalApplied() {
        boolean isApplied = false;
        try {
            String result = client.target(URILookup.getBaseAPI())
                    .path("/students/isStudentAppliedToProposal")
                    .path(userManager.getUsername() + "")
                    .path(currentProposal.getId() + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<String>() {
                    });

            if (result.equals("YES")) {
                isApplied = true;
            }

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return false;
        }
        return isApplied;
    }
    
    public boolean isMaxProposalReached() {
        boolean maxReached = false;
        try {
            String totalProposal = client.target(URILookup.getBaseAPI())
                    .path("/students/isStudentMaxProposalReached")
                    .path(userManager.getUsername() + "")
                    .path(currentProposal.getId() + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<String>() {
                    });
            
            if (Integer.parseInt(totalProposal) == 5) {
                maxReached = true;
            }

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return false;
        }
        return maxReached;
    }
    
    public List<WorkProposalDTO> clickSearch() {
        if (this.searchField == null || this.searchField.trim().length() == 0) {
            return getAllWorkProposalsREST();
        }
        
        return searchProposalByTitleREST();
    }
    
    public List<WorkProposalDTO> searchProposalByTitleREST() {
        List<WorkProposalDTO> list = new LinkedList<>();
        try {
            
            WorkProposalDTO resource = client.target(URILookup.getBaseAPI())
                    .path("/proposals/searchByTitle")
                    .path(this.searchField + "")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<WorkProposalDTO>() {
                    });
            list.add(resource);
            
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return list;
    }

    public boolean searchFieldIsEmpty() {
        if (this.searchField == null) {
            return true;
        }
        return this.searchField.trim().isEmpty();
    }
    
    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
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
