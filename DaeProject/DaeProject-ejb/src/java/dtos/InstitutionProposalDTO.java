package dtos;

import entities.InstitutionProposal.InstitutionProposalType;
import entities.WorkProposal.ProposalStatus;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "InstitutionProposal")
@XmlAccessorType(XmlAccessType.FIELD)
public class InstitutionProposalDTO extends WorkProposalDTO {
    
    private String supervisor;
    private InstitutionProposalType institutionProposalType;
    
    public InstitutionProposalDTO() {
    }    
    
    public InstitutionProposalDTO(
            int id, 
            String title, 
            String scientificAreas, 
            String objectives, 
            String supervisor,
            InstitutionProposalType proposalType) {
        super(id, title, scientificAreas, objectives);
        this.supervisor = supervisor;
        this.institutionProposalType = proposalType;
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
