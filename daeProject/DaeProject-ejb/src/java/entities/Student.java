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
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllStudents", 
            query = "SELECT s FROM Student s ORDER BY s.name")
})
public class Student extends User implements Serializable {
    
    @NotNull(message = "Student number must not be empty")
    private String studentNumber;
    
    @ManyToMany(mappedBy = "studentsApply")
    private List<WorkProposal> workProposalsApply;
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="student")
    private WorkProposal workProposal;
    
    public Student() {
        
    }

    public Student(String password, String name, String email, String studentNumber) {
        super(password, name, email);
        this.studentNumber = studentNumber;
        this.workProposalsApply = new LinkedList<>();
    }

    public void addProposalApply(WorkProposal workProp) {
        workProposalsApply.add(workProp);
    }
    
    public void removeProposalApply(WorkProposal workProp) {
        workProposalsApply.remove(workProp);
    }
    
    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public List<WorkProposal> getWorkProposalsApply() {
        return workProposalsApply;
    }

    public void setWorkProposalsApply(List<WorkProposal> workProposalsApply) {
        this.workProposalsApply = workProposalsApply;
    }

    public WorkProposal getWorkProposal() {
        return workProposal;
    }

    public void setWorkProposal(WorkProposal workProposal) {
        this.workProposal = workProposal;
    }
    
    @Override
    public String toString() {
        return "Student{" + "Password=" + password + ", name=" + name + ", email=" + email + ", student number=" + studentNumber + '}';
    }
}
