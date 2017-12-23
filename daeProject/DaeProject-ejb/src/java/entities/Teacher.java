/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "getAllTeachers", query = "SELECT t FROM Teacher t ORDER BY t.name")

public class Teacher extends User implements Serializable{

    protected Teacher() {
    }

    public Teacher(String password, String name, String email) {
        super(password, GROUP.Teacher, name, email);
    }
}
