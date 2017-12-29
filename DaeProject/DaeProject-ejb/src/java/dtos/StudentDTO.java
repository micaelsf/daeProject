/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Student") 
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentDTO extends UserDTO implements Serializable {
    
  
    private String studentNumber;
    private int courseId;
    private String courseName;
   
    public StudentDTO(){
    }

    public StudentDTO(
            String username,
            String password,
            String name,
            String email,
            String studentNumber, 
            String city, 
            String address,
            int courseId,
            String courseName
    ) {
        super(username, password, name, email, city, address);
        this.studentNumber = studentNumber;
        this.courseId = courseId;
        this.courseName = courseName;
    }
    
    @Override
    public void reset() {
        super.reset();
        this.studentNumber = null;
        this.courseId = 0;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
    
}
