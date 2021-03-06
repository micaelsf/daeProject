/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllInstitutionProposals",
            query = "SELECT ip FROM InstitutionProposal ip ORDER BY ip.title"),
    @NamedQuery(name = "getInstitutionProposalByTitle", 
            query = "SELECT ip FROM InstitutionProposal ip WHERE ip.title LIKE :title")
})
public class InstitutionProposal extends WorkProposal {

    public static enum InstitutionProposalType implements Serializable {
        Projeto, Estágio, Dissertação;
    }
    
    @Column(nullable = false)
    private String supervisor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "PROPOSAL_TYPE")
    private InstitutionProposalType enumProposalType;
    
    @ManyToOne
    @JoinColumn(name = "INSTITUTION_USERNAME")
    private Institution institution;
    
    public InstitutionProposal() {
    }
    
    public InstitutionProposal(
            String title, 
            String scientificAreas, 
            String objectives, 
            String workResume, 
            //List<String> bibliography, 
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
            InstitutionProposalType proposalType,
            Institution institution
    ) {
        super(
                title, 
                scientificAreas, 
                objectives, 
                workResume, 
                //bibliography, 
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
        this.enumProposalType = proposalType;
        this.institution = institution;
        //this.institutions = new LinkedList<>();
    }
/*    
    public void addInstitution(Institution institution) {
        institutions.add(institution);
    }

    public void removeInstitution(Institution institution) {
        institutions.remove(institution);
    }
*/    
    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public InstitutionProposalType getEnumProposalType() {
        return enumProposalType;
    }

    public void setEnumProposalType(InstitutionProposalType enumProposalType) {
        this.enumProposalType = enumProposalType;
    }
    
}
