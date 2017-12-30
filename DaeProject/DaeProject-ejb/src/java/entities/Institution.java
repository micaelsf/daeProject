/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllInstitutions",
            query = "SELECT s FROM Institution s ORDER BY s.name"),
    @NamedQuery(name = "getInstitutionByUsername", 
            query = "SELECT i FROM Institution i WHERE i.username = :username"),
    @NamedQuery(name = "getInstitutionByEmail", 
            query = "SELECT i FROM Institution i WHERE i.email = :email"),
    @NamedQuery(name = "getInstitutionByName", 
            query = "SELECT i FROM Institution i WHERE i.name LIKE :name")
})
public class Institution extends User implements Serializable {

    @OneToMany(mappedBy = "institution", cascade = CascadeType.REMOVE)
    private List<InstitutionProposal> proposals;
    
    @Column(nullable = true)
    private String enterprise;
    
    public Institution() {
    }

    public Institution(
            String username, 
            String password, 
            String name, 
            String email, 
            String city, 
            String address, 
            String enterprise
    ) {
        super(username, password, GROUP.Institution, name, email, city, address);
        this.enterprise = enterprise;
        this.proposals = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "Instituição{"
                + ", nome=" + name
                + ", e-mail=" + email + "}";
    }

    public List<InstitutionProposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<InstitutionProposal> proposals) {
        this.proposals = proposals;
    }
    
    public void addProposal(InstitutionProposal proposal) {
        proposals.add(proposal);
    }

    public void removeProposal(InstitutionProposal proposal) {
        proposals.remove(proposal);
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }
    
}

