package dtos;

import entities.InstitutionProposal.InstitutionProposalType;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "InstitutionProposal")
@XmlAccessorType(XmlAccessType.FIELD)
public class InstitutionProposalDTO extends WorkProposalDTO implements Serializable {
    
    private String supervisor;
    private InstitutionProposalType institutionProposalType;
    
    public InstitutionProposalDTO() {
    }    
    
    public InstitutionProposalDTO(
            int id, 
            String title, 
            String scientificAreas, 
            String objectives,  
            String workResume,  
            String bibliography1, 
            String bibliography2,
            String bibliography3,
            String bibliography4,
            String bibliography5, 
            String workPlan,  
            String workLocality,  
            String successRequirements,  
            float budget,  
            String support,  
            String supervisor,
            InstitutionProposalType proposalType
    ) {
        super(
                id, 
                title, 
                scientificAreas, 
                objectives, 
                workResume, 
                bibliography1, 
                bibliography2, 
                bibliography3, 
                bibliography4, 
                bibliography5, 
                workPlan, 
                workLocality, 
                successRequirements,
                budget, 
                support
        );
        this.supervisor = supervisor;
        this.institutionProposalType = proposalType;
    }
    
    @Override
    public void reset() {
        super.reset();
        setSupervisor(null);
        setInstitutionProposalType(null);  
    } 
    
    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public InstitutionProposalType getInstitutionProposalType() {
        return institutionProposalType;
    }

    public void setInstitutionProposalType(InstitutionProposalType institutionProposalType) {
        this.institutionProposalType = institutionProposalType;
    }
}
