/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.UserGroup.GROUP;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity
@NamedQuery(name = "getAllTeachers", query = "SELECT t FROM Teacher t ORDER BY t.name")

public class Teacher extends User {

    @NotNull(message = "O nome do professor não pode ser null")

    protected Teacher() {
    }

    public Teacher(String password, String name, String email) {
        super(password, GROUP.Teacher, name, email);
    }

    @Override
    public String toString() {
        return "Professor{"
                + ", nome=" + name
                + ", e-mail=" + email + "}";
    }
}
