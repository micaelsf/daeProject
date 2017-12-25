package dtos;

import entities.InstitutionProposal.INSTITUTION_PROPOSAL_TYPE;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "InstitutionProposal")
@XmlAccessorType(XmlAccessType.FIELD)
public class InstitutionProposalDTO extends WorkProposalDTO {
    
    /*private int id;        
    private String title;    
    private String scientificAreas;
    private String objectives;
    private int status;*/
    //private String status;
    private String supervisor;
    private INSTITUTION_PROPOSAL_TYPE institutionProposalType;
    
    public InstitutionProposalDTO() {
    }    
    
    public InstitutionProposalDTO(
            int id, 
            String title, 
            String scientificAreas, 
            String objectives, 
            int status, 
            String supervisor,
            INSTITUTION_PROPOSAL_TYPE proposalType) {
        /*this.id = id;        
        this.title = title;        
        this.scientificAreas = scientificAreas;        
        this.objectives = objectives;
        this.status = status;*/
        super(id, title, scientificAreas, objectives, status);
        this.supervisor = supervisor;
        this.institutionProposalType = institutionProposalType;
    }
  /*  
    public void reset() {
        setId(-1);
        setTitle(null);        
        setScientificAreas(null);        
        setObjectives(null);
        setStatus(-1);
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

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
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
*/
    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public INSTITUTION_PROPOSAL_TYPE getInstitutionProposalType() {
        return institutionProposalType;
    }

    public void setInstitutionProposalType(INSTITUTION_PROPOSAL_TYPE institutionProposalType) {
        this.institutionProposalType = institutionProposalType;
    }
    
}
