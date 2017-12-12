/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllTeachers",
    query = "SELECT s FROM Teacher s ORDER BY s.name")
})
public class Teacher extends Proponent implements Serializable {

    @NotNull(message = "Teacher number must not be empty")
    private String teacherNumber;
    
    public Teacher() {
    }

    public Teacher(String password, String name, String email, String teacherNumber) {
        super(password, name, email);
        this.teacherNumber = teacherNumber;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }
    
    
}
