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
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "PROPOSALS")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "getAllProposals",  query = "SELECT wp FROM WorkProposal wp ORDER BY wp.title")
public class WorkProposal implements Serializable {
    
    public static enum ProposalStatus implements Serializable {
        Aceite, NãoAceite, Pendente;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String scientificAreas;

    @ManyToMany
    @JoinTable(name = "PROPOSAL_STUDENT",
            joinColumns = @JoinColumn(name = "PROPOSAL_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID"))
    private List<Student> studentsApply;
    
    @OneToOne(fetch=FetchType.LAZY)
    @PrimaryKeyJoinColumn(name="ID") //PrimaryKeyJoinColumn instead of JoinColumn because of conflicts while writing ID
    private Student student;
    
    @Column(nullable = true)
    private String workResume;
    
    @Column(nullable = true)
    private String objectives;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> bibliography;
    
    @Column(name = "WORK_PLAN", nullable = true)
    private String workPlan;
    
    @Column(name = "WORK_LOCALITY", nullable = true)
    private String workLocality;
    
    @Column(name = "SUCCESS_REQUIREMENTS", nullable = true)
    private String successRequirements;
    
    @Column(nullable = true)
    private float budget;
    
    @Column(nullable = true)
    private String support;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProposalStatus status;
    
    public WorkProposal() {
        
    }
    
    public WorkProposal(String title, String scientificAreas, String objectives) {
        this.title = title;
        this.scientificAreas = scientificAreas;        
        this.objectives = objectives;
        this.status = ProposalStatus.Pendente;
        this.studentsApply = new LinkedList<>();
        this.bibliography = new LinkedList<>();
    }
     
    public void addStudentApply(Student student) {
        studentsApply.add(student);
    }

    public void removeStudentApply(Student student) {
        studentsApply.remove(student);
    }
    
    public void addBibliography(String b) {
        bibliography.add(b);
    }

    public void removeBibliography(String b) {
        bibliography.remove(b);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Student> getStudentsApply() {
        return studentsApply;
    }

    public void setStudentsApply(List<Student> studentsApply) {
        this.studentsApply = studentsApply;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getWorkResume() {
        return workResume;
    }

    public void setWorkResume(String workResume) {
        this.workResume = workResume;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public List<String> getBibliography() {
        return bibliography;
    }

    public void setBibliography(List<String> bibliography) {
        this.bibliography = bibliography;
    }

    public String getWorkPlan() {
        return workPlan;
    }

    public void setWorkPlan(String workPlan) {
        this.workPlan = workPlan;
    }

    public String getWorkLocality() {
        return workLocality;
    }

    public void setWorkLocality(String workLocality) {
        this.workLocality = workLocality;
    }

    public String getSuccessRequirements() {
        return successRequirements;
    }

    public void setSuccessRequirements(String successRequirements) {
        this.successRequirements = successRequirements;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public ProposalStatus getStatus() {
        return status;
    }

    public void setStatus(ProposalStatus status) {
        this.status = status;
    }
}
