/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Proponent extends User implements Serializable{
    
    @ManyToMany(mappedBy = "proponents")
    private List<WorkProposal> workProposals;
        
    public Proponent() {
    }

    public Proponent(String password, String name, String email) {
        super(password, name, email);
        workProposals = new LinkedList<>();
    }
    
    public void addWorkProposals(WorkProposal workProposal) {
        workProposals.add(workProposal);
    }
    
    public void removeWorkProposals(WorkProposal workProposal) {
        workProposals.remove(workProposal);
    }
    
    public List<WorkProposal> getWorkProposals() {
        return workProposals;
    }

    public void setWorkProposals(List<WorkProposal> workProposals) {
        this.workProposals = workProposals;
    }
    
}
