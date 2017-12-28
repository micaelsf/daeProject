/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.UserGroup.GROUP;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllStudents",
            query = "SELECT s FROM Student s ORDER BY s.name"),
    @NamedQuery(name = "getAllStudentsCourse",
            query = "SELECT s FROM Student s WHERE s.course.id = :courseId ORDER BY s.name"),
    @NamedQuery(name = "getAllStudentByNumber",
            query = "SELECT s FROM Student s WHERE s.studentNumber = :number")
})
public class Student extends User{

    @Column(nullable = false)
    private String studentNumber;

    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;
    
    @OneToMany(mappedBy = "student")
    public List<Document> documents;

    @ManyToMany(mappedBy = "studentsApply")
    private List<WorkProposal> workProposalsApply;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "student")
    private WorkProposal workProposal;
    
    @OneToOne(fetch=FetchType.LAZY)
    @PrimaryKeyJoinColumn(name="ID")
    private PublicProof publicProof;

    public Student() {
        documents = new LinkedList<>();
    }

    public Student(String password, String name, String email, String studentNumber, String city, String address, Course course) {
        super(password, GROUP.Student, name, email, city, address);
        this.course = course;
        this.studentNumber = studentNumber;

        documents = new LinkedList<>();
        this.workProposalsApply = new LinkedList<>();
    }

    public PublicProof getPublicProof() {
        return publicProof;
    }

    public void setPublicProof(PublicProof publicProof) {
        this.publicProof = publicProof;
    }

    public void addProposalApply(WorkProposal workProp) {
        workProposalsApply.add(workProp);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void addDocument(Document document) {
        this.documents.add(document);
    }

    public void removeDocument(Document document) {
        this.documents.remove(document);
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

    public String toString() {
        return "Student{" + "Password=" + password + ", nome=" + name + ", e-mail=" + email + ", número de estudante=" + studentNumber + '}';
    }
}
