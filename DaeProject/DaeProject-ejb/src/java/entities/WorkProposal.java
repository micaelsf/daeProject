/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "PROPOSALS"/*,
    uniqueConstraints =
        @UniqueConstraint(columnNames = {"TITLE"})*/
)
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
    @NamedQuery(name = "getAllProposals",  
            query = "SELECT wp FROM WorkProposal wp ORDER BY wp.title"),
    @NamedQuery(name = "getAllEndedProposals",  
            query = "SELECT wp FROM WorkProposal wp WHERE wp.isWorkCompleted = TRUE ORDER BY wp.title"),
    @NamedQuery(name = "getProposalById",  
            query = "SELECT wp FROM WorkProposal wp WHERE wp.id = :proposalId"),
    @NamedQuery(name = "getWorkProposalByTitle", 
            query = "SELECT wp FROM WorkProposal wp WHERE wp.title LIKE :title")
})
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
            inverseJoinColumns = @JoinColumn(name = "STUDENT_USERNAME", referencedColumnName = "USERNAME"))
    private List<Student> studentsApply;
    
    @OneToOne(fetch=FetchType.LAZY)
    @PrimaryKeyJoinColumn(name="USERNAME") //PrimaryKeyJoinColumn instead of JoinColumn because of conflicts while writing ID
    private Student student;
    
    @Column(nullable = false)
    private String workResume;
    
    @Column(nullable = false)
    private String objectives;
    
    @Column(nullable = true)
    private String bibliography1;
    
    @Column(nullable = true)
    private String bibliography2;
    
    @Column(nullable = true)
    private String bibliography3;
    
    @Column(nullable = true)
    private String bibliography4;
    
    @Column(nullable = true)
    private String bibliography5;
    
    //@ElementCollection(fetch = FetchType.EAGER)
    //private List<String> bibliography;
    
    @Column(name = "WORK_PLAN", nullable = false)
    private String workPlan;
    
    @Column(name = "WORK_LOCALITY", nullable = false)
    private String workLocality;
    
    @Column(name = "SUCCESS_REQUIREMENTS", nullable = false)
    private String successRequirements;
    
    @Column(nullable = false)
    private float budget;
    
    @Column(nullable = false)
    private String support;
    
    @Column(nullable = false)
    private String created_at;
    
    @Column(nullable = true)
    private String published_at;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProposalStatus status;
    
    @Column(name = "REJECT_REASON", nullable = true)
    private String rejectReason;
    
    @Column(nullable = true)
    private String comments;
    
    @Column(name = "PROGRESS_STATUS", nullable = false)
    private boolean isWorkCompleted; // true: proposal is ended can be published
    
    public WorkProposal() {
    }
    
    public WorkProposal(
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
            String support
    ) {
        this.title = title;
        this.scientificAreas = scientificAreas;        
        this.objectives = objectives;        
        this.workResume = workResume;  
        this.bibliography1 = bibliography1;       
        this.bibliography2 = bibliography2;       
        this.bibliography3 = bibliography3;       
        this.bibliography4 = bibliography4;       
        this.bibliography5 = bibliography5;
        this.workPlan = workPlan;       
        this.workLocality = workLocality;       
        this.successRequirements = successRequirements;
        this.budget = budget;
        this.support = support;
        
        this.rejectReason = " ";
        this.comments = " ";
        this.isWorkCompleted = false;
        this.status = ProposalStatus.Pendente;
        
        this.studentsApply = new LinkedList<>();
        //this.bibliography = new LinkedList<>();
        
        /*for (String s: (List<String>) bibliography) {
            this.bibliography.add(s);
        }*/
                
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        this.created_at = dateFormat.format(cal.getTime());
    }
     
    public void addStudentApply(Student student) {
        studentsApply.add(student);
    }

    public void removeStudentApply(Student student) {
        studentsApply.remove(student);
    }
/*    
    public void addBibliography(String b) {
        bibliography.add(b);
    }

    public void removeBibliography(String b) {
        bibliography.remove(b);
    }
*/

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isIsWorkCompleted() {
        return isWorkCompleted;
    }

    public void setIsWorkCompleted(boolean isWorkCompleted) {
        this.isWorkCompleted = isWorkCompleted;
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
/*
    public List<String> getBibliography() {
        return bibliography;
    }

    public void setBibliography(List<String> bibliography) {
        this.bibliography = bibliography;
    }
*/
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

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getBibliography1() {
        return bibliography1;
    }

    public void setBibliography1(String bibliography1) {
        this.bibliography1 = bibliography1;
    }

    public String getBibliography2() {
        return bibliography2;
    }

    public void setBibliography2(String bibliography2) {
        this.bibliography2 = bibliography2;
    }

    public String getBibliography3() {
        return bibliography3;
    }

    public void setBibliography3(String bibliography3) {
        this.bibliography3 = bibliography3;
    }

    public String getBibliography4() {
        return bibliography4;
    }

    public void setBibliography4(String bibliography4) {
        this.bibliography4 = bibliography4;
    }

    public String getBibliography5() {
        return bibliography5;
    }

    public void setBibliography5(String bibliography5) {
        this.bibliography5 = bibliography5;
    }
    
}