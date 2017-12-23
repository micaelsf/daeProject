/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllInstituitions",
            query = "SELECT s FROM Instituition s ORDER BY s.name")
})

public class Instituition extends User implements Serializable{

    @NotNull(message = "O id da instituição não pode ser null")

    public Instituition() {
    }

    public Instituition(String password, String name, String email) {
        super(password, GROUP.Instituition, name, email);
    }

    @Override
    public String toString() {
        return "Instituição{"
                + ", nome=" + name
                + ", e-mail=" + email + "}";
    }

}
