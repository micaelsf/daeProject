package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PUBLIC_PROOFS")
@NamedQueries({
    @NamedQuery(name = "getAllPublicProofs", query = "SELECT pp FROM PublicProof pp ORDER BY pp.proofDate")
})
public class PublicProof implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(nullable = false)
    private String proofDate;
    
    @Column(nullable = false)
    private String proofTime;
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private String ccpMember;
    
    @Column(nullable = false)
    private String ccpMemberEmail;
    
    @Column(nullable = false)
    private String workGuider;
    
    @Column(nullable = false)
    private String workGuiderEmail;
    
    @Column(nullable = false)
    private String teacher;
    
    @Column(nullable = false)
    private String teacherEmail;
    
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "publicProof")
    private Student student;
    
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "publicProof")
    private Document documentATA;

    @Column(nullable = false)
    private String workTitle;
    
    @Column(nullable = true)
    private String link;
    
    public PublicProof(){
    }
    
    public PublicProof(
            int id, 
            String date,
            String time,
            String location,
            String ccpMember,
            String ccpMemberEmail,
            String workGuider,
            String workGuiderEmail,
            String teacher,
            String teacherEmail,
            Student student,
            String workTitle
    ){
        this.id = id;
        this.proofDate = date;
        this.proofTime = time;
        this.location = location;
        this.ccpMember = ccpMember;
        this.ccpMemberEmail = ccpMemberEmail;
        this.workGuider = workGuider;
        this.workGuiderEmail = workGuiderEmail;
        this.teacher = teacher;
        this.teacherEmail = teacherEmail;
        this.student = student;
        this.workTitle = workTitle;
        this.link = null;
        this.documentATA = null;
    }

    public Document getDocumentATA() {
        return documentATA;
    }

    public void setDocumentATA(Document documentATA) {
        this.documentATA = documentATA;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProofDate() {
        return proofDate;
    }

    public void setProofDate(String proofDate) {
        this.proofDate = proofDate;
    }

    public String getProofTime() {
        return proofTime;
    }

    public void setProofTime(String proofTime) {
        this.proofTime = proofTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCcpMember() {
        return ccpMember;
    }

    public void setCcpMember(String ccpMember) {
        this.ccpMember = ccpMember;
    }

    public String getCcpMemberEmail() {
        return ccpMemberEmail;
    }

    public void setCcpMemberEmail(String ccpMemberEmail) {
        this.ccpMemberEmail = ccpMemberEmail;
    }

    public String getWorkGuider() {
        return workGuider;
    }

    public void setWorkGuider(String workGuider) {
        this.workGuider = workGuider;
    }

    public String getWorkGuiderEmail() {
        return workGuiderEmail;
    }

    public void setWorkGuiderEmail(String workGuiderEmail) {
        this.workGuiderEmail = workGuiderEmail;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    
}
