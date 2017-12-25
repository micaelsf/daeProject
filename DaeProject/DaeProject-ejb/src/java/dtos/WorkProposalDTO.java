package dtos;

import entities.WorkProposal.ProposalStatus;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "WorkProposal")
@XmlAccessorType(XmlAccessType.FIELD)
public class WorkProposalDTO implements Serializable {
    
    private int id;        
    private String title;    
    private String scientificAreas;
    private String objectives;
    private ProposalStatus status;
    //private String status;

    public WorkProposalDTO() {
    }    
    
    public WorkProposalDTO(int id, String title, String scientificAreas, String objectives) {
        this.id = id;        
        this.title = title;        
        this.scientificAreas = scientificAreas;        
        this.objectives = objectives;
        this.status = ProposalStatus.Pendente;
    }
    
    public void reset() {
        setId(-1);
        setTitle(null);        
        setScientificAreas(null);        
        setObjectives(null);
        setStatus(null);
    }    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScientificAreas() {
        return scientificAreas;
    }

    public void setScientificAreas(String scientificAreas) {
        this.scientificAreas = scientificAreas;
    }

    public ProposalStatus getStatus() {
        return this.status;
    }

    public void setStatus(ProposalStatus status) {
        this.status = status;            
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }
    
    public String statusToStr(int status) {
        switch (status) {
            case 1: return "Não aceite";            
            case 2: return "Aceite";
            case 3: return "Em espera"; 
            default: return null;
        }
    }
    
    public int statusToInt(String status) {
        switch (status) {
            case "Não aceite": return 1;            
            case "Aceite": return 2;
            case "Em espera": return 3; 
            default: return -1;
        }
    }
    
}
