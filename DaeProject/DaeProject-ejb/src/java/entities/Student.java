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
    @NamedQuery(name = "getAllStudents",
    query = "SELECT s FROM Student s ORDER BY s.name")
})
public class Student extends User implements Serializable {
    
    @NotNull(message = "Student number must not be empty")
    private String studentNumber;
    
    public Student() {
    }

    public Student(String password, String name, String email, String studentNumber) {
        super(password, name, email);
        this.studentNumber = studentNumber;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
    
    @Override
    public String toString() {
        return "Student{" + "Password=" + password + ", name=" + name + ", email=" + email + ", student number=" + studentNumber + '}';
    }
}
