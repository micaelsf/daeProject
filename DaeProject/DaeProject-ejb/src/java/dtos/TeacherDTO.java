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

@XmlRootElement(name = "Teacher") 
@XmlAccessorType(XmlAccessType.FIELD)
public class TeacherDTO extends UserDTO implements Serializable{
    private String teacherNumber;
   
    public TeacherDTO(){
    }

    public TeacherDTO(
            int id,
            String teacherNumber,
            String password,
            String name,
            String email) {
        super(id, password, name, email);
        this.teacherNumber = teacherNumber;
    }
    
    @Override
    public void reset() {
        super.reset();
        this.teacherNumber = null;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }
    
}
