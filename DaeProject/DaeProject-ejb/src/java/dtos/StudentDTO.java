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
public class StudentDTO extends UserDTO implements Serializable{
    private String studentNumber;
   
    public StudentDTO(){
    }

    public StudentDTO(
            int id,
            String studentNumber,
            String password,
            String name,
            String email) {
        super(id, password, name, email);
        this.studentNumber = studentNumber;
    }
    
    @Override
    public void reset() {
        super.reset();
        this.studentNumber = null;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
    
}
