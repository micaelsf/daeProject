package web;

import static com.sun.xml.ws.security.impl.policy.Constants.logger;
import dtos.DocumentDTO;
import dtos.InstitutionDTO;
import dtos.InstitutionProposalDTO;
import ejbs.InstitutionBean;
import entities.InstitutionProposal.InstitutionProposalType;
import entities.WorkProposal;
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
public class InstitutionManager implements Serializable {

    private Client client;
    private HttpAuthenticationFeature feature;

    @ManagedProperty(value = "#{userManager}")
    private UserManager userManager;

    @ManagedProperty(value = "#{uploadManager}")
    private UploadManager uploadManager;

    private InstitutionProposalDTO newProposal;
    private InstitutionProposalDTO currentProposal;
    
    private List<DocumentDTO> documents;
    private DocumentDTO document;

    @EJB
    private InstitutionBean institutionBean;

    private InstitutionDTO institution;
    
    private String filePath;
    private UIComponent component;
    
    public InstitutionManager() {
        client = ClientBuilder.newClient();
        newProposal = new InstitutionProposalDTO();
    }

    @PostConstruct
    public void initClient() {
        feature = HttpAuthenticationFeature.basic(userManager.getEmail(), userManager.getPassword());
        client.register(feature);
        getLoggedStudent();
    }

    private void getLoggedStudent() {
        try {

            institution = client.target(URILookup.getBaseAPI())
                    .path("/institutions/findInstitution")
                    .path(userManager.getEmail())
                    .request(MediaType.APPLICATION_XML)
                    .get(InstitutionDTO.class);

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }

    /* PROPOSAL */
    public InstitutionProposalType[] getAllTypes() {
        return InstitutionProposalType.values();
    }
    
    public String createProposal() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/institutionProposals/createREST")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(newProposal));
            newProposal.reset();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<InstitutionProposalDTO> getAllProposalsREST() {
        List<InstitutionProposalDTO> returnedProposals;
        try {
            returnedProposals = client.target(URILookup.getBaseAPI())
                    .path("/institutionProposals/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<InstitutionProposalDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return returnedProposals;
    } 
    
    public String updateProposalREST() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/institutionProposals/updateREST")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentProposal));
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public void removeProposal(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("id");
            int id = Integer.parseInt(param.getValue().toString());

            client.target(URILookup.getBaseAPI())
                    .path("/institutionProposals/removeREST")
                    .path(id + "")
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(""));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado! Tente novamente mais tarde!", logger);
        }
    }
    
    public boolean isPendente(WorkProposal.ProposalStatus status) {
        return status == WorkProposal.ProposalStatus.Pendente;
    }
    
    public boolean isRejected() {
        return currentProposal.getStatus() == WorkProposal.ProposalStatus.NãoAceite;
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

    public DocumentDTO getDocument() {
        return document;
    }

    public void setDocument(DocumentDTO document) {
        this.document = document;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public InstitutionProposalDTO getNewProposal() {
        return newProposal;
    }

    public void setNewProposal(InstitutionProposalDTO newProposal) {
        this.newProposal = newProposal;
    }

    public InstitutionProposalDTO getCurrentProposal() {
        return currentProposal;
    }

    public void setCurrentProposal(InstitutionProposalDTO currentProposal) {
        this.currentProposal = currentProposal;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

}
