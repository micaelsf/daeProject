/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllInstitutionProposals",
            query = "SELECT ip FROM InstitutionProposal ip ORDER BY ip.title")
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
    
    @ManyToMany
    @JoinTable(name = "PROPOSAL_INSTITUTION",
            joinColumns = @JoinColumn(name = "PROPOSAL_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PROPONENT_ID", referencedColumnName = "ID"))
    private List<Institution> institutions;
    
    public InstitutionProposal() {
    }
    
    public InstitutionProposal(String title, String scientificAreas, String objectives, String supervisor, InstitutionProposalType proposalType) {
        super(title, scientificAreas, objectives);
        this.supervisor = supervisor;
        this.enumProposalType = proposalType;
        this.institutions = new LinkedList<>();
    }
    
    public void addInstitution(Institution institution) {
        institutions.add(institution);
    }

    public void removeInstitution(Institution institution) {
        institutions.remove(institution);
    }
    
    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public InstitutionProposalType getEnumInstitution() {
        return enumProposalType;
    }

    public void setEnumInstitution(InstitutionProposalType proposalType) {
        this.enumProposalType = proposalType;
    }

    public List<Institution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
    }
    
}
