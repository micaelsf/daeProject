/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "getAllTeachers", query = "SELECT t FROM Teacher t ORDER BY t.name")

public class Teacher extends User implements Serializable {

    @Column(nullable = false)
    private String office;
    
    protected Teacher() {
    }

    public Teacher(String password, String name, String email, String office) {
        super(password, GROUP.Teacher, name, email);
        this.office = office;
    }

    @Override
    public String toString() {
        return "Professor{"
                + ", nome=" + name
                + ", e-mail=" + email + "}";
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
    
}
