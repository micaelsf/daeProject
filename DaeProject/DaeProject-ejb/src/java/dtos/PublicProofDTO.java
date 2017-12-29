package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PublicProof")
@XmlAccessorType(XmlAccessType.FIELD)
public class PublicProofDTO implements Serializable{

    private int id;
    private String proofDate;
    private String proofTime;
    private String location;
    private String ccpMember;
    private String ccpMemberEmail;
    private String workGuider;
    private String workGuiderEmail;
    private String teacher;
    private String teacherEmail;
    private String studentUsername;
    private String studentName;
    private String studentEmail;
    private String studentNumber;
    private String studentCourse;
    private String workTitle;
    private String link;
    private String ataName;
    private String oldCompleteFilePath;
    
    public PublicProofDTO(){
    }
    
    public PublicProofDTO(
            int id, 
            String proofDate,
            String proofTime,
            String location,
            String ccpMember,
            String ccpMemberEmail,
            String workGuider,
            String workGuiderEmail,
            String teacher,
            String teacherEmail,
            String studentUsername,
            String studentName,
            String studentEmail,
            String studentNumber,
            String studentCourse,
            String workTitle,
            String ataName,
            String oldCompleteFilePath
    ){
            this.id = id; 
            this.proofDate = proofDate;
            this.proofTime = proofTime;
            this.location = location;
            this.ccpMember = ccpMember;
            this.ccpMemberEmail= ccpMemberEmail;
            this.workGuider = workGuider;
            this.workGuiderEmail = workGuiderEmail;
            this.teacher = teacher;
            this.teacherEmail = teacherEmail;
            this.studentUsername = studentUsername;
            this.studentName = studentName;
            this.studentEmail = studentEmail;
            this.studentNumber = studentNumber;
            this.studentCourse = studentCourse;
            this.workTitle = workTitle;
            this.ataName = ataName;
            this.link =  " ";
            this.oldCompleteFilePath = oldCompleteFilePath;
    }
    
    public void reset(){
        this.id = 0; 
        this.proofDate = null;
        this.proofTime = null;
        this.location = null;
        this.ccpMember = null;
        this.ccpMemberEmail= null;
        this.workGuider = null;
        this.workGuiderEmail = null;
        this.teacher = null;
        this.teacherEmail = null;
        this.studentUsername = null;
        this.studentName = null;
        this.studentEmail = null;
        this.studentNumber = null;
        this.studentCourse = null;
        this.workTitle = null;
        this.ataName = null;
    }

    public String getOldCompleteFilePath() {
        return oldCompleteFilePath;
    }

    public void setOldCompleteFilePath(String oldCompleteFilePath) {
        this.oldCompleteFilePath = oldCompleteFilePath;
    }

    public String getAtaName() {
        return ataName;
    }

    public void setAtaName(String ataName) {
        this.ataName = ataName;
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
    
    public String getProofTimeShort() {
        String[] splited_time = proofTime.split(":");
        if (splited_time.length > 0) {
            return splited_time[0] + ":" + splited_time[1];
        }
        return "";
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

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public String getstudentUsername() {
        return studentUsername;
    }

    public void setstudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentCourse() {
        return studentCourse;
    }

    public void setStudentCourse(String studentCourse) {
        this.studentCourse = studentCourse;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    public boolean isAtaAssigned() {
        if (this.ataName == null) {
            return false;
        }
        return this.ataName.trim().length() > 0;
    }
    
    public String isAtaAssignedToString() {
        if (isAtaAssigned()) {
            return "Sim";
        }
        return "Não";
    }
}
