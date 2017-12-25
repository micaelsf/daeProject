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
    @NamedQuery(name = "getAllInstitutions",
            query = "SELECT s FROM Institution s ORDER BY s.name")
})
public class Institution extends User implements Serializable{

    @NotNull(message = "O nome da instituição não pode ser null")

    public Institution() {
    }

    public Institution(String password, String name, String email) {
        super(password, GROUP.Institution, name, email);
    }

    @Override
    public String toString() {
        return "Instituição{"
                + ", nome=" + name
                + ", e-mail=" + email + "}";
    }

}
