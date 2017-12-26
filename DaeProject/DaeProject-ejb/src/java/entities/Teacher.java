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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "getAllTeachers", query = "SELECT t FROM Teacher t ORDER BY t.name")
public class Teacher extends User implements Serializable {

    @Column(nullable = false)
    private String office;
    
    @ManyToMany(mappedBy = "teachers")
    private List<TeacherProposal> proposals;
    
    protected Teacher() {
    }

    public Teacher(String password, String name, String email, String office) {
        super(password, GROUP.Teacher, name, email);
        this.office = office;
        
        proposals = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "Professor{"
                + ", nome=" + name
                + ", e-mail=" + email + "}";
    }

    public List<TeacherProposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<TeacherProposal> proposals) {
        this.proposals = proposals;
    }
    
    public void addProposal(TeacherProposal proposal) {
        proposals.add(proposal);
    }

    public void removeProposal(TeacherProposal proposal) {
        proposals.remove(proposal);
    }
    
    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
    
}
